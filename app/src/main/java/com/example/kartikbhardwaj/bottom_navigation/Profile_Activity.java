package com.example.kartikbhardwaj.bottom_navigation;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_Activity extends AppCompatActivity {

    ImageButton cross;
    Dialog nameEditPopup;
    Button save;
    EditText newName;
    TextView username;
    CircleImageView profile_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);


        nameEditPopup=new Dialog(this);
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
