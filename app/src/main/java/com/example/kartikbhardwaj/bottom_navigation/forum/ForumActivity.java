package com.example.kartikbhardwaj.bottom_navigation.forum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.forum.askquestion.AskQuestion;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListAdapter;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {


    RecyclerView forumRV;
    FloatingActionButton askQuestion;

    private List<QuestionListModel> dummyData() {
        List<QuestionListModel> questionList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            questionList.add(new QuestionListModel("How do stock exchanges work?"));
        }
        return questionList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        askQuestion = findViewById(R.id.askquestion);
        forumRV=findViewById(R.id.topquesrecycler);
        forumRV.setLayoutManager(new LinearLayoutManager(this));
        QuestionListAdapter questionListAdapter = new QuestionListAdapter(dummyData());
        forumRV.setAdapter(questionListAdapter);

        askQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent questioningIntent = new Intent(ForumActivity.this, AskQuestion.class);
                startActivity(questioningIntent);
            }
        });

    }
}
