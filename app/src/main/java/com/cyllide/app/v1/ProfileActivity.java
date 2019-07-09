package com.cyllide.app.v1;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
import com.cyllide.app.v1.forum.ForumActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.cyllide.app.v1.MainActivity.MY_PERMISSION_REQUEST_CODE;

public class ProfileActivity extends AppCompatActivity {

    ImageButton cross;
//    CircleImageView profilePic;
    Uri targetUri;
    Map<String,String> uploadPicUriMap = new ArrayMap<>();

    TextView
//            username,
            quizzesWon,
            quizzesParticipated,
            numReferrals,
            numPosts,
            numUpvotes,
            numHearts;
    AnimatedPieView contestWinPerc;
    RequestQueue requestQueue;
    RequestQueue uploadPicQueue, getPicQueue;
    Map<String,String> downloadPicUriMap = new ArrayMap<>();

    Map<String,String> profileMap = new ArrayMap<>();
    boolean isEditable;
    Uri bitmapUri;
    StorageReference storageReference;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_activity_profile);
        Fresco.initialize(this);
        storageReference= FirebaseStorage.getInstance().getReference();
        sharedPreferences=getApplicationContext().getSharedPreferences("profileUrl", MODE_PRIVATE);

        cross=findViewById(R.id.view_only_cross_btn);
//        username=findViewById(R.id.view_only_profile_username);
//        profilePic=findViewById(R.id.view_only_profile_pic);
//        username = findViewById(R.id.view_only_profile_username);
        quizzesWon = findViewById(R.id.view_only_profile_quiz_wins);
        quizzesParticipated = findViewById(R.id.view_only_profile_quizzes);
        numReferrals = findViewById(R.id.view_only_profile_referrals);
        numPosts = findViewById(R.id.view_only_profile_posts);
        numUpvotes = findViewById(R.id.view_only_profile_upvotes);
        contestWinPerc = findViewById(R.id.view_only_contest_win_perc);
        numHearts = findViewById(R.id.view_only_profile_num_coins);

        Intent intent = getIntent();
        if(intent!=null){

            isEditable = intent.getBooleanExtra("Editable",false);

        }

        cross.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            Intent exitIntent= new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(exitIntent);
            finish();
            }
        });
        fillAllViewsVolley();
    }

    void fillAllViewsVolley() {
        requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        profileMap.put("token",AppConstants.token);
        profileMap.put("username",AppConstants.viewUsername);
        String url = getResources().getString(R.string.apiBaseURL) + "profileinfo";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
//                    username.setText(jsonResponse.getString("userName"));
                    quizzesWon.setText(jsonResponse.getString("quizzesWon"));
                    quizzesParticipated.setText(jsonResponse.getString("quizzesWon"));
                    numReferrals.setText(jsonResponse.getString("numberReferrals"));
                    numPosts.setText(String.valueOf(jsonResponse.getInt("questionsAsked")+jsonResponse.getInt("questionsAnswered")));
                    numUpvotes.setText(jsonResponse.getString("numUpvotes"));
                    numHearts.setText(jsonResponse.getString("numCoins"));
                    AnimatedPieViewConfig config =  new  AnimatedPieViewConfig ().drawText(false).textSize(40);
                    config.strokeWidth(30);
//                    double contestsPart = jsonResponse.getDouble("portfolioDays");
//                    double contestsWon = jsonResponse.getDouble("profitablePortfolioDays");
//                    double winPercent = contestsWon/contestsPart;
//                    double lostPercent = 1 - winPercent;
                    config.startAngle(-90).addData(
                            new SimplePieInfo((float) 0.5, ContextCompat.getColor(ProfileActivity.this, R.color.progress_),"")).addData (
                            new SimplePieInfo( (float) 0.5, ContextCompat.getColor(ProfileActivity.this, R.color.transparent), "" )).duration(1500);
                    contestWinPerc.applyConfig (config);
                    contestWinPerc.start();
                    RequestOptions cropOptions = new RequestOptions().override(100,100);
                    String profilePicURL = jsonResponse.getString("profilePic");
//                    if(profilePicURL.equals(AppConstants.noProfilePicURL)){
//                        ColorGenerator generator = ColorGenerator.MATERIAL;
//                        Log.d("ProfileFragment","inside if");
//                        int color = generator.getColor(username.getText().toString());
//                        TextDrawable drawable = TextDrawable.builder()
//                                .beginConfig()
//                                .width(60)  // width in px
//                                .height(60) // height in px
//                                .endConfig()
//                                .buildRect(Character.toString(username.getText().toString().charAt(0)).toUpperCase(), color);
//
//                        profilePic.setImageDrawable(drawable);
//                    }
//                    else {
//                        Glide.with(ProfileActivity.this).load(jsonResponse.getString("profilePic")).apply(cropOptions).into(profilePic);
//                    }
//
//                    if(isEditable) {
//                        profilePic.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//
//                                if (!AppConstants.cameraAccepted) {
//                                    ActivityCompat.requestPermissions(ProfileActivity.this,
//                                            new String[]{Manifest.permission.CAMERA,
//                                                    Manifest.permission.READ_EXTERNAL_STORAGE,
//                                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                            },
//                                            MY_PERMISSION_REQUEST_CODE);
//                                } else {
//
//
//                                    Intent intent = new Intent(Intent.ACTION_PICK,
//                                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                                    startActivityForResult(intent, 0);
//
//                                }
//                            }
//                        });
//                    }
                } catch (JSONException e) {
                    Log.d("ProfileActivity",e.toString());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return profileMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            targetUri = data.getData();

//            if(targetUri.equals(Uri.parse(AppConstants.noProfilePicURL))){
//                ColorGenerator generator = ColorGenerator.MATERIAL;
//                Log.d("ProfileFragment","inside if");
//                int color = generator.getColor(username.getText().toString());
//                TextDrawable drawable = TextDrawable.builder()
//                        .beginConfig()
//                        .width(60)  // width in px
//                        .height(60) // height in px
//                        .endConfig()
//                        .buildRect(Character.toString(username.getText().toString().charAt(0)).toUpperCase(), color);
//
//                profilePic.setImageDrawable(drawable);
//
//            }
//            else {
//                RequestOptions requestOptions = new RequestOptions().override(100);
//                Glide.with(getApplicationContext()).load(targetUri).apply(requestOptions).into(profilePic);
//            }
//            uploadImage(getApplicationContext());
//            Log.e("ProfilePicSet","inside on activity result");
//            Toast.makeText(getApplicationContext(),"onActivityResult",Toast.LENGTH_LONG).show();
        }







    }

    private void uploadImage(final Context context) {

        if(targetUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Uploading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            final StorageReference ref = storageReference.child(UUID.randomUUID().toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), targetUri);
                ByteArrayOutputStream baos =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,25,baos);
                String path =MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(),bitmap,"profile_pic",null);
                bitmapUri =Uri.parse(path);

                //data =baos.toByteArray();

                // profilePic.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
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
                                    SharedPreferences.Editor editor=sharedPreferences.edit();
                                    editor.putString("profileUri",uri.toString());
                                    editor.commit();
                                    setProfilePicVolley(uri.toString());
                                }
                            });
                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(context, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
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
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.putString("profileUri",uri);
                    editor.commit();

                    Log.d("ProfileFragment","Inside get profile pic");
                    Log.d("ProfileFragment", uri);
                    Log.d("ProfileFragment",AppConstants.noProfilePicURL);
//                    if(uri.equals(AppConstants.noProfilePicURL)){
//                        ColorGenerator generator = ColorGenerator.MATERIAL;
//                        Log.d("ProfileFragment","inside if");
//                        int color = generator.getColor(username.getText().toString());
//                        TextDrawable drawable = TextDrawable.builder()
//                                .beginConfig()
//                                .width(120)  // width in px
//                                .height(120) // height in px
//                                .endConfig()
//                                .buildRect(Character.toString(username.getText().toString().charAt(0)).toUpperCase(), color);
//
//                        profilePic.setImageDrawable(drawable);
//
//                    }
//                    else {
//                        RequestOptions requestOptions = new RequestOptions().override(100);
//                        Glide.with(getApplicationContext()).load(uri).apply(requestOptions).into(profilePic);
//                    }


//                    RequestOptions requestOptions = new RequestOptions().override(100);
//                    Glide.with(getContext()).load(uri).apply(requestOptions).into(profilePic);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return downloadPicUriMap;
            }
        };
        getPicQueue.add(stringRequest);
    }

    private void setProfilePicVolley(final String toString) {
        uploadPicQueue = Volley.newRequestQueue(getApplicationContext());
        uploadPicUriMap.put("token", AppConstants.token);
        uploadPicUriMap.put("profileURL",toString);
        Log.d("respNow",toString);
        String url = getResources().getString(R.string.apiBaseURL)+"profilepic";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return uploadPicUriMap;
            }
        };
        uploadPicQueue.add(stringRequest);
    }



}
