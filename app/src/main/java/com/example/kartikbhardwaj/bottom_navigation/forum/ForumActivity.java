package com.example.kartikbhardwaj.bottom_navigation.forum;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListAdapter;
import com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.QuestionListModel;

import java.util.ArrayList;
import java.util.List;

public class ForumActivity extends AppCompatActivity {


    RecyclerView forumRV;
    ArrayList<String> tags;

    private List<QuestionListModel> dummyData() {
        tags = new ArrayList<>();
        tags.add("Finance");
        tags.add("Business");
        List<QuestionListModel> questionList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            questionList.add(new QuestionListModel("How do stock exchanges work?",0,tags));
        }
        return questionList;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        forumRV=findViewById(R.id.topquesrecycler);
        forumRV.setLayoutManager(new LinearLayoutManager(this));
        QuestionListAdapter questionListAdapter = new QuestionListAdapter(dummyData());
        forumRV.setAdapter(questionListAdapter);



    }
}
