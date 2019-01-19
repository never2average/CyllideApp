package com.example.kartikbhardwaj.bottom_navigation;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class Profile_Activity extends AppCompatActivity {
LinearLayout l1;
Animation downtoup;
ImageButton cross;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);
  l1=findViewById(R.id.layout_profile);
   downtoup=AnimationUtils.loadAnimation(this,R.anim.downtoup);
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
}
