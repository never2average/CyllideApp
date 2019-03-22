package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.tabs.TabLayout;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MonthlyActivity extends AppCompatActivity {

    private int curr_selection = 0;
    private String[] descs = {"Small cap is a term used to classify companies with a relatively small market capitalization. A company’s market capitalization is the market value of its outstanding shares. In India, normally a company below market capitalization of Rs.5000 crores is classified as small cap company.",
    "A company’s market capitalization is the market value of its outstanding shares. In India, normally a company with market capitalization above Rs.5000 crores and less than Rs.20000 crores is considered as mid cap company.",
    "Large cap is a term used to classify companies with a relatively large market capitalization. A company's market capitalization is the market value of its outstanding shares. In India, normally companies with the market capitalization higher than Rs.20,000 crores is considered as Large cap companies.",
    "A company’s market capitalization is the market value of its outstanding shares. A company whose market cap is among the top 500 companies traded in NSE is classified under Nifty 500"};

    private LabelToggle smallCapButton;
    private LabelToggle midCapButton;
    private LabelToggle largeCapButton;
    private LabelToggle niftyButton;
    private TextView descTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ImageView imgView=findViewById(R.id.contestbackbutton);
        smallCapButton = findViewById(R.id.smallcap_button);
        midCapButton = findViewById(R.id.midcap_button);
        largeCapButton = findViewById(R.id.largecap_button);
        niftyButton = findViewById(R.id.nifty_button);

        descTV = findViewById(R.id.desc_tv);
        updateDisplay();

        smallCapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection = 0;
                updateDisplay();
            }
        });

        midCapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection=1;
                updateDisplay();
            }
        });

        largeCapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection=2;
                updateDisplay();
            }
        });

        niftyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection=3;
                updateDisplay();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));

        

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateDisplay(){
        descTV.setText(descs[curr_selection]);
    }

}
