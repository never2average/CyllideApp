package com.cyllide.app.v1.howitworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyllide.app.v1.R;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    private View view;


    ImageView slide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmenthow1, container, false);
        slide=view.findViewById(R.id.imghow1);


        return view;
    }

    public static Fragment1 newInstance() {
        Fragment1 fragment1 = new Fragment1();
        Bundle bundle = new Bundle();
        fragment1.setArguments(bundle);

        return fragment1;
    }
}
