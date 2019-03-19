package com.example.kartikbhardwaj.bottom_navigation.forum.questionlist.questionPage;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kartikbhardwaj.bottom_navigation.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class QuestionAnswerActivity extends AppCompatActivity {

	FloatingActionMenu contributeMenu;
	FloatingActionButton commentButton, answerButton;
	RecyclerView ansRecyclerView;
	String question;
	TextView questionDetail, questionTitle;
	String answeredBy[] = {"answer1","answer2","answer3","answer4"};
	String answer[] = {"This is the answer to the first question.","This is the answer two the second question. This is a long answer. Currently residing in the code itself.","answer3","answer4"};
	String dateStamp[] = {"date1","date2","date3","date4"};
	final Context c = this;

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

		contributeMenu = findViewById(R.id.answerMenu);
		commentButton = findViewById(R.id.commentOption);
		answerButton = findViewById(R.id.answerOption);
		contributeMenu.setClosedOnTouchOutside(true);

		commentButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//TODO something when floating action menu first item clicked
				LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
				View mView = layoutInflaterAndroid.inflate(R.layout.dialog_post_answer, null);
				AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
				alertDialogBuilderUserInput.setView(mView);
				final TextView title= mView.findViewById(R.id.post_answer);
				String text="Add a comment";
				title.setText(text);
				final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
				alertDialogBuilderUserInput
						.setCancelable(false)
						.setPositiveButton("Send", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogBox, int id) {
								// ToDo get user input here
							}
						})

						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialogBox, int id) {
										dialogBox.cancel();
									}
								});

				AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
				alertDialogAndroid.show();

			}
		});
		answerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//TODO something when floating action menu second item clicked
				LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
				View mView = layoutInflaterAndroid.inflate(R.layout.dialog_post_answer, null);
				AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
				alertDialogBuilderUserInput.setView(mView);
				final TextView title= mView.findViewById(R.id.post_answer);
				String text="Add an answer";
				title.setText(text);
				final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
				alertDialogBuilderUserInput
						.setCancelable(false)
						.setPositiveButton("Send", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialogBox, int id) {
								// ToDo get user input here
							}
						})

						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialogBox, int id) {
										dialogBox.cancel();
									}
								});

				AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
				alertDialogAndroid.show();
			}
		});

		AnimatorSet set = new AnimatorSet();

		ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(contributeMenu.getMenuIconView(), "scaleX", 1.0f, 0.2f);
		ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(contributeMenu.getMenuIconView(), "scaleY", 1.0f, 0.2f);

		ObjectAnimator scaleInX = ObjectAnimator.ofFloat(contributeMenu.getMenuIconView(), "scaleX", 0.2f, 1.0f);
		ObjectAnimator scaleInY = ObjectAnimator.ofFloat(contributeMenu.getMenuIconView(), "scaleY", 0.2f, 1.0f);

		scaleOutX.setDuration(50);
		scaleOutY.setDuration(50);

		scaleInX.setDuration(150);
		scaleInY.setDuration(150);

		scaleInX.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationStart(Animator animation) {
				contributeMenu.getMenuIconView().setImageResource(contributeMenu.isOpened()
						? R.drawable.ic_create_24dp : R.drawable.close_icon);
			}
		});

		set.play(scaleOutX).with(scaleOutY);
		set.play(scaleInX).with(scaleInY).after(scaleOutX);
		set.setInterpolator(new OvershootInterpolator(2));

		contributeMenu.setIconToggleAnimatorSet(set);


	}
}
