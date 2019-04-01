package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
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
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import de.hdodenhof.circleimageview.CircleImageView;


import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    ImageButton cross;
    Button save;
    TextView username, quizzesWon, quizzesParticipated, numReferrals, numPosts, numUpvotes;
    CircleImageView profilePic;
    Uri defaultProfilePic =Uri.parse("android.resource://com.example.kartikbhardwaj.bottom_navigation/drawable/profile_pic");
    StorageReference storageReference;
    Uri targetUri;
    SharedPreferences sharedPreferences;
    RequestQueue uploadPicQueue, getPicQueue;
    Map<String,String> uploadPicUriMap = new ArrayMap<>();
    Map<String,String> downloadPicUriMap = new ArrayMap<>();
    RequestQueue profileDataRequestQueue;
    Map<String,String> profileMap = new ArrayMap<>();
    AnimatedPieView contestWinPerc;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile,null);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        profilePic=view.findViewById(R.id.profile_pic);

        storageReference= FirebaseStorage.getInstance().getReference();
        sharedPreferences=view.getContext().getSharedPreferences("profileUrl",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();


        if(sharedPreferences.getString("profileUri",null)==null)
        {
            Toast.makeText(getContext(),"no uri found for profile pic",Toast.LENGTH_LONG).show();

            getProfilePicVolley();




        }else{
            String ur=sharedPreferences.getString("profileUri",null);
            Uri uri=Uri.parse(ur);









            Glide.with(getContext()).load(uri).into(profilePic);
            Toast.makeText(getContext(),ur,Toast.LENGTH_LONG).show();




        }


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

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        profileInfo();
    }

    private void getProfilePicVolley() {
        String url = getResources().getString(R.string.apiBaseURL)+"profilepic";
        getPicQueue = Volley.newRequestQueue(getContext());
        downloadPicUriMap.put("token", AppConstants.token);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("error",response);
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
            Glide.with(this).load(targetUri).into(profilePic);
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
            ref.putFile(targetUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Uri downloaduri = uri;
                                    SharedPreferences.Editor editor=sharedPreferences.edit();

                                    editor.putString("profileUri",downloaduri.toString());
                                    editor.commit();
                                    setProfilePicVolley(downloaduri.toString());

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

    private void setProfilePicVolley(String toString) {
        uploadPicQueue = Volley.newRequestQueue(getContext());
        uploadPicUriMap.put("token", AppConstants.token);
        String url = getResources().getString(R.string.apiBaseURL)+"profilepic";
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("setURL", response);
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
                    AnimatedPieViewConfig config =  new  AnimatedPieViewConfig ().drawText(true).textSize(40);
                    double contestsPart = jsonResponse.getDouble("contestsParticipated");
                    double contestsWon = jsonResponse.getDouble("contestsWon");
                    double winPercent = contestsWon/contestsPart;
                    double lostPercent = 1 - winPercent;
                    config.startAngle(-90).addData(
                            new SimplePieInfo((float) winPercent, ContextCompat.getColor(getContext(), R.color.progressgreen),"Win %")).addData (
                                    new SimplePieInfo( (float) lostPercent, ContextCompat.getColor(getContext(), R.color.progressred), "Loss %" )).duration(1500);
                    contestWinPerc.applyConfig (config);
                    contestWinPerc.start();
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
