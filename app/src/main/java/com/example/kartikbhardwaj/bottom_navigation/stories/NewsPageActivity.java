package com.example.kartikbhardwaj.bottom_navigation.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

public class NewsPageActivity extends AppCompatActivity {
    private String name,description,imageURL,date,source, url;
    private TextView nameTv, descTv, sourceTv, dateTv;
    private SimpleDraweeView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_news_page);

        nameTv=findViewById(R.id.newsNameTV);
        dateTv=findViewById(R.id.newsDateTV);
        descTv=findViewById(R.id.newsDescTV);
        sourceTv=findViewById(R.id.newsSourceTV);
        image=findViewById(R.id.newsThumbImage);

        name= getIntent().getStringExtra("newsname");
        description= getIntent().getStringExtra("newsdesc");
        imageURL= getIntent().getStringExtra("newsimageurl");
        date=getIntent().getStringExtra("newsdate");
        source=getIntent().getStringExtra("newssource");
        url=getIntent().getStringExtra("newsurl");

        nameTv.setText(name);
        descTv.setText(description);
        sourceTv.setText(source);
        dateTv.setText(date);
        image.setImageURI(imageURL);
    }
}
