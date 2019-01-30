package com.example.kartikbhardwaj.bottom_navigation.howitworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kartikbhardwaj.bottom_navigation.R;

import androidx.fragment.app.Fragment;

public class Fragment0 extends Fragment {
    private View view;


    ImageView slide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmenthow0, container, false);
        slide=view.findViewById(R.id.imghow0);


        return view;
    }

    public static Fragment0 newInstance() {
        Fragment0 fragment0 = new Fragment0();
        Bundle bundle = new Bundle();
        fragment0.setArguments(bundle);

        return fragment0;
    }
}
