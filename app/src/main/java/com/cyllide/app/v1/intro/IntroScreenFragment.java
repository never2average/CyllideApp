package com.cyllide.app.v1.intro;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;

import androidx.fragment.app.Fragment;

public class IntroScreenFragment extends Fragment {

    public static final String ARG_IMAGE = "image_resource";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_intro_screen, container, false);
        Bundle args = getArguments();
        ((ImageView)rootView.findViewById(R.id.imageview_intro_screen))
                .setImageResource(args.getInt(ARG_IMAGE));
        return rootView;
    }

}
