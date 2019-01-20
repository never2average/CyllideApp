package com.example.kartikbhardwaj.bottom_navigation;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Profile_Activity extends AppCompatActivity {

    ImageButton cross;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
        cross=findViewById(R.id.cross_btn);
        cross.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            Intent exitIntent= new Intent(Profile_Activity.this,MainActivity.class);
            startActivity(exitIntent);
            finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        overridePendingTransition(R.anim.uptodown,R.anim.downtoup);
    }
}
