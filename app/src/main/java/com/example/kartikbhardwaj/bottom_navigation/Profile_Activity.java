package com.example.kartikbhardwaj.bottom_navigation;


import android.app.Activity;
import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqModal;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.Faq_Activity;
import com.example.kartikbhardwaj.bottom_navigation.stories.NewsModel;
import com.example.kartikbhardwaj.bottom_navigation.stories.StoriesActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import static java.security.AccessController.getContext;

public class Profile_Activity extends AppCompatActivity {

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
            Intent exitIntent= new Intent(Profile_Activity.this,MainActivity.class);
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
              Intent faqintent =new Intent(Profile_Activity.this,Faq_Activity.class);
              startActivity(faqintent);


            }
        });



        Log.e("ProfilePicSet ","profilePicSet");
        Toast.makeText(Profile_Activity.this,"onCreateFunction",Toast.LENGTH_LONG).show();






    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Uri targetUri = data.getData();
            //textTargetUri.setText(targetUri.toString());\
            Glide.with(this).load(targetUri).into(profilePic);

            //profilePic.setImageURI(targetUri);
            Log.e("ProfilePicSet","inside on activity result");
            Toast.makeText(Profile_Activity.this,"onActivityResult",Toast.LENGTH_LONG).show();


        }







    }












}
