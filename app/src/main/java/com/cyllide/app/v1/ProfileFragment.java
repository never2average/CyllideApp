package com.cyllide.app.v1;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.cyllide.app.v1.MainActivity.MY_PERMISSION_REQUEST_CODE;

public class ProfileFragment extends Fragment {

    ImageButton cross;
    Button save;
    TextView username, quizzesWon, quizzesParticipated, numReferrals, numPosts, numUpvotes, numHearts;
    CircleImageView profilePic;
    Uri defaultProfilePic =Uri.parse(
            "android.resource://com.example.kartikbhardwaj.bottom_navigation/drawable/profile_pic");
    StorageReference storageReference;
    Uri targetUri;
    SharedPreferences sharedPreferences;
    RequestQueue uploadPicQueue, getPicQueue;
    Map<String,String> uploadPicUriMap = new ArrayMap<>();
    Map<String,String> downloadPicUriMap = new ArrayMap<>();
    RequestQueue profileDataRequestQueue;
    Map<String,String> profileMap = new ArrayMap<>();
    AnimatedPieView contestWinPerc;
    byte[] data;
    Uri bitmapUri;
    String url;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile,null);

        return view;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    AppConstants.cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                }
                break;
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePic=view.findViewById(R.id.profile_pic);


        storageReference= FirebaseStorage.getInstance().getReference();
        sharedPreferences=view.getContext().getSharedPreferences("profileUrl", MODE_PRIVATE);





        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.quiz_wining_xml);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        cross=view.findViewById(R.id.cross_btn);
        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment fragment = new HomeFragment();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

            }
        });

        username = view.findViewById(R.id.profile_username);
        quizzesWon = view.findViewById(R.id.profile_quiz_wins);
        quizzesParticipated = view.findViewById(R.id.profile_quizzes);
        numReferrals = view.findViewById(R.id.profile_referrals);
        numPosts = view.findViewById(R.id.profile_posts);
        numUpvotes = view.findViewById(R.id.profile_upvotes);
        contestWinPerc = view.findViewById(R.id.contest_win_perc);
        numHearts = view.findViewById(R.id.profile_num_coins);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!AppConstants.cameraAccepted){
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.CAMERA,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            },
                            MY_PERMISSION_REQUEST_CODE);
                }
                else {


                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);

                }
            }
        });
        profileInfo();




    }

    private void getProfilePicVolley() {
        final String[] url = {getResources().getString(R.string.apiBaseURL) + "profilepic"};
        getPicQueue = Volley.newRequestQueue(getContext());
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
                    if(uri.equals(AppConstants.noProfilePicURL)){
                        ColorGenerator generator = ColorGenerator.MATERIAL;
                        Log.d("ProfileFragment","inside if");
                        int color = generator.getColor(username.getText().toString());
                        TextDrawable drawable = TextDrawable.builder()
                                .beginConfig()
                                .width(120)  // width in px
                                .height(120) // height in px
                                .endConfig()
                                .buildRect(Character.toString(username.getText().toString().charAt(0)).toUpperCase(), color);

                        profilePic.setImageDrawable(drawable);

                    }
                    else {
                        RequestOptions requestOptions = new RequestOptions().override(100);
                        Glide.with(getContext()).load(uri).apply(requestOptions).into(profilePic);
                    }


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


    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){

            targetUri = data.getData();

            if(targetUri.equals(Uri.parse(AppConstants.noProfilePicURL))){
                ColorGenerator generator = ColorGenerator.MATERIAL;
                Log.d("ProfileFragment","inside if");
                int color = generator.getColor(username.getText().toString());
                TextDrawable drawable = TextDrawable.builder()
                        .beginConfig()
                        .width(60)  // width in px
                        .height(60) // height in px
                        .endConfig()
                        .buildRect(Character.toString(username.getText().toString().charAt(0)).toUpperCase(), color);

                profilePic.setImageDrawable(drawable);

            }
            else {
                RequestOptions requestOptions = new RequestOptions().override(100);
                Glide.with(getContext()).load(targetUri).apply(requestOptions).into(profilePic);
            }
            uploadImage(getContext());
            Log.e("ProfilePicSet","inside on activity result");
            Toast.makeText(getContext(),"onActivityResult",Toast.LENGTH_LONG).show();
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), targetUri);
                ByteArrayOutputStream baos =new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG,25,baos);
                String path =MediaStore.Images.Media.insertImage(getContext().getContentResolver(),bitmap,"profile_pic",null);
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

    private void setProfilePicVolley(final String toString) {
        uploadPicQueue = Volley.newRequestQueue(getContext());
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

    private void profileInfo(){
        profileDataRequestQueue = Volley.newRequestQueue(getContext());
        profileMap.put("token",AppConstants.token);
        String url = getResources().getString(R.string.apiBaseURL)+"profileinfo";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
                    username.setText(jsonResponse.getString("userName"));
                    quizzesWon.setText(jsonResponse.getString("quizzesWon"));
                    quizzesParticipated.setText(jsonResponse.getString("quizzesWon"));
                    numReferrals.setText(jsonResponse.getString("numberReferrals"));
                    numPosts.setText(String.valueOf(jsonResponse.getInt("questionsAsked")+jsonResponse.getInt("questionsAnswered")));
                    numUpvotes.setText(jsonResponse.getString("numUpvotes"));
                    numHearts.setText(jsonResponse.getString("numCoins"));
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("AUTHENTICATION", MODE_PRIVATE);
                    sharedPreferences.edit().putInt("coins",Integer.parseInt(jsonResponse.getString("numCoins")));
                    AnimatedPieViewConfig config =  new  AnimatedPieViewConfig ().drawText(true).textSize(40);
                    double contestsPart = jsonResponse.getDouble("contestsParticipated");
                    double contestsWon = jsonResponse.getDouble("contestsWon");
                    if(contestsPart==0.0){
                        config.startAngle(-90).addData(
                                new SimplePieInfo(1, ContextCompat.getColor(getContext(), R.color.contest_grey),"")).duration(1500);
                        contestWinPerc.applyConfig(config);
                        contestWinPerc.start();
                    }
                    else {
                        double winPercent = contestsWon / contestsPart;
                        double lostPercent = 1 - winPercent;
                        config.startAngle(-90).addData(
                                new SimplePieInfo((float) winPercent, ContextCompat.getColor(getContext(), R.color.progressgreen), "Win %")).addData(
                                new SimplePieInfo((float) lostPercent, ContextCompat.getColor(getContext(), R.color.progressred), "Loss %")).duration(1500);
                        contestWinPerc.applyConfig(config);
                        contestWinPerc.start();
                    }

                    if(sharedPreferences.getString("profileUri",null)==null)
                    {
                        Log.d("ProfileFragment","null, getting profile pic volley");
                        getProfilePicVolley();
                    }else{
                        String ur=sharedPreferences.getString("profileUri",null);
                        Uri uri=Uri.parse(ur);
                        Log.d("imageuri",ur);
                        Log.d("ProfileFragment","URI exists, checking condition");

                        Log.d("ProfileFragment",uri.toString());

                        Log.d("ProfileFragment",AppConstants.noProfilePicURL);
                        if(uri.equals(Uri.parse(AppConstants.noProfilePicURL))){
                            ColorGenerator generator = ColorGenerator.MATERIAL;
                            Log.d("ProfileFragment","inside if");
                            int color = generator.getColor(username.getText().toString());
                            TextDrawable drawable = TextDrawable.builder()
                                    .beginConfig()
                                    .width(60)  // width in px
                                    .height(60) // height in px
                                    .endConfig()
                                    .buildRect(Character.toString(username.getText().toString().charAt(0)).toUpperCase(), color);

                            profilePic.setImageDrawable(drawable);

                        }
                        else {

                            Log.d("ProfileFragment","setting default pic from profile info");
                            RequestOptions requestOptions = new RequestOptions().override(100);
                            Glide.with(getContext()).load(uri).apply(requestOptions).into(profilePic);
                        }
                    }

                } catch (JSONException e) {
                    Log.d("profileinfoerror", e.toString());
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
        profileDataRequestQueue.add(stringRequest);
    }


}
