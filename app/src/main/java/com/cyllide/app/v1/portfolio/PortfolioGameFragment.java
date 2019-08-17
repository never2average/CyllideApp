package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.PortfolioGameCardModel;
import com.cyllide.app.v1.R;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import link.fls.swipestack.SwipeStack;
import pl.droidsonroids.gif.GifImageView;

public class PortfolioGameFragment extends Fragment {


    Map<String, String> cardsHeader = new HashMap<>();
    RequestQueue cardsRequestQueue;
    GifImageView loading;
    SwipeStack cardStack;
    boolean isSuper = false;
    ArrayList<PortfolioGameCardModel> portfolioGameCardModels = new ArrayList<>();
    PortfolioGameCardAdapter adapter;
    Context context;

    public PortfolioGameFragment(Context context) {
        this.context = context;
    }

    int i = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_game, container, false);
        cardStack = view.findViewById(R.id.swipe_deck);
        loading = view.findViewById(R.id.loading_screen);
        cardStack.setListener(new SwipeStack.SwipeStackListener() {
            @Override
            public void onViewSwipedToLeft(int position) {
                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
            }

            @Override
            public void onViewSwipedToRight(int position) {
                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
                if(isSuper) {
                    sendSwipeCard(portfolioGameCardModels.get(position),200);
                    isSuper = false;
                }
                else{
                    sendSwipeCard(portfolioGameCardModels.get(position),100);

                }

            }

            @Override
            public void onStackEmpty() {
                loading.setVisibility(View.VISIBLE);
                fetchCards(i);
                i++;
            }
        });



        MaterialCardView dontChooseStockBtn = view.findViewById(R.id.portfolio_game_cross);
        dontChooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopViewToLeft();
            }
        });
        MaterialCardView chooseStockDoubleQuantity = view.findViewById(R.id.portfolio_game_diamond);
        chooseStockDoubleQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopViewToRight();
                isSuper = true;

            }
        });

        MaterialCardView chooseStockBtn = view.findViewById(R.id.portfolio_game_heart);
        chooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardStack.swipeTopViewToRight();


            }
        });
//        backBtn = findViewById(R.id.portfolio_game_back_button);
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent returnHome = new Intent(PortfolioGameHomeActivity.this,MainActivity.class);
////                startActivity(returnHome);
////                finish();
//                onBackPressed();
//            }
//        });
        cardStack.forceLayout();
        cardStack.invalidate();
        cardStack.refreshDrawableState();
        fetchCards(i);
        i++;

        return view;

    }

    void fetchCards(int i) {
        cardsRequestQueue = Volley.newRequestQueue(context);
        String url = "https://api.cyllide.com/api/client/bulkdata/fetch";
        cardsHeader.put("page", Integer.toString(i));
        StringRequest cardRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", response);
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONObject detailsObject = responseObject.getJSONObject("details");
                    JSONObject summaryObject = responseObject.getJSONObject("summary");
                    portfolioGameCardModels = new ArrayList<>();
                    for (Iterator<String> iter = detailsObject.keys(); iter.hasNext(); ) {
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
                    loading.setVisibility(View.GONE);

                    adapter = new PortfolioGameCardAdapter(portfolioGameCardModels, context);
                    cardStack.setAdapter(adapter);

                    cardStack.forceLayout();
                    cardStack.invalidate();
                    cardStack.refreshDrawableState();
                    adapter.notifyDataSetChanged();
                    cardStack.resetStack();
                } catch (JSONException e) {
                    Log.d("ERROR", e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRORHEAR", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return cardsHeader;
            }
        };

        cardRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        cardsRequestQueue.add(cardRequest);

    }

    RequestQueue requestQueue;
    Map<String, String> getCardsHeader = new HashMap<>();

    private void sendSwipeCard(PortfolioGameCardModel portfolioGameCardModel, int i) {
        requestQueue = Volley.newRequestQueue(context);
        getCardsHeader.put("token", AppConstants.token);
        getCardsHeader.put("ticker", portfolioGameCardModel.getTicker());
        getCardsHeader.put("quantity", Integer.toString(i));
        String url = getResources().getString(R.string.apiBaseURL) + "portfolio/order";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRORHEAR", error.toString());

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return getCardsHeader;
            }
        };

        requestQueue.add(stringRequest);


    }


}
