package com.cyllide.app.v1.faq_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;


import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {


    private RecyclerView faq_RV;
    private ImageView crossbutton;
    String questionList[] ={
            "I have won a contest when can I expect my money in my wallet?",
            "What metric do you use to rate top traders?",
            "Is there any catch after winning my amount in the quiz?",
            "There are many apps on investing, why should I choose Cyllide?",
            "Why Cyllide?",
            "I haven't entered my UPI Id in the field after winning the quiz?"};
    String answerList[] ={
            "You can expect it within 7 working days.",
            "There are plenty. But the main ones are their RC rank which rates traders according to" +
            "their consistency and Global rank which takes the number wins into account.",
            "Absolutely not! The money is all yours!",
            "Great Question! Learning is fun, but learning with earning is awesome! We host quizzes " +
            "and contests to encourage individuals to test their skills before jumping into the ruthless stock market.",
            "Simple! To increase the financial literacy of the country and bring in more people to the world of investing. We believe this can be achieved only by incentivizing the end user" +
            "and giving them enough confidence to be the master of their own money.",
            "In case you haven't entered your UPI ID, please contact help@cyllide.com"};

    private List<FaqModal> dummyData() {
        List<FaqModal> data = new ArrayList<>(12);
        for (int i = 0; i < 6; i++) {
            data.add(new FaqModal(questionList[i], answerList[i]));
        }//data is the list of objects to be set in the list item
        return data;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faq_RV = findViewById(R.id.faq_activity);
        crossbutton=findViewById(R.id.crossbtn);
        final Context context = FAQActivity.this;
        faq_RV.setHasFixedSize(true);
        faq_RV.setLayoutManager(new LinearLayoutManager(context));
        List<FaqModal> data = dummyData();
            final FaqAdapter mAdapter = new FaqAdapter(data);
            faq_RV.setAdapter(mAdapter);

            crossbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainActivityInt=new Intent(FAQActivity.this, MainActivity.class);
                    startActivity(mainActivityInt);
                    finish();
                }
            });
    }

}
