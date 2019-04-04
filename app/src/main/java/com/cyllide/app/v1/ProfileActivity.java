package com.cyllide.app.v1;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.v1.forum.ForumActivity;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    ImageButton cross;
    CircleImageView profilePic;
    TextView username, quizzesWon, quizzesParticipated, numReferrals, numPosts, numUpvotes, numHearts;
    AnimatedPieView contestWinPerc;
    RequestQueue requestQueue;
    Map<String,String> profileMap = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Fresco.initialize(this);

        cross=findViewById(R.id.view_only_cross_btn);
        username=findViewById(R.id.view_only_profile_username);
        profilePic=findViewById(R.id.view_only_profile_pic);
        username = findViewById(R.id.view_only_profile_username);
        quizzesWon = findViewById(R.id.view_only_profile_quiz_wins);
        quizzesParticipated = findViewById(R.id.view_only_profile_quizzes);
        numReferrals = findViewById(R.id.view_only_profile_referrals);
        numPosts = findViewById(R.id.view_only_profile_posts);
        numUpvotes = findViewById(R.id.view_only_profile_upvotes);
        contestWinPerc = findViewById(R.id.view_only_contest_win_perc);
        numHearts = findViewById(R.id.view_only_profile_num_coins);

        cross.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
            Intent exitIntent= new Intent(ProfileActivity.this, ForumActivity.class);
            startActivity(exitIntent);
            finish();
            }
        });
        fillAllViewsVolley();
    }

    void fillAllViewsVolley() {
        requestQueue = Volley.newRequestQueue(ProfileActivity.this);
        profileMap.put("token",AppConstants.token);
        profileMap.put("username",AppConstants.viewUsername);
        String url = getResources().getString(R.string.apiBaseURL) + "profileinfo";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response).getJSONObject("data");
                    username.setText(jsonResponse.getString("userName"));
                    quizzesWon.setText(jsonResponse.getString("quizzesWon"));
                    quizzesParticipated.setText(jsonResponse.getString("quizzesWon"));
                    numReferrals.setText(jsonResponse.getString("numberReferrals"));
                    numPosts.setText(String.valueOf(jsonResponse.getInt("questionsAsked")+jsonResponse.getInt("questionsAnswered")));
                    numUpvotes.setText(jsonResponse.getString("numUpvotes"));
                    numHearts.setText(jsonResponse.getString("numCoins"));
                    AnimatedPieViewConfig config =  new  AnimatedPieViewConfig ().drawText(true).textSize(40);
                    double contestsPart = jsonResponse.getDouble("contestsParticipated");
                    double contestsWon = jsonResponse.getDouble("contestsWon");
                    double winPercent = contestsWon/contestsPart;
                    double lostPercent = 1 - winPercent;
                    config.startAngle(-90).addData(
                            new SimplePieInfo((float) winPercent, ContextCompat.getColor(ProfileActivity.this, R.color.progressgreen),"Win %")).addData (
                            new SimplePieInfo( (float) lostPercent, ContextCompat.getColor(ProfileActivity.this, R.color.progressred), "Loss %" )).duration(1500);
                    contestWinPerc.applyConfig (config);
                    contestWinPerc.start();
                    RequestOptions cropOptions = new RequestOptions().override(100,100);
                    Glide.with(ProfileActivity.this).load(jsonResponse.getString("profilePic")).apply(cropOptions).into(profilePic);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return profileMap;
            }
        };
        requestQueue.add(stringRequest);
    }
}
