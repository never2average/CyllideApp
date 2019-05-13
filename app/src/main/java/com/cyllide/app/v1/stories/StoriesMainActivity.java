package com.cyllide.app.v1.stories;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;


public class StoriesMainActivity extends AppCompatActivity {

    ImageView back;
    LinearLayout loadingLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        Log.d("StoriesActivity","onCreate");
        loadingLayout = findViewById(R.id.activity_stories_loading_layout);
        loadingLayout.setVisibility(View.VISIBLE);
        back = findViewById(R.id.newsbackbutton);

        final customViewPager viewPager =
                 findViewById(R.id.view_pager);
        final PagerAdapter adapter = new StoriesPagerAdapter
                (getSupportFragmentManager(),
                        2);
        viewPager.setAdapter(adapter);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StoriesMainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        }


    @Override
    public void onBackPressed(){
        Intent returnHome = new Intent(this,MainActivity.class);
        startActivity(returnHome);
        finish();
    }


}

