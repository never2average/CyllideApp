package com.cyllide.app.beta;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ReferralFragment extends Fragment {

    Button referralButton;
    ImageView crossbtn;
    TextView referralText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.referral_fragment,null);
   }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        referralButton = view.findViewById(R.id.invitebutton);
        crossbtn=view.findViewById(R.id.crossbtn);
        referralText = view.findViewById(R.id.referral_text_view);
        referralText.setText(AppConstants.referral);
        final Context context = getContext();
        crossbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeFragment fragment = new HomeFragment();
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        Fresco.initialize(getContext());
        final Activity activity = getActivity();

        referralButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Join me on Cyllide ,a financial services app.Enter my code *"+ AppConstants.referral+ "* and get 4 hearts. https://play.google.com/store/apps/details?id=com.cyllide.app.beta");
                sendIntent.setType("text/plain");
                startActivityForResult(sendIntent, 1);
            }
        });
    }
}
