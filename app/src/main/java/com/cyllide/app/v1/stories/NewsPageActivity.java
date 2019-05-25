package com.cyllide.app.v1.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cyllide.app.v1.R;
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
        nameTv=findViewById(R.id.newsNameTV);
        dateTv=findViewById(R.id.newsDateTV);
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
        sourceTv.setText(source);
        dateTv.setText(date);
        image.setImageURI(imageURL);
        if(author.equals("null")){
            authorTv.setText("");
        }
        else{
            authorTv.setText(author);
        }
    }
}