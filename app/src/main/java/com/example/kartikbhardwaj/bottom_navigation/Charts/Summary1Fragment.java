package com.example.kartikbhardwaj.bottom_navigation.Charts;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kartikbhardwaj.bottom_navigation.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


public class Summary1Fragment extends Fragment {



    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        Log.e("Lol","Made up");
        return inflater.inflate(R.layout.fragment_summary1, container, false);


    }



    @Override
    public void onStart() {
        super.onStart();
    }
}
