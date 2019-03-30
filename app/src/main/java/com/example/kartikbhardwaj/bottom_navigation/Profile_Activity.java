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

public class Profile_Activity extends AppCompatActivity {

    ImageButton cross;
    Button save;
    TextView username;
    CardView faqs;
    CardView toptraders;
    CircleImageView profilePic;
    Uri defaultProfilePic = Uri.parse("android.resource://com.example.kartikbhardwaj.bottom_navigation/drawable/profile_pic");
    Uri pic_from_net;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        Fresco.initialize(this);


        toptraders = findViewById(R.id.toptraders);
        faqs = findViewById(R.id.faq);


        cross = findViewById(R.id.cross_btn);
        username = findViewById(R.id.username);
        profilePic = findViewById(R.id.profile_pic);

        pic_from_net = Uri.parse("data:image/png;base64,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");
        Glide.with(this).load(pic_from_net).into(profilePic);


        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exitIntent = new Intent(Profile_Activity.this, MainActivity.class);
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
                Intent faqintent = new Intent(Profile_Activity.this, Faq_Activity.class);
                startActivity(faqintent);


            }
        });


        Log.e("ProfilePicSet ", "profilePicSet");
        Toast.makeText(Profile_Activity.this, "onCreateFunction", Toast.LENGTH_LONG).show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());\
            Glide.with(this).load(targetUri).into(profilePic);

            //profilePic.setImageURI(targetUri);
            Log.e("ProfilePicSet", "inside on activity result");
            Toast.makeText(Profile_Activity.this, "onActivityResult", Toast.LENGTH_LONG).show();


        }


    }


}
