package com.cyllide.app.v1.howitworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyllide.app.v1.R;

import androidx.fragment.app.Fragment;

public class Fragment3 extends Fragment {
    private View view;


    ImageView slide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmenthow3, container, false);
        slide=view.findViewById(R.id.imghow3);


        return view;
    }

    public static Fragment3 newInstance() {
        Fragment3 fragment3 = new Fragment3();
        Bundle bundle = new Bundle();
        fragment3.setArguments(bundle);


        return fragment3;
    }
}
