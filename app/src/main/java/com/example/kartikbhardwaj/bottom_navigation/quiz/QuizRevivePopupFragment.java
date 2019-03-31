package com.example.kartikbhardwaj.bottom_navigation.quiz;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kartikbhardwaj.bottom_navigation.AppConstants;
import com.example.kartikbhardwaj.bottom_navigation.R;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;

public class QuizRevivePopupFragment extends DialogFragment{
    CardView revivalYes,revivalNo;
    TextView coinsLeft;

    public QuizRevivePopupFragment() {
    }

    public static QuizRevivePopupFragment newInstance(String title){
        QuizRevivePopupFragment frag = new QuizRevivePopupFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;

    }

    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.quiz_revival_xml, container, false);




        // Do all the stuff to initialize your custom view

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        coinsLeft = view.findViewById(R.id.quiz_revival_coins_left);
        revivalYes = view.findViewById(R.id.quiz_revivial_yes_card_view);
        revivalNo = view.findViewById(R.id.quiz_revival_no_card_view);
        coinsLeft.setText(Integer.toString(AppConstants.coins));

        revivalYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Yes",Toast.LENGTH_SHORT).show();
                QuizActivity.hasRevive = true;
                dismiss();
            }
        });

        revivalNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizActivity.hasRevive = false;
                Toast.makeText(view.getContext(),"No",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });



    }
}
