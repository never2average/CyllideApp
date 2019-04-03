package com.cyllide.app.v1.howitworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyllide.app.v1.R;

import androidx.fragment.app.Fragment;

public class Fragment7 extends Fragment {
    private View view;


    ImageView slide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmenthow7, container, false);
        slide=view.findViewById(R.id.imghow7);

        return view;
    }

    public static Fragment7 newInstance() {
        Fragment7 fragment7 = new Fragment7();
        Bundle bundle = new Bundle();
        fragment7.setArguments(bundle);

        return fragment7;
    }
}
