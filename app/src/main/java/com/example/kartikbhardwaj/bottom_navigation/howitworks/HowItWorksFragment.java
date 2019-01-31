package com.example.kartikbhardwaj.bottom_navigation.howitworks;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static android.view.View.GONE;

public class HowItWorksFragment extends Fragment {
    static FloatingActionButton fabSkip;
    static ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getContext());
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        MainActivity.bottomNavigationView.setVisibility(GONE);
        return inflater.inflate(R.layout.activity_how_it_works,null);
    }
    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabSkip=view.findViewById(R.id.fabSkip);
        viewPager = (ViewPager) view.findViewById(R.id.how_it_works_pager);
        viewPager.setAdapter(new HowItWorksAdapter(getChildFragmentManager()));

        fabSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tab_layout_how);
        tabLayout.setupWithViewPager(viewPager, true);
    }


}
