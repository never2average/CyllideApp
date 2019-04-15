package com.cyllide.app.v1.intro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class IntroActivity extends AppCompatActivity {

    private boolean introCompleted = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        final MaterialButton skipButton = findViewById(R.id.intro_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                introCompleted = true;
                finish();
            }
        });
        final IntroPagerAdapter pagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.intro_screen_pager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int selectedIndex = 0;
            boolean mPageEnd = false;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if( mPageEnd && position == selectedIndex) {
                    finish();
                }
            }

            @Override
            public void onPageSelected(int position) {
                selectedIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if(selectedIndex == pagerAdapter.getCount() - 1)
                {   skipButton.setText("Finish");
                    introCompleted = true;
                    mPageEnd = true;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(introCompleted){
            SharedPreferences.Editor sharedPreferencesEditor =
                    PreferenceManager.getDefaultSharedPreferences(this).edit();
            sharedPreferencesEditor.putBoolean(
                    MainActivity.COMPLETED_TUTORIAL_PREF_NAME, true);
            sharedPreferencesEditor.apply();
        }
    }
}
