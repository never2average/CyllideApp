package com.example.kartikbhardwaj.bottom_navigation.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuizFragment extends Fragment {

    TextView mainQuestion, optionA, optionB, optionC, optionD;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        Intent intent = getI;
        View view=inflater.inflate(R.layout.fragment_quiz_question_page,null);
        mainQuestion = view.findViewById(R.id.question_text);
        optionA = view.findViewById(R.id.optionA);
        optionB = view.findViewById(R.id.optionB);
        optionC = view.findViewById(R.id.optionC);
        optionD = view.findViewById(R.id.optionD);




        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
