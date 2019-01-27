package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.kartikbhardwaj.bottom_navigation.HomeFragment;
import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.StatsFragment;
import com.google.android.material.navigation.NavigationView;


public class PortfolioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);


        androidx.appcompat.widget.Toolbar toolbar =findViewById(R.id.tool_bar1);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);
       // actionBar.setHomeAsUpIndicator(R.drawable.navigation_drawer_icon);





        AvailableStocksFragment fragment=new AvailableStocksFragment();
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container1,fragment);
        fragmentTransaction.commit();

//      if(  MenuItem.getItemId()==R.id.item1)
//      {
//          item.setChecked(true);
//      }

       // dl.addDrawerListener(t);
        //t.syncState();





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.item1:
                AvailableStocksFragment fragment1=new AvailableStocksFragment();
                FragmentManager fragmentManager=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.container1,fragment1);
                fragmentTransaction.commit();


             return true;
            case R.id.item2:

                PortfolioPositionsFragment fragment=new PortfolioPositionsFragment();
                FragmentManager fragmentManager1=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                fragmentTransaction1.add(R.id.container1,fragment);
                fragmentTransaction1.commit();
                item.setChecked(true);

                return true;

            case R.id.item3:

                PendingOrdersFragment fragment3=new PendingOrdersFragment();
                FragmentManager fragmentManager3=getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                fragmentTransaction3.add(R.id.container1,fragment3);
                fragmentTransaction3.commit();
                item.setChecked(true);

                return true;



        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.portfolioappbaritems,menu);
        MenuItem item=menu.findItem(R.id.item1);
        item.setChecked(true);

        return true;
    }

    public boolean  loadfragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;


    }


}
