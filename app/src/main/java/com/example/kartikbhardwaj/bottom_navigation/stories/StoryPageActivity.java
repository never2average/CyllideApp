package com.example.kartikbhardwaj.bottom_navigation.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class StoryPageActivity extends AppCompatActivity {
    private String name,description,imageURL;
    private TextView nameTv, descTv;
    private SimpleDraweeView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_story_page);

        nameTv=findViewById(R.id.storyNameTV);
        descTv=findViewById(R.id.storyDescTV);
        image=findViewById(R.id.storyThumbImage);

        name= getIntent().getStringExtra("storyname");
        description= getIntent().getStringExtra("storydesc");
        imageURL= getIntent().getStringExtra("storyurl");

        nameTv.setText(name);
        descTv.setText(description);
        image.setImageURI(imageURL);

    }
}
