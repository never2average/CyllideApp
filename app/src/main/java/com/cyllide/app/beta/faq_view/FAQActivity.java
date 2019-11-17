package com.cyllide.app.beta.faq_view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cyllide.app.beta.R;


import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {


    private RecyclerView faq_RV;
    private ImageView crossbutton;
    String questionList[] ={
            "I have won a live quiz, when can I expect my money in my wallet?",
            "How do i withdraw my money?",
            "How does the referral program work?",
            "In what ways can I get more hearts?",
            "Is there any catch after winning my amount in the quiz?",
            "There are many apps on investing, why should I choose Cyllide?",
            "I haven't entered my UPI Id in the field after winning the quiz."};
    String answerList[] ={
            "You can expect it within 7 working days. Once the total prize amount is more than ₹ 20",
            "Go to Profile > Click on cash icon > Enter your UPI ID and click confirm.",
            "For every individual that you refer, you'll gain extra 3 hearts which you can use in the live quiz feature to increase your chances of winning.",
            "Referrals: For each referral, you get 3 hearts.",
            "Absolutely not! The money is all yours!" ,
            "Great Question! Learning is fun, but learning with earning is awesome! We host quizzes " +
            "and contests to encourage individuals to test their skills before jumping into the ruthless stock market.",
            "In case you haven't entered your UPI ID, please contact help@cyllide.com"};

    private List<FaqModal> dummyData() {
        List<FaqModal> data = new ArrayList<>(12);
        for (int i = 0; i < 6; i++) {
            data.add(new FaqModal(questionList[i], answerList[i]));
        }
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
                  onBackPressed();
                }
            });
    }

}
