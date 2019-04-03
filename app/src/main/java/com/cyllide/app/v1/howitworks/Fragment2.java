package com.cyllide.app.v1.howitworks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyllide.app.v1.R;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    private View view;


    ImageView slide;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragmenthow2, container, false);
        slide=view.findViewById(R.id.imghow2);
        return view;
    }

    public static Fragment2 newInstance() {
        Fragment2 fragment2 = new Fragment2();
        Bundle bundle = new Bundle();
        fragment2.setArguments(bundle);
        return fragment2;
    }
}
