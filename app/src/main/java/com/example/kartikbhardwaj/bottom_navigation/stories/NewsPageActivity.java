package com.example.kartikbhardwaj.bottom_navigation.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class NewsPageActivity extends AppCompatActivity {
    private String name,description,imageURL,date,source, url, author;
    private TextView nameTv, descTv, sourceTv, dateTv, authorTv;
    private SimpleDraweeView image;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_news_page);
        back=findViewById(R.id.newspagebackbutton);
        nameTv=findViewById(R.id.newsNameTV);
        dateTv=findViewById(R.id.newsDateTV);
        descTv=findViewById(R.id.newsDescTV);
        sourceTv=findViewById(R.id.newsSourceTV);
        image=findViewById(R.id.newsThumbImage);
        authorTv=findViewById(R.id.newsAuthorTV);

        name= getIntent().getStringExtra("newsname");
        description= getIntent().getStringExtra("newsdesc");
        imageURL= getIntent().getStringExtra("newsimageurl");
        date=getIntent().getStringExtra("newsdate");
        source=getIntent().getStringExtra("newssource");
        url=getIntent().getStringExtra("newsurl");
        author=getIntent().getStringExtra("newsauthor");
        nameTv.setText(name);
        descTv.setText(description);
        sourceTv.setText(source);
        dateTv.setText(date);
        image.setImageURI(imageURL);
        authorTv.setText(author);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NewsPageActivity.this,StoriesActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
