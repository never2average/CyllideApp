package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.navigation.NavigationView;


public class PortfolioActivity extends AppCompatActivity {

    ActionBarDrawerToggle t;
    NavigationView nv;
     DrawerLayout dl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);


        androidx.appcompat.widget.Toolbar toolbar =findViewById(R.id.tool_bar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon);




        dl=(DrawerLayout)findViewById(R.id.dl);
       // dl.addDrawerListener(t);
        //t.syncState();


        nv=(NavigationView)findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                switch (id)
                {
                    case R.id.Nav1:
                        Toast.makeText(PortfolioActivity.this,"Option1",Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        dl.closeDrawers();
                    case R.id.Nav2:
                        Toast.makeText(PortfolioActivity.this,"Option2",Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        dl.closeDrawers();


                    case R.id.Nav3:
                        Toast.makeText(PortfolioActivity.this,"Option3",Toast.LENGTH_SHORT).show();
                        item.setChecked(true);
                        dl.closeDrawers();


                    default:
                        return true;
                }

            }
        });




    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dl.openDrawer(GravityCompat.START);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}
