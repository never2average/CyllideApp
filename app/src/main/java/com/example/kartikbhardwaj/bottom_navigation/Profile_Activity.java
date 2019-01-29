package com.example.kartikbhardwaj.bottom_navigation;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqModal;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.Faq_Activity;
import com.example.kartikbhardwaj.bottom_navigation.stories.NewsModel;
import com.example.kartikbhardwaj.bottom_navigation.stories.StoriesActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Activity extends AppCompatActivity {

    ImageButton cross;
    Dialog nameEditPopup;
    Dialog popup;
    Button save;
    EditText newName;
    TextView username;
    CircleImageView profile_pic;
    CardView setting;
    CardView faqs;
    CardView aboutus;
    CardView toptraders;
    String currentPaswrd_string;
    String newpaswrd_string;
    String againnewpaswrd_string;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);


        nameEditPopup=new Dialog(this);
        popup=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);

        setting=findViewById(R.id.setting);
        aboutus=findViewById(R.id.aboutus);
        toptraders=findViewById(R.id.toptraders);
        faqs=findViewById(R.id.faq);



        cross=findViewById(R.id.cross_btn);
        username=findViewById(R.id.username);
        profile_pic=findViewById(R.id.profile_pic);


        cross.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            Intent exitIntent= new Intent(Profile_Activity.this,MainActivity.class);
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Profile_Activity.this,profile_pic,ViewCompat.getTransitionName(profile_pic));

        startActivity(exitIntent,options.toBundle());
            finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                popup.setContentView(R.layout.setting_popup);
//                final TextInputLayout currPlayout;
//                final TextInputLayout newPlayout;
//                final TextInputLayout againPlayout;
//                final TextInputEditText currpaswrd;
//                final TextInputEditText newpaswrd;
//                final TextInputEditText againpaswrd;
//                Button savePaswrd;
//                currPlayout=popup.findViewById(R.id.EnterCurrPaswrdlayout);
//                newPlayout=popup.findViewById(R.id.EnternewPaswrdlayout);
//                againPlayout=popup.findViewById(R.id.entrpaswrdAgainlayout);
//                currpaswrd=popup.findViewById(R.id.EnterCurrPaswrd);
//                newpaswrd=popup.findViewById(R.id.EnternewPaswrd);
//                againpaswrd=popup.findViewById(R.id.entrpaswrdAgain);
//                savePaswrd=popup.findViewById(R.id.save_paswrd);
//                newpaswrd_string=newpaswrd.getText().toString();
//                currentPaswrd_string=currpaswrd.getText().toString();
//                againnewpaswrd_string=againpaswrd.getText().toString();
//                currpaswrd.setShowSoftInputOnFocus(false);
//                newpaswrd.setShowSoftInputOnFocus(false);
//                againpaswrd.setShowSoftInputOnFocus(false);
//
//
//                savePaswrd.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        if(!currentPaswrd_string.equals("")){
//                            currPlayout.setErrorEnabled(true);
//                            currPlayout.setError("Please enter your current Password");
//                        }
//                        if(!newpaswrd_string.equals("")){
//                            newPlayout.setEnabled(true);
//
//                            newPlayout.setError("Please Enter Your New Password");
//                        }
//                        if(!againnewpaswrd_string.equals(""))
//                        {    againPlayout.setErrorEnabled(true);
//                            againPlayout.setError("Please Confirm Your Password");
//
//                        }
//
//                        Snackbar.make(findViewById(R.id.layout_profile),"Your Password has been saved ",Snackbar.LENGTH_LONG).show();
//
//
////
//
//                    }
//                });
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
//
//
//
//
//                popup.getWindow().setBackgroundDrawableResource(android.R.drawable.screen_background_light);
//                popup.show();


                Intent changePasswordIntent= new Intent(Profile_Activity.this,PasswordChangeActivity.class);
                startActivity(changePasswordIntent);
                finish();

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
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                popup.setContentView(R.layout.aboutus_popup);
//                popup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                popup.show();

            }
        });


        if(PasswordChangeStatus.passwordChangeStatus)
        {

            final Snackbar snackbar = Snackbar.make(findViewById(R.id.layout_profile),"Your Password has been saved ",Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snackbar.dismiss();

                }
            });
            snackbar.show();
            PasswordChangeStatus.passwordChangeStatusfunc(false);

        }



    }


    public void editname(View view)
    {
        nameEditPopup.setContentView(R.layout.edit_username_popup);
        nameEditPopup.setCanceledOnTouchOutside(true);
        newName=nameEditPopup.findViewById(R.id.edit_name);
        newName=nameEditPopup.findViewById(R.id.edit_name);
        save=nameEditPopup.findViewById(R.id.save_btn);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName1;
                newName1=newName.getText().toString();
                username.setText(newName1);
                username.setText(newName1);
                newName.setText("");



                nameEditPopup.dismiss();


            }
        });
        nameEditPopup.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        nameEditPopup.show();

    }





}
