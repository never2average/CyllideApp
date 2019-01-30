package com.example.kartikbhardwaj.bottom_navigation.howitworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kartikbhardwaj.bottom_navigation.R;

import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {
    private View view;


    ImageView slide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmenthow4, container, false);
        slide=view.findViewById(R.id.imghow4);


        return view;
    }

    public static Fragment4 newInstance() {
        Fragment4 fragment4 = new Fragment4();
        Bundle bundle = new Bundle();
        fragment4.setArguments(bundle);

        return fragment4;
    }
}
