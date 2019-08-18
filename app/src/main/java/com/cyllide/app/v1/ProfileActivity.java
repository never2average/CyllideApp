package com.cyllide.app.v1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;
import com.yalantis.ucrop.UCrop;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


public class ProfileActivity extends AppCompatActivity {

    ImageButton cross;
    Uri targetUri;
    Map<String, String> uploadPicUriMap = new ArrayMap<>();
    Dialog quizWinPopup;
    ImageView profileMedal;
    String level;
    RequestQueue winPAYTMRequestQueue;
    Map<String, String> winPAYTMRequestHeader = new ArrayMap<>();
    Map<String, String> othersMap = new ArrayMap<>();

    TextView
            username,
            quizzesWon,
            quizzesParticipated,
            numReferrals,
            numPosts,
            coins,
            money,
            percentageDaysProfitable,
            prizes,
            numUpvotes,
            numHearts;

    AnimatedPieView contestWinPerc;
    RequestQueue requestQueue;
    RequestQueue uploadPicQueue, getPicQueue;
    Map<String, String> downloadPicUriMap = new ArrayMap<>();

    Map<String, String> profileMap = new ArrayMap<>();
    boolean isEditable;
    Uri bitmapUri;
    StorageReference storageReference;
    SharedPreferences sharedPreferences;

    ImageView cyllideLogo;
    ImageView profilePic;
    String viewUsername;

    Uri photoURI;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_profile);
        Fresco.initialize(this);
        cross = findViewById(R.id.view_only_cross_btn);
        username = findViewById(R.id.view_only_profile_username);
        username = findViewById(R.id.view_only_profile_username);
        storageReference = FirebaseStorage.getInstance().getReference();
        sharedPreferences = getApplicationContext().getSharedPreferences("profileUrl", MODE_PRIVATE);
        quizzesWon = findViewById(R.id.view_only_profile_quiz_wins);
        quizzesParticipated = findViewById(R.id.view_only_profile_quizzes);
        numReferrals = findViewById(R.id.view_only_profile_referrals);
        numPosts = findViewById(R.id.view_only_profile_posts);
        numUpvotes = findViewById(R.id.view_only_profile_upvotes);
        contestWinPerc = findViewById(R.id.view_only_contest_win_perc);
        numHearts = findViewById(R.id.view_only_profile_num_coins);
        coins = findViewById(R.id.points_collected);
        prizes = findViewById(R.id.view_only_profile_prizes);
        money = findViewById(R.id.money_won);
        percentageDaysProfitable = findViewById(R.id.per_days_profitable);

        cyllideLogo = findViewById(R.id.cyllidemainlogo);
        profilePic = findViewById(R.id.profile_pic_container);
        profileMedal = findViewById(R.id.profile_medal);

        quizWinPopup = new Dialog(this);
        quizWinPopup.setContentView(R.layout.quiz_wining_xml);
        quizWinPopup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exitIntent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(exitIntent);
                finish();
            }
        });
        try {
            if (getIntent().getStringExtra("viewname") != null) {
                fetchProfileOthers(getIntent().getStringExtra("viewname"));
            } else {
                setupProfile();
            }

        } catch (Exception e) {

        }

    }

    void setupProfile() {
        try {
            coins.setText(AppConstants.coins);
            money.setText(AppConstants.money);
        } catch (Exception e) {
            Log.d("ProfileActivity", "Coins and money are not loaded");
        }
        try {
            viewUsername = getIntent().getStringExtra("viewname");
        } catch (Exception e) {

        }

        level = AppConstants.userLevel;
        if (level.equals("Gold")) {
            profileMedal.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_gold_medal));
        } else {
            if (level.equals("Silver")) {
                profileMedal.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_silver_medal));
            } else {
                profileMedal.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_bronze_medal));
            }
        }
        if (sharedPreferences.getString("profileUri", null) == null) {
            ColorGenerator generator = ColorGenerator.MATERIAL;
            int color = generator.getColor(AppConstants.username);
            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(100)
                    .height(100)
                    .endConfig()
                    .buildRect(Character.toString(AppConstants.username.charAt(0)).toUpperCase(), color);
            profilePic.setImageDrawable(drawable);

        } else {
            String ur = sharedPreferences.getString("profileUri", null);
            Uri uri = Uri.parse(ur);
            Log.d("imageuri", ur);
            Glide.with(ProfileActivity.this).load(uri).into(profilePic);


        }

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {

            isEditable = intent.getBooleanExtra("Editable", false);

        }


        fillAllViewsVolley();
    }

    void winnersMoney(String upiID) {
        winPAYTMRequestQueue = Volley.newRequestQueue(ProfileActivity.this);
        winPAYTMRequestHeader.put("token", AppConstants.token);
        winPAYTMRequestHeader.put("upiID", upiID);
        String url = getResources().getString(R.string.apiBaseURL) + "quiz/reward";
        StringRequest sr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("moneyResponse", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {

                return winPAYTMRequestHeader;
            }
        };

        winPAYTMRequestQueue.add(sr);
    }


    void fillAllViewsVolley() {
        requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        profileMap.put("token", AppConstants.token);
        profileMap.put("username", AppConstants.viewUsername);
        String url = getResources().getString(R.string.apiBaseURL) + "profileinfo";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
                    quizzesWon.setText(jsonResponse.getString("quizzesWon"));
                    quizzesParticipated.setText(jsonResponse.getString("quizzesWon"));
                    numReferrals.setText(jsonResponse.getString("numberReferrals"));
                    numPosts.setText(String.valueOf(jsonResponse.getInt("questionsAsked") + jsonResponse.getInt("questionsAnswered")));
                    numUpvotes.setText(jsonResponse.getString("numUpvotes"));
                    numHearts.setText(jsonResponse.getString("numCoins"));
                    AnimatedPieViewConfig config = new AnimatedPieViewConfig().drawText(false).textSize(40);
                    config.strokeWidth(30);
                    coins.setText(jsonResponse.getString("points_collected"));
                    money.setText(jsonResponse.getString("money_won"));
                    money.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.cyllide_grey));
                    if (Integer.parseInt(money.getText().toString()) > AppConstants.minWithdrawable) {
                        money.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.green));
                        money.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                TextView quizMoney = quizWinPopup.findViewById(R.id.quiz_winning_prize_money);
                                final TextInputEditText upiID = quizWinPopup.findViewById(R.id.upi_id);

                                quizWinPopup.show();
                                quizMoney.setText("₹ " + money.getText().toString());
                                ImageView closePrizePopup = quizWinPopup.findViewById(R.id.close_prize_popup);
                                closePrizePopup.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        quizWinPopup.dismiss();
                                    }
                                });
                                ImageView sendUPI = quizWinPopup.findViewById(R.id.upi_id_button);
                                sendUPI.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        quizWinPopup.dismiss();
                                        final String string = upiID.getText().toString();
                                        Snackbar snackbar = Snackbar
                                                .make(findViewById(R.id.root_layout), "Money will be sent", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                        winnersMoney(string);
                                    }
                                });

                            }
                        });
                    }

                    double contestsPart = jsonResponse.getDouble("portfolioDays");
                    prizes.setText(contestsPart + "");
                    double contestsWon = jsonResponse.getDouble("profitablePortfolioDays");
                    double winPercent;

                    try {
                        winPercent = contestsWon / contestsPart;
                        if (contestsPart == 0) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        winPercent = 0;
                    }
                    double lostPercent = 1 - winPercent;
                    percentageDaysProfitable.setText(winPercent * 100 + "%");
                    config.startAngle(-90).addData(
                            new SimplePieInfo((float) winPercent, ContextCompat.getColor(ProfileActivity.this, R.color.progress_), "")).addData(
                            new SimplePieInfo((float) lostPercent, ContextCompat.getColor(ProfileActivity.this, R.color.transparent), "")).duration(1500);
                    contestWinPerc.applyConfig(config);
                    contestWinPerc.start();
                    RequestOptions cropOptions = new RequestOptions().override(100, 100);
                    String profilePicURL = jsonResponse.getString("profilePic");
                } catch (JSONException e) {
                    Log.d("ProfileActivity", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return profileMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {


            UCrop.of(photoURI, photoURI)
                    .start(ProfileActivity.this);
        }

        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            Glide.with(ProfileActivity.this).load(photoURI).into(profilePic);
            targetUri = photoURI;

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("profileUri", targetUri.toString());

            editor.commit();
            uploadImage(ProfileActivity.this);


        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
    }

    private void uploadImage(final Context context) {

        if (targetUri != null) {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            final StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), targetUri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap, "profile_pic", null);
                bitmapUri = Uri.parse(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ref.putFile(bitmapUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Glide.with(ProfileActivity.this).load(targetUri).into(profilePic);


                                    setProfilePicVolley(uri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            progressDialog.setMessage("Uploaded " + (int) progress + "%");
                        }
                    });
        }
    }

    private void getProfilePicVolley() {
        final String[] url = {getResources().getString(R.string.apiBaseURL) + "profilepic"};
        getPicQueue = Volley.newRequestQueue(getApplicationContext());
        downloadPicUriMap.put("token", AppConstants.token);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url[0], new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String uri = jsonObject.getJSONArray("data").getJSONObject(0).getString("profilePic");
                    url[0] = uri;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("profileUri", uri);
                    editor.commit();

                    Log.d("ProfileFragment", "Inside get profile pic");
                    Log.d("ProfileFragment", uri);
                    Log.d("ProfileFragment", AppConstants.noProfilePicURL);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return downloadPicUriMap;
            }
        };
        getPicQueue.add(stringRequest);
    }

    private void setProfilePicVolley(final String toString) {
        uploadPicQueue = Volley.newRequestQueue(getApplicationContext());
        uploadPicUriMap.put("token", AppConstants.token);
        uploadPicUriMap.put("profileURL", toString);
        Log.d("respNow", toString);
        String url = getResources().getString(R.string.apiBaseURL) + "profilepic";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return uploadPicUriMap;
            }
        };
        uploadPicQueue.add(stringRequest);
    }


    String currentPhotoPath;

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this,
                        "com.cyllide.app.v1.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private void fetchProfileOthers(String username) {
        requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        String url = getResources().getString(R.string.apiBaseURL) + "profileinfo";
        othersMap.put("token", AppConstants.token);
        othersMap.put("username", username);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
                    quizzesWon.setText(jsonResponse.getString("quizzesWon"));
                    quizzesParticipated.setText(jsonResponse.getString("quizzesWon"));
                    numReferrals.setText(jsonResponse.getString("numberReferrals"));
                    numPosts.setText(String.valueOf(jsonResponse.getInt("questionsAsked") + jsonResponse.getInt("questionsAnswered")));
                    numUpvotes.setText(jsonResponse.getString("numUpvotes"));
                    numHearts.setText(jsonResponse.getString("numCoins"));
                    AnimatedPieViewConfig config = new AnimatedPieViewConfig().drawText(false).textSize(40);
                    config.strokeWidth(30);
                    coins.setText(jsonResponse.getString("points_collected"));
                    money.setText(jsonResponse.getString("money_won"));
                    String uri = jsonResponse.getString("profilePic");
                    Glide.with(ProfileActivity.this).load(uri).into(profilePic);
                    String level = jsonResponse.getString("level");
                    if (level.equals("Gold")) {
                        profileMedal.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_gold_medal));
                    } else {
                        if (level.equals("Silver")) {
                            profileMedal.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_silver_medal));
                        } else {
                            profileMedal.setImageDrawable(ContextCompat.getDrawable(ProfileActivity.this, R.drawable.ic_bronze_medal));
                        }
                    }
                    money.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.cyllide_grey));

                    double contestsPart = jsonResponse.getDouble("portfolioDays");
                    prizes.setText(contestsPart + "");
                    double contestsWon = jsonResponse.getDouble("profitablePortfolioDays");
                    double winPercent;

                    try {
                        winPercent = contestsWon / contestsPart;
                        if (contestsPart == 0) {
                            throw new Exception();
                        }
                    } catch (Exception e) {
                        winPercent = 0;
                    }
                    double lostPercent = 1 - winPercent;
                    percentageDaysProfitable.setText(winPercent * 100 + "%");
                    config.startAngle(-90).addData(
                            new SimplePieInfo((float) winPercent, ContextCompat.getColor(ProfileActivity.this, R.color.progress_), "")).addData(
                            new SimplePieInfo((float) lostPercent, ContextCompat.getColor(ProfileActivity.this, R.color.transparent), "")).duration(1500);
                    contestWinPerc.applyConfig(config);
                    contestWinPerc.start();
                    RequestOptions cropOptions = new RequestOptions().override(100, 100);
                    String profilePicURL = jsonResponse.getString("profilePic");
                } catch (JSONException e) {
                    Log.d("ProfileActivity", e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("LOGERROR", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return othersMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
