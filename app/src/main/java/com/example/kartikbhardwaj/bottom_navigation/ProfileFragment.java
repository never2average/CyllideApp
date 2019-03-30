package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bumptech.glide.Glide;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.Faq_Activity;
import com.example.kartikbhardwaj.bottom_navigation.howitworks.Fragment0;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.content.CursorLoader;
import de.hdodenhof.circleimageview.CircleImageView;
import com.amazonaws.mobileconnectors.s3.transferutility.*;


import java.io.File;
import java.net.URI;

import static android.app.Activity.RESULT_OK;
import static com.amazonaws.services.kms.model.KeyManagerType.AWS;

public class ProfileFragment extends Fragment {

    ImageButton cross;
    Button save;
    TextView username;
    CardView toptraders;
    CircleImageView profilePic;
    Uri defaultProfilePic =Uri.parse("android.resource://com.example.kartikbhardwaj.bottom_navigation/drawable/profile_pic");




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


        AmazonS3Client s3;
        BasicAWSCredentials credentials;
        TransferUtility transferUtility;
        String secret="lt1c8ZvncAjXWGwvGRlt73FBTn+woqvSKDk7gOUU";
        String key="AKIAYR4VSDM4ASSOVN7U";
        credentials=new BasicAWSCredentials(key,secret);
        s3=new AmazonS3Client(credentials);








        final Dialog dialog=new Dialog(getContext());
        dialog.setContentView(R.layout.quiz_wining_xml);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));




        toptraders=view.findViewById(R.id.toptraders);

        toptraders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

            }
        });





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
        profilePic=view.findViewById(R.id.profile_pic);
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
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });





    }



    @Override
   public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            ;
            //textTargetUri.setText(targetUri.toString());\
            Glide.with(this).load(targetUri).into(profilePic);

            AmazonS3Client s3;
            BasicAWSCredentials credentials;
            TransferUtility transferUtility;
            String secret="lt1c8ZvncAjXWGwvGRlt73FBTn+woqvSKDk7gOUU";
            String key="AKIAYR4VSDM4ASSOVN7U";
            credentials=new BasicAWSCredentials(key,secret);
            s3=new AmazonS3Client(credentials);

            transferUtility=new TransferUtility(s3,getContext());
            String[] proj={MediaStore.Images.Media.DATA};
            CursorLoader loader=new CursorLoader(getContext(),targetUri,proj,null,null,null);
            Cursor cursor=loader.loadInBackground();
            int Column_index=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result=cursor.getString(Column_index);
            cursor.close();


//            File file = new File(targetUri.getPath());//create path from uri
//            final String[] split = file.getPath().split(":");//split the path.
//            String filePath = split[1];//assign it to a string(your choice).

            TransferObserver downloadObserver =
                    transferUtility.upload(
                            "cyllideassets","profile_pic",
                            new File(result));





//            TransferUtility transferUtility =
//                    TransferUtility.builder()
//                            .context(getContext())
//                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
//                            .s3Client(new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider()))
//                            .build();



           // Uri uri = data.getData();

            //File file = new File(filepath);
            //ObjectMetadata myObjectMetadata = new ObjectMetadata();
           // myObjectMetadata.setContentType("image/png");
          //  String mediaUrl = file.getName();
//            TransferObserver observer = transferUtility.upload(AWSKeys.BUCKET_NAME, mediaUrl,
//                    file);
//            observer.setTransferListener(new UploadListener());






            // Attach a listener to the observer to get state update and progress notifications
            downloadObserver.setTransferListener(new TransferListener() {

                @Override
                public void onStateChanged(int id, TransferState state) {
                    if (TransferState.COMPLETED == state) {
                        // Handle a completed upload.
                        //
                        Toast.makeText(getContext(),"uploaded",Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    float percentDonef = ((float)bytesCurrent/(float)bytesTotal) * 100;
                    int percentDone = (int)percentDonef;

                    Log.d("LOG_TAG", "   ID:" + id + "   bytesCurrent: " + bytesCurrent + "   bytesTotal: " + bytesTotal + " " + percentDone + "%");
                }

                @Override
                public void onError(int id, Exception ex) {
                    // Handle errors
                    Toast.makeText(getContext(),"  not uploaded error",Toast.LENGTH_LONG).show();

                }

            });

            // If you prefer to poll for the data, instead of attaching a
            // listener, check for the state and progress in the observer.
            if (TransferState.COMPLETED == downloadObserver.getState()) {
                // Handle a completed upload.
            }

            Log.d("LOG_TAG", "Bytes Transferrred: " + downloadObserver.getBytesTransferred());
            Log.d("LOG_TAG", "Bytes Total: " + downloadObserver.getBytesTotal());


















            //profilePic.setImageURI(targetUri);
            Log.e("ProfilePicSet","inside on activity result");
            Toast.makeText(getContext(),"onActivityResult",Toast.LENGTH_LONG).show();


        }







    }





}
