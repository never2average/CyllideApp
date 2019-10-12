package com.cyllide.app.beta.portfolio;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.PortfolioGameCardModel;
import com.cyllide.app.beta.PortfolioGameCardRVAdapter;
import com.cyllide.app.beta.R;
import com.cyllide.app.beta.stories.SnapHelperOneByOne;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import link.fls.swipestack.SwipeStack;
import me.toptas.fancyshowcase.FancyShowCaseQueue;
import me.toptas.fancyshowcase.FancyShowCaseView;
import me.toptas.fancyshowcase.listener.OnViewInflateListener;
import pl.droidsonroids.gif.GifImageView;

public class PortfolioGameFragment extends Fragment {


    Map<String, String> cardsHeader = new HashMap<>();
    RequestQueue cardsRequestQueue;
//    GifImageView loading;
    SwipeStack cardStack;
    boolean isSuper = false;
    ArrayList<PortfolioGameCardModel> portfolioGameCardModels = new ArrayList<>();
    PortfolioGameCardAdapter adapter;
    Context context;
    RecyclerView recyclerView;
    boolean isFirstTime = false;
    MaterialCardView dontChooseStockBtn;
    PortfolioGameCardRVAdapter rvAdapter;
    MaterialCardView chooseStockBtn;
    MaterialCardView chooseStockDoubleQuantity;
    TextView noCards;
    LinearLayout rootView;
    boolean isLoading = false;
    LinearLayoutManager linearLayoutManager1;

    public PortfolioGameFragment(Context context) {
        this.context = context;
    }

    int i = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_portfolio_game, container, false);
        cardStack = view.findViewById(R.id.swipe_deck);
        recyclerView = view.findViewById(R.id.game_rv);
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(recyclerView);
//        loading = view.findViewById(R.id.loading_screen);
        noCards = view.findViewById(R.id.no_cards_tv);
        rootView = view.findViewById(R.id.root_layout);
//        cardStack.setListener(new SwipeStack.SwipeStackListener() {
//            @Override
//            public void onViewSwipedToLeft(int position) {
//                Log.i("MainActivity", "card was swiped left, position in adapter: " + position);
//
//            }
//
//            @Override
//            public void onViewSwipedToRight(int position) {
//                Log.i("MainActivity", "card was swiped right, position in adapter: " + position);
//                if(isSuper) {
//                    Log.d("NUMMMMM", ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition()+"");
//                    sendSwipeCard(portfolioGameCardModels.get(position),200);
//                    isSuper = false;
//                }
//                else{
//                    sendSwipeCard(portfolioGameCardModels.get(position),100);
//
//                }
//
//            }
//
//            @Override
//            public void onStackEmpty() {
//                loading.setVisibility(View.VISIBLE);
//                i++;
//                fetchCards(i);
//            }
//        });
//



        dontChooseStockBtn = view.findViewById(R.id.portfolio_game_cross);
        dontChooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.d("NUMMMMM", position+"");
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        if(position != 0) {
                            dontChooseStockBtn.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate));
                            recyclerView.smoothScrollToPosition(position - 1);
                        }
                    }
                });

//                cardStack.swipeTopViewToLeft();
//                adapter = new PortfolioGameCardAdapter(portfolioGameCardModels, context);
//                cardStack.setAdapter(adapter);
            }
        });
        chooseStockDoubleQuantity = view.findViewById(R.id.portfolio_game_diamond);
        chooseStockDoubleQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.d("NUMMMMM", position+"");
                Log.d("NUMMMMM", ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition()+"");
                if(portfolioGameCardModels.get(position).getTicker() == null){
                    Snackbar snackbar = Snackbar

                            .make(rootView,"Loading more cards", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    return;

                }
                sendSwipeCard(portfolioGameCardModels.get(position),100);

                cardStack.swipeTopViewToRight();

                isSuper = true;

            }
        });

        chooseStockBtn = view.findViewById(R.id.portfolio_game_heart);
        chooseStockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int position = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                Log.d("NUMMMMM", position+"");
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                            recyclerView.smoothScrollToPosition(position + 1);

                    }
                });


            }
        });

        cardStack.forceLayout();
        cardStack.invalidate();
        cardStack.refreshDrawableState();


        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String name = preferences.getString(formattedDate, "");
        if(!name.equalsIgnoreCase(""))
        {
           i = Integer.parseInt(name);
           i = 1;
           isFirstTime = true;
        }
        else{
            i = 1;
        }



        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == portfolioGameCardModels.size() - 1) {
                        //bottom of list!
                        if(i<=5 && !isLoading){
                            i++;
                            isLoading = true;
//                            loading.setVisibility(View.VISIBLE);
                            portfolioGameCardModels.add(new PortfolioGameCardModel());
                            rvAdapter.notifyDataSetChanged();
                            fetchCards(i);
                        }

                    }
                }
            }
        });

        rvAdapter = new PortfolioGameCardRVAdapter(portfolioGameCardModels);
//        linearLayoutManager1 =new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false){
//            @Override
//            public boolean canScrollHorizontally() {
//                return false;
//            }
//        };

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(rvAdapter);




        fetchCards(i);

        return view;


    }

    void fetchCards(final int i) {
        cardsRequestQueue = Volley.newRequestQueue(context);
        Log.d("IIIII","Entering here as page"+i+"");
        String url = "https://api.cyllide.com/api/client/bulkdata/fetch";
        cardsHeader.put("page", Integer.toString(i));
        StringRequest cardRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", response);
                try {
                    if(portfolioGameCardModels.size()>= i*10+1){
                        Log.d("IIIII","Exiting here as page"+i+" already exists");
                        return;
                    }
                    try {
                        portfolioGameCardModels.remove(portfolioGameCardModels.size() - 1);
                    }
                    catch (Exception e){}
                    JSONObject responseObject = new JSONObject(response);
                    JSONObject detailsObject = responseObject.getJSONObject("details");
                    JSONObject summaryObject = responseObject.getJSONObject("summary");
                    for (Iterator<String> iter = detailsObject.keys(); iter.hasNext(); ) {
                        String key = iter.next();
//                        if(isFirstTime){
//                            Log.d("HEREEEE",preferences.getString("ticker", ""));
//                            Log.d("HEREEE",key);
//                            if(key.equals( preferences.getString("ticker", ""))){
//                                isFirstTime = false;
//                            }
//                            else{
//                                continue;
//                            }
//                        }


                        Log.d("KEY",key);

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
                    Log.d("IIIII","Setting adapter"+i+"");
//                    loading.setVisibility(View.GONE);
                    rvAdapter.notifyDataSetChanged();


                    noCards.setVisibility(View.GONE);

                    if(portfolioGameCardModels.size() == 0 && i <5){
                        Log.d("IIIII","Fetching cards again"+i+"");

                        int e = i+1;
                        fetchCards(e);

                    }
                    if(responseObject.equals(null)){
                            noCards.setVisibility(View.VISIBLE);
                    }


                    adapter = new PortfolioGameCardAdapter(portfolioGameCardModels, context);
                    cardStack.setAdapter(adapter);

                    cardStack.forceLayout();
                    cardStack.invalidate();
                    cardStack.refreshDrawableState();
                    adapter.notifyDataSetChanged();
                    isLoading = false;
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

    private void sendSwipeCard(PortfolioGameCardModel portfolioGameCardModel, int qty) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("ticker",portfolioGameCardModel.getTicker());
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);
        editor.putString(formattedDate,Integer.toString(i));
        editor.apply();
        requestQueue = Volley.newRequestQueue(context);
        getCardsHeader.put("token", AppConstants.token);
        getCardsHeader.put("ticker", portfolioGameCardModel.getTicker());
        getCardsHeader.put("quantity", Integer.toString(qty));
        String url = getResources().getString(R.string.apiBaseURL) + "portfolio/order";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("RESPONSE", response);
                if(response.equals("{\"data\": \"Position Not Taken\"}")){
                    Snackbar snackbar = Snackbar

                            .make(rootView,"Market is Closed", Snackbar.LENGTH_LONG);
                    snackbar.show();
                    cardStack.resetStack();
                }
                else{
                    adapter = new PortfolioGameCardAdapter(portfolioGameCardModels, context);
                    cardStack.setAdapter(adapter);

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
                return getCardsHeader;
            }
        };

        requestQueue.add(stringRequest);



    }

    public void showAppTour(){


        FancyShowCaseView welcome = new FancyShowCaseView.Builder(getActivity())
                .backgroundColor(R.color.colorPrimary)
                .customView(R.layout.welcome_contest, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated(@NonNull View view) {
                    }
                })
                .build();


        FancyShowCaseView gameTab = new FancyShowCaseView.Builder(getActivity())
                .backgroundColor(R.color.colorPrimary)
                .focusOn(((PortfolioGameHomeActivity) getActivity()).game)
                .customView(R.layout.contest_tour_game, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated( @NonNull View view) {
                    }
                })
                .build();



        FancyShowCaseView positionsTab = new FancyShowCaseView.Builder(getActivity())
                .backgroundColor(R.color.colorPrimary)
                .focusOn(((PortfolioGameHomeActivity) getActivity()).positions)
                .customView(R.layout.contest_tour_positions, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated( @NonNull View view) {
                    }
                })
                .build();

        FancyShowCaseView leaderboardTab = new FancyShowCaseView.Builder(getActivity())
                .backgroundColor(R.color.colorPrimary)
                .focusOn(((PortfolioGameHomeActivity) getActivity()).leaderboard)
                .customView(R.layout.contest_tour_leaderboard, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated(@NonNull View view) {
                    }
                })
                .build();

        FancyShowCaseView noStocksButton = new FancyShowCaseView.Builder(getActivity())
                .backgroundColor(R.color.colorPrimary)
                .focusOn(dontChooseStockBtn)
                .customView(R.layout.contest_tour_cancel, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated( View view) {
                    }
                })
                .build();

        FancyShowCaseView chooseStocksButton = new FancyShowCaseView.Builder(getActivity())
                .backgroundColor(R.color.colorPrimary)
                .focusOn(chooseStockBtn)
                .customView(R.layout.contest_tour_love, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated( View view) {
                    }
                })
                .build();

        FancyShowCaseView chooseDoubleStocksButton = new FancyShowCaseView.Builder(getActivity())
                .backgroundColor(R.color.colorPrimary)
                .focusOn(chooseStockDoubleQuantity)
                .customView(R.layout.contest_tour_star, new OnViewInflateListener() {
                    @Override
                    public void onViewInflated( View view) {
                    }
                })
                .build();


        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int dpi = (int)(metrics.density);
        int x = 50*dpi;
        int y = 160*dpi;
        int w = 1100*dpi;
        int h = 120*dpi;
//        FancyShowCaseView features = new FancyShowCaseView.Builder(this)
//                .backgroundColor(R.color.colorPrimary)
//                .focusRectAtPosition(x, y, w, h)
//                .fitSystemWindows(true)
//                .customView(R.layout.app_tour_features, new OnViewInflateListener() {
//                    @Override
//                    public void onViewInflated(@NotNull View view) {
//                    }
//                })
//                .focusShape(FocusShape.ROUNDED_RECTANGLE)
//                .roundRectRadius(15)
//                .build();

//        FancyShowCaseView end = new FancyShowCaseView.Builder(this)
//                .backgroundColor(R.color.deeppurple700)
//                .customView(R.layout.app_tour_end, new OnViewInflateListener() {
//                    @Override
//                    public void onViewInflated(@NotNull View view) {
//                    }
//                })
//                .build();

        FancyShowCaseQueue queue = new FancyShowCaseQueue()
                .add(welcome)
                .add(gameTab)
                .add(positionsTab)
                .add(leaderboardTab)
                .add(noStocksButton)
                .add(chooseStocksButton)
                .add(chooseDoubleStocksButton);

        queue.show();

    }







}
