package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.ConnectionStatus;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.PortfolioGameCardModel;
import com.cyllide.app.v1.R;
import com.daprlabs.aaron.swipedeck.SwipeDeck;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import link.fls.swipestack.SwipeStack;


public class PortfolioGameHomeActivity extends AppCompatActivity {

    SwipeStack cardStack;
    List<String> testData;
    ImageView backBtn;
    PortfolioGameCardAdapter adapter;
    ImageView tab1, tab2, tab3;
    Map<String,String> cardsHeader = new HashMap<>();
    RequestQueue cardsRequestQueue;

    void fetchCards(int i){
        Context context;
        cardsRequestQueue = Volley.newRequestQueue(PortfolioGameHomeActivity.this);
        String url = "https://api.cyllide.com/api/client/bulkdata/fetch";
        cardsHeader.put("page",Integer.toString(i));
        StringRequest cardRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE",response);
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONObject detailsObject = responseObject.getJSONObject("details");
                    JSONObject summaryObject = responseObject.getJSONObject("summary");
                    ArrayList<PortfolioGameCardModel> portfolioGameCardModels = new ArrayList<>();
                    for(Iterator<String> iter = detailsObject.keys(); iter.hasNext();) {
                        String key = iter.next();
                        PortfolioGameCardModel model = new PortfolioGameCardModel();
                        model.setTicker(key);
                        model.setCompanySector(detailsObject.getJSONObject(key).getString("Sector"));
                        model.setCompanyIndustry(detailsObject.getJSONObject(key).getString("Industry"));
                        model.setPreviousClose(summaryObject.getJSONObject(key).getString("Previous close"));
                        model.setOpen(summaryObject.getJSONObject(key).getString("Open"));
                        model.setMarketCap(summaryObject.getJSONObject(key).getString("Market cap"));
                        model.setPeRatio(summaryObject.getJSONObject(key).getString("PE ratio (TTM)"));
                        portfolioGameCardModels.add(model);
                    }
                    adapter = new PortfolioGameCardAdapter(portfolioGameCardModels, PortfolioGameHomeActivity.this);
                        cardStack.setAdapter(adapter);
                        cardStack.forceLayout();
                        cardStack.invalidate();
                        cardStack.refreshDrawableState();
                        adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRORHEAR",error.toString());

            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return cardsHeader;
            }
        };

        cardRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        cardsRequestQueue.add(cardRequest);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.portfolio_game_home_activity);
        tab1 = findViewById(R.id.pg_home_activity_1);
        tab2 = findViewById(R.id.pg_home_activity_2);
        tab3 = findViewById(R.id.pg_home_activity_3);
        cardStack = findViewById(R.id.swipe_deck);
        tab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameHomeActivity.this,PortfolioGamePortfolioActivity.class);
                startActivity(returnHome);
                finish();
            }
        });
        tab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnHome = new Intent(PortfolioGameHomeActivity.this,PortfolioGameLeaderboardActivity.class);
                startActivity(returnHome);
                finish();
            }
        });

        testData = new ArrayList<>();
        for(int i=0;i<5; i++){
            testData.add(String.valueOf(i));
        }




        cardStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void onViewSwipedToRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
            }

            @Override
            public void onStackEmpty() {

            }
        });


//        cardStack.setCallback(new SwipeDeck.SwipeDeckCallback() {
//            @Override
//            public void cardSwipedLeft(long positionInAdapter) {
//                Log.i("MainActivity", "card was swiped left, position in adapter: " + positionInAdapter);
//            }
//
//            @Override
//            public void cardSwipedRight(long positionInAdapter) {
//                Log.i("MainActivity", "card was swiped right, position in adapter: " + positionInAdapter);
//
//            }
//        });

//        cardStack.setLeftImage(R.id.left_image);
//        cardStack.setRightImage(R.id.right_image);

        //example of buttons triggering events on the deck
        MaterialCardView dontChooseStockBtn = findViewById(R.id.portfolio_game_cross);
        dontChooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopViewToLeft();
            }
        });
        MaterialCardView chooseStockDoubleQuantity = findViewById(R.id.portfolio_game_diamond);
        chooseStockDoubleQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopViewToRight();
            }
        });

        MaterialCardView chooseStockBtn = findViewById(R.id.portfolio_game_heart);
        chooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                adapter.notifyDataSetChanged();
//                cardStack.swipeTopCardRight(300);
            }
        });
        backBtn = findViewById(R.id.portfolio_game_back_button);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent returnHome = new Intent(PortfolioGameHomeActivity.this,MainActivity.class);
//                startActivity(returnHome);
//                finish();
                onBackPressed();
            }
        });
        cardStack.forceLayout();
        cardStack.invalidate();
        cardStack.refreshDrawableState();
        fetchCards(1);

    }

//    @Override
//    public void onBackPressed(){
//        Intent returnHome = new Intent(this,MainActivity.class);
//        startActivity(returnHome);
//        finish();
//    }

}