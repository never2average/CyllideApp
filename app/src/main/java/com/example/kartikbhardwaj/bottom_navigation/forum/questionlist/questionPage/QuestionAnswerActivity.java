package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kartikbhardwaj.bottom_navigation.R;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerActivity extends AppCompatActivity {

	RecyclerView ansRecyclerView;
	String question;
	TextView questionDetail, questionTitle;
	String answeredBy[] = {"answer1","answer2","answer3","answer4"};
	String answer[] = {"This is the answer to the first question.","This is the answer two the second question. This is a long answer. Currently residing in the code itself.","answer3","answer4"};
	String dateStamp[] = {"date1","date2","date3","date4"};

	private List<QuestionAnswerModel> dummyData() {
		List<QuestionAnswerModel> data = new ArrayList<>(5);
		for (int i = 0; i < 4; i++) {
			data.add(new QuestionAnswerModel(answer[i], answeredBy[i], dateStamp[i]));
		}
		return data;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question_answer);
		ansRecyclerView = findViewById(R.id.ansRV);
		questionDetail = findViewById(R.id.questionDetailText);
		questionTitle = findViewById(R.id.questionTitle);
		question = getIntent().getStringExtra("question title");
		questionTitle.setText(question);
		List<QuestionAnswerModel> answers = dummyData();
		QuestionAnswerAdapter adapter = new QuestionAnswerAdapter(answers);
		ansRecyclerView.setAdapter(adapter);
		ansRecyclerView.setHasFixedSize(true);
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
		ansRecyclerView.setLayoutManager(layoutManager);


	}
}
