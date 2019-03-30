package com.example.kartikbhardwaj.bottom_navigation;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.Faq_Activity;
import com.facebook.drawee.backends.pipeline.Fresco;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageButton cross;
    Button save;
    TextView username;
    CardView faqs;
    CardView toptraders;
    CircleImageView profilePic;
    Uri defaultProfilePic =Uri.parse("android.resource://com.example.kartikbhardwaj.bottom_navigation/drawable/profile_pic");








    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        Fresco.initialize(this);





        toptraders=findViewById(R.id.toptraders);
        faqs=findViewById(R.id.faq);



        cross=findViewById(R.id.cross_btn);
        username=findViewById(R.id.username);
        profilePic=findViewById(R.id.profile_pic);






        cross.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            Intent exitIntent= new Intent(ProfileActivity.this,MainActivity.class);
       // ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Profile_Activity.this,profile_pic,ViewCompat.getTransitionName(profile_pic));

       // startActivity(exitIntent,options.toBundle());
        startActivity(exitIntent);
            finish();
            }
        });


        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });





        toptraders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


 //                popup.setContentView(R.layout.top_traders_popup);
 //                popup.getWindow();
 //
 //                popup.show();


            }
        });





        faqs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//               // intent to faq activity
              Intent faqintent =new Intent(ProfileActivity.this,Faq_Activity.class);
              startActivity(faqintent);


            }
        });



        Log.e("ProfilePicSet ","profilePicSet");
        Toast.makeText(ProfileActivity.this,"onCreateFunction",Toast.LENGTH_LONG).show();






    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());\
            Glide.with(this).load(targetUri).into(profilePic);

            //profilePic.setImageURI(targetUri);
            Log.e("ProfilePicSet","inside on activity result");
            Toast.makeText(ProfileActivity.this,"onActivityResult",Toast.LENGTH_LONG).show();


        }







    }












}
