package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;
import de.hdodenhof.circleimageview.CircleImageView;


import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    ImageButton cross;
    Button save;
    TextView username;
    CircleImageView profilePic;
    Uri defaultProfilePic =Uri.parse("android.resource://com.example.kartikbhardwaj.bottom_navigation/drawable/profile_pic");
    StorageReference storageReference;
    Uri targetUri;
    SharedPreferences sharedPreferences;
    RequestQueue uploadPicQueue, getPicQueue;
    Map<String,String> uploadPicUriMap = new ArrayMap<>();
    Map<String,String> downloadPicUriMap = new ArrayMap<>();





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view= inflater.inflate(R.layout.activity_profile_,null);









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
        username=view.findViewById(R.id.username);
        AnimatedPieView mAnimatedPieView = view.findViewById(R.id.win_perc);
        AnimatedPieViewConfig config =  new  AnimatedPieViewConfig ();
        config.startAngle(-90).addData(
                new SimplePieInfo( 75.0f , R.color.primary_light_max,"Win %")).addData (
                new SimplePieInfo( 25.0f ,R.color.primary_dark_max, "Loss %" )).duration( 2000 );
        mAnimatedPieView.applyConfig (config);
        mAnimatedPieView.start();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ConnectivityManager conMgr =(ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo=conMgr.getActiveNetworkInfo();

                if(netInfo ==null||!netInfo.isConnected()||!netInfo.isAvailable())
                {
                    Toast.makeText(getContext(),"Poor Network Connection",Toast.LENGTH_LONG).show();

                } else {

                    Intent intent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 0);
                }


            }
        });





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
            //Glide.with(this).load(targetUri).into(profilePic);


            //textTargetUri.setText(targetUri.toString());\

//            AmazonS3Client s3;
//            BasicAWSCredentials credentials;
//            String secret="lt1c8ZvncAjXWGwvGRlt73FBTn+woqvSKDk7gOUU";
//            String key="AKIAYR4VSDM4ASSOVN7U";
//            credentials=new BasicAWSCredentials(key,secret);
//            s3=new AmazonS3Client(credentials);
//            s3.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
////          TransferUtility  transferUtility=new TransferUtility(s3,getContext());
//
//            TransferUtility transferUtility =
//                    TransferUtility.builder()
//                            .defaultBucket("cyllideassets")
//                            .context(getContext())
//                            .s3Client(new AmazonS3Client(new BasicAWSCredentials(key,secret)))
//                            .build();
//
//
//            String[] proj={MediaStore.Images.Media.DATA};
//            CursorLoader loader=new CursorLoader(getContext(),targetUri,proj,null,null,null);
//            Cursor cursor=loader.loadInBackground();
//            int Column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            cursor.moveToFirst();
//            String result=cursor.getString(Column_index);
//            cursor.close();
//
//
//            TransferObserver Observer =
//                    transferUtility.upload(
//                            "cyllideassets",result,
//                            new File(result));
//
//
//
//
//
//
//
//
//
//
//
//
//            // Attach a listener to the observer to get state update and progress notifications
//            Observer.setTransferListener(new TransferListener() {
//
//                @Override
//                public void onStateChanged(int id, TransferState state) {
//                    if (TransferState.COMPLETED == state) {
//                        // Handle a completed upload.
//                        //
//                        Toast.makeText(getContext(),"uploaded",Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
//                    float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
//                    int percentDone = (int)percentDonef;
//
//                    Log.d("LOG_TAG", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
//                }
//
//                @Override
//                public void onError(int id, Exception ex) {
//                    // Handle errors
//                    Toast.makeText(getContext(),ex.toString(),Toast.LENGTH_LONG).show();
//
//                }
//
//            });
//
//            // If you prefer to poll for the data, instead of attaching a
//            // listener, check for the state and progress in the observer.
//            if (TransferState.COMPLETED == Observer.getState()) {
//                // Handle a completed upload.
//            }
//
//            Log.d("LOG_TAG", "Bytes Transferrred: " + Observer.getBytesTransferred());
//            Log.d("LOG_TAG", "Bytes Total: " + Observer.getBytesTotal());
//












            uploadImage(getContext());


            //profilePic.setImageURI(targetUri);
            Log.e("ProfilePicSet","inside on activity result");
            Toast.makeText(getContext(),"onActivityResult",Toast.LENGTH_LONG).show();


        }







    }

    private void uploadImage(final Context context) {

        if(targetUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(context);
            progressDialog.setTitle("Sync...");
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

                                    Glide
                                            .with(getContext())
                                            .load(uri)
                                            .placeholder(R.drawable.loadinganimation)
                                            .into(profilePic);


                                    SharedPreferences.Editor editor=sharedPreferences.edit();

                                    editor.putString("profileUri",downloaduri.toString());
                                    editor.commit();
                                    setProfilePicVolley(downloaduri.toString());




                                    // mdatabase.child("url").setValue(downloaduri.toString());
                                }
                            });
                            Toast.makeText(context, "Uploaded", Toast.LENGTH_SHORT).show();
//                            Intent imageActivity = new Intent(addImage.this,com.itparkbynipun.firebaseproject.imageActivity.class);
//                            startActivity(imageActivity);
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

                            progressDialog.setMessage("Sync "+(int)progress+"%");
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


}
