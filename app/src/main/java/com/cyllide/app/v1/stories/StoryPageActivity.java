package com.cyllide.app.v1.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class StoryPageActivity extends AppCompatActivity {
    private String name;
    private String description;
    private TextView nameTv, descTv;
    private SimpleDraweeView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("StoriesPageActivity","Oncreate");
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_story_page);

        nameTv=findViewById(R.id.storyNameTV);
        descTv=findViewById(R.id.storyDescTV);
        image=findViewById(R.id.storyThumbImage);

        name= getIntent().getStringExtra("storyname");
        description= getIntent().getStringExtra("storydesc");
        String imageURL = getIntent().getStringExtra("storyurl");

        nameTv.setText(name);
        descTv.setText(description);
        image.setImageURI(imageURL);

    }



}
