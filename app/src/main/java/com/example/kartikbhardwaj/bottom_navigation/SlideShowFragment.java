package com.example.kartikbhardwaj.bottom_navigation;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



public class SlideShowFragment extends Fragment {
    private ViewFlipper viewFlipper;
    private float initialXPoint;
    int image[]={R.drawable.ss_page_1,R.drawable.ss_page_2};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_slide_show,null);

        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        viewFlipper = (ViewFlipper) view.findViewById(R.id.myflipper);

        for (int i = 0; i < image.length; i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(image[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            viewFlipper.addView(imageView);
        }
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialXPoint = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        float finalx = event.getX();
                        if (initialXPoint > finalx) {
                            if (viewFlipper.getDisplayedChild() == image.length)
                                break;
                            viewFlipper.showNext();
                        } else {
                            if (viewFlipper.getDisplayedChild() == 0)
                                break;
                            viewFlipper.showPrevious();
                        }
                        break;
                }
                return false;
            }
        });
        return view;
    }
}