package com.example.kartikbhardwaj.bottom_navigation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.kartikbhardwaj.bottom_navigation.Portfolio.MyPortfolio;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity{


    Toolbar toolbar;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo=findViewById(R.id.logo);
        SharedPreferences.Editor editor = getSharedPreferences("AUTHENTICATION", MODE_PRIVATE).edit();
//        editor.putString("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiQnVyam9zZSIsImV4cCI6MTU4MjU1NzQzNH0.M9K5ZcW515hWwBe3gNHdVB6AhQRpubfuQFn7xvrpLNg");
        editor.putString("token","Not found!");
        editor.apply();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        loadfragment(new HomeFragment());

        toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
    }

        public boolean loadfragment(Fragment fragment) {
            if (fragment != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
                return true;
            }
            return false;


        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pic:
                Intent profileIntent=new Intent(this,Profile_Activity.class);
                startActivity(profileIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void switchToPortfolioActivity(View view){
        Intent portfolioIntent =new Intent(MainActivity.this,MyPortfolio.class);
        startActivity(portfolioIntent);
    }
}
