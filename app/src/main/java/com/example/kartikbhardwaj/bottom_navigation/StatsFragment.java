package com.example.kartikbhardwaj.bottom_navigation;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StatsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.stats_fragment,null);

        AnimatedPieView mAnimatedPieView = view.findViewById(R.id.win_perc);
        AnimatedPieViewConfig config =  new  AnimatedPieViewConfig ();
        config.startAngle(-90).addData(
                new  SimplePieInfo ( 75.0f , R.color.primary_light_max,"Win %")).addData (
                        new SimplePieInfo( 25.0f ,R.color.primary_dark_max, "Loss %" )).duration( 2000 );
        mAnimatedPieView.applyConfig (config);
        mAnimatedPieView.start();

        return view;
    }
}
