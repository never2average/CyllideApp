package com.cyllide.app.v1;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cyllide.app.v1.portfolio.PortfolioGameHomeActivity;
import com.cyllide.app.v1.forum.ForumActivity;
import com.cyllide.app.v1.quiz.QuizRulesActivity;
import com.cyllide.app.v1.stories.StoriesMainActivity;
import com.facebook.drawee.backends.pipeline.Fresco;

import com.google.android.material.card.MaterialCardView;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Map;

public class HomeFragment extends Fragment {
    Calendar startTime = Calendar.getInstance();
    Dialog quizPopup;

    TextView timer;
    ImageView networkTower;
    View content;
    TextView retry_button;

    TextView greetingsTV, winningsTV, pointsTV;
    de.hdodenhof.circleimageview.CircleImageView profilePic;
    Map<String,String> homepageDataHeaders = new ArrayMap<>();
    RequestQueue homepageQueue;


    @Override
    public void onResume() {
        super.onResume();
    }

    MaterialCardView stories, portfolios, quiz, forum;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Fresco.initialize(getContext());

        return inflater.inflate(R.layout.home_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        startTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR_OF_DAY) + 2);


        super.onViewCreated(view, savedInstanceState);
        stories = view.findViewById(R.id.storiescard);
        portfolios = view.findViewById(R.id.portfoliocard);
        quiz = view.findViewById(R.id.quizcard);
        forum = view.findViewById(R.id.forumcard);
        quizPopup = new Dialog(view.getContext());
        networkTower=view.findViewById(R.id.network_tower);
        retry_button=view.findViewById(R.id.retry_button);
        content=view;
        greetingsTV = view.findViewById(R.id.home_fragment_greetings);
        winningsTV = view.findViewById(R.id.money_won);
        pointsTV = view.findViewById(R.id.points_collected);
        profilePic = view.findViewById(R.id.profile_pic_container);


    }

    @Override
    public void onStart() {
        super.onStart();



        final Activity activity = getActivity();
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), StoriesMainActivity.class);
                getContext().startActivity(intent);
                getActivity().finish();
            }
        });
        portfolios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

                if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
                    Toast.makeText(getContext(), "Poor Network Connection", Toast.LENGTH_LONG).show();
                    content.findViewById(R.id.main_content).setVisibility(View.GONE);
                    content.findViewById(R.id.network_retry_layout).setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Internet Connection Lost", Toast.LENGTH_LONG).show();


                } else {
                    Intent portfolioIntent = new Intent(getContext(), PortfolioGameHomeActivity.class);
                    startActivity(portfolioIntent);
                    getActivity().finish();
                }
            }

        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConnectivityManager conMgr = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
                if (ConnectionStatus.connectionstatus) {
                    SharedPreferences sharedPreferences = getContext().getSharedPreferences("AUTHENTICATION", Context.MODE_PRIVATE);
                    String token = sharedPreferences.getString("token", "Not found!");
                    Intent intent;
                    intent = new Intent(getContext(), QuizRulesActivity.class);

                    getContext().startActivity(intent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Internet Connection Lost", Toast.LENGTH_LONG).show();
                    content.findViewById(R.id.main_content).setVisibility(View.GONE);
                    content.findViewById(R.id.network_retry_layout).setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Internet Connection Lost", Toast.LENGTH_LONG).show();


                }
            }

        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),ProfileActivity.class);
                intent.putExtra("Editable",true);
                getContext().startActivity(intent);
                getActivity().finish();

            }
        });




        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConnectionStatus.connectionstatus) {
                    Intent intent = new Intent(getContext(), ForumActivity.class);
                    getContext().startActivity(intent);
                    getActivity().finish();
                } else {


                   content.findViewById(R.id.main_content).setVisibility(View.GONE);
                   content.findViewById(R.id.network_retry_layout).setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Internet Connection Lost", Toast.LENGTH_LONG).show();

                }
            }
        });

        retry_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ConnectionStatus.connectionstatus) {
                  content.findViewById(R.id.network_retry_layout).setVisibility(View.GONE);
                  content.findViewById(R.id.main_content).setVisibility(View.VISIBLE);
                } else {


                    content.findViewById(R.id.main_content).setVisibility(View.GONE);
                    content.findViewById(R.id.network_retry_layout).setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(), "Internet Connection Lost", Toast.LENGTH_LONG).show();

                }

            }
        });

        fetchDataVolley();
    }

    void fetchDataVolley() {
        String url = getResources().getString(R.string.apiBaseURL)+"info/homepage";
        homepageQueue = Volley.newRequestQueue(getContext());
        homepageDataHeaders.put("token", AppConstants.token);
        StringRequest homepageRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.d("HomeFragment", response);
                    JSONObject jsonObject = new JSONObject(response).getJSONObject("data");
                    greetingsTV.setText("Hey, "+jsonObject.getString("username")+"!");
                    winningsTV.setText("Rs. "+jsonObject.getInt("cashWon")+"  ");
                    AppConstants.coins = jsonObject.getInt("cyllidePoints");
                    AppConstants.money = jsonObject.getInt("cyllidePoints");
                    //TODO shift this to profile activity if required
                    pointsTV.setText(jsonObject.getInt("cyllidePoints")+" coins  ");
                    String profileURL = jsonObject.getString("profilePicURL");
                    if(profileURL.equals(AppConstants.noProfilePicURL)){
                        ColorGenerator generator = ColorGenerator.MATERIAL;
                        int color = generator.getColor(jsonObject.getString("username"));
                        TextDrawable drawable = TextDrawable.builder()
                                .beginConfig()
                                .width(100)
                                .height(100)
                                .endConfig()
                                .buildRect(Character.toString(jsonObject.getString("username").charAt(0)).toUpperCase(), color);
                        profilePic.setImageDrawable(drawable);

                    }
                    else {
                        RequestOptions requestOptions = new RequestOptions().override(100);
                        Glide.with(getContext()).load(profileURL).apply(requestOptions).into(profilePic);
                    }
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
                return homepageDataHeaders;
            }
        };
        homepageQueue.add(homepageRequest); 
    }


}
