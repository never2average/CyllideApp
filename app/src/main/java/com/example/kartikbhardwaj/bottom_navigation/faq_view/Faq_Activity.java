package com.example.kartikbhardwaj.bottom_navigation.faq_view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.kartikbhardwaj.bottom_navigation.MainActivity;
import com.example.kartikbhardwaj.bottom_navigation.stories.NewsAdapter;
import com.example.kartikbhardwaj.bottom_navigation.stories.NewsModel;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.example.kartikbhardwaj.bottom_navigation.R;


import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class Faq_Activity extends AppCompatActivity {


    private RecyclerView faq_RV;
    private ImageView crossbutton;
    String questionList[] ={"I have won a contest when can I expect my money in my wallet?","How does your Referral program work? Can I use the features of the app for free?","What is the point of rating traders for your contests?","What metric do you use to rate top traders?","Is there any catch after winning my amount in the quiz?","There are many apps on investing, why should I choose TradeRoyale?","Why TradeRoyale?"};
    String answerList[] ={"You can expect it within 2-3 working days.","Even though your entry to the first quiz is free, you need to refer a friend to enter" +
            "the next quiz contest. Once you reach 10 referrals your entry to the quiz through" +
            "your account is free for life. And yes, the rest of the features are free at the moment.","We believe in working with the best talent. Hopefully sometime in near future we " +
            "can work with our selected top traders to make the world of investing a better place","There are plenty. But the main ones are their RC rank which rates traders according to" +
            "their consistency and Global rank which takes the number wins into account.","Absolutely not! The money is all yours!","Great Question! Learning is fun, but learning with earning is awesome! We host quizzes " +
            "and contests to encourage individuals to test their skills before jumping into the ruthless stock market.","Simple! To increase the financial literacy of the country and bring in more people to the world of investing. We believe this can be achieved only by incentivizing the end user" +
            "and giving them enough confidence to be the master of their own money."};

    private List<FaqModal> dummyData() {
        List<FaqModal> data = new ArrayList<>(12);
        for (int i = 0; i < 7; i++) {
            data.add(new FaqModal(questionList[i], answerList[i]));
        }//data is the list of objects to be set in the list item
        return data;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_);

        faq_RV = findViewById(R.id.faq_activity);
        crossbutton=findViewById(R.id.crossbtn);
        final Context context = Faq_Activity.this;
        faq_RV.setHasFixedSize(true);
        faq_RV.setLayoutManager(new LinearLayoutManager(context));
        List<FaqModal> data = dummyData();
            final FaqAdapter mAdapter = new FaqAdapter(data);
            faq_RV.setAdapter(mAdapter);

            crossbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mainActivityInt=new Intent(Faq_Activity.this, MainActivity.class);
                    startActivity(mainActivityInt);
                    finish();
                }
            });
    }

}
