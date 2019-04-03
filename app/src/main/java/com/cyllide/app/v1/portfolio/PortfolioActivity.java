package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.CustomWebView;
import com.cyllide.app.v1.R;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONException;
import org.json.JSONObject;



public class PortfolioActivity extends AppCompatActivity {

    ImageView stockAnalysis, orderHistory, portfolioPositions, backButton;
    FrameLayout fl;
    LabelToggle oneDay, oneWeek, oneMonth, oneYear, sixMonths;
    RequestQueue requestQueue;
    TextView previousCloseTV,openTV,dailyRangeTV,yearlyRangeTV;
    private GifImageView progressLoader;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);
        webView = (WebView) findViewById(R.id.web_view_chart_portfolio);
        progressLoader = findViewById(R.id.web_view_loading);
       webView.setWebViewClient(new CustomWebView(progressLoader,webView));
       backButton = findViewById(R.id.portfolio_activity_back_button);
       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(PortfolioActivity.this, MyPortfolio.class);
               startActivity(intent);
           }
       });

       // webView.setWebViewClient(new CustomWebView());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/oneday.html");

        oneDay = findViewById(R.id.nifty_one_day);
        oneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/oneday.html");
            }
        });

        oneWeek = findViewById(R.id.nifty_one_wk);
        oneWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/oneweek.html");
            }
        });

        oneMonth = findViewById(R.id.nifty_one_mon);
        oneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/onemonth.html");
            }
        });

        sixMonths = findViewById(R.id.nifty_six_mon);
        sixMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/sixmonths.html");
            }
        });

        oneYear = findViewById(R.id.nifty_one_yr);
        oneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl("file:///android_asset/oneyear.html");
            }
        });


        fl = findViewById(R.id.portfolio_container);
        previousCloseTV = findViewById(R.id.previous_close_value);
        openTV = findViewById(R.id.open_value);
        dailyRangeTV = findViewById(R.id.days_range_value);
        yearlyRangeTV = findViewById(R.id.week_range_value);


        stockAnalysis = findViewById(R.id.stockchooser);
        stockAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PortfolioActivity.this, PortfolioDetailActivity.class);
                intent.putExtra("id","stockAnalysis");
                startActivity(intent);


            }
        });

        orderHistory = findViewById(R.id.order_history);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PortfolioActivity.this, PortfolioDetailActivity.class);
                intent.putExtra("id","orderHistory");
                startActivity(intent);

            }
        });

        portfolioPositions = findViewById(R.id.portfolio_positions);
        portfolioPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PortfolioActivity.this, PortfolioDetailActivity.class);
                intent.putExtra("id","portfolioPositions");
                startActivity(intent);

            }
        });


        getNiftySummary();



    }


//    public class CustomWebView extends WebViewClient {
//
//
//
//        @Override
//        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
//            webview.setVisibility(webview.INVISIBLE);
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//
//            spinner.setVisibility(View.GONE);
//
//            view.setVisibility(webView.VISIBLE);
//            super.onPageFinished(view, url);
//
//        }
//
//    }

    private void getNiftySummary() {

        requestQueue = Volley.newRequestQueue(this);
        String url = "http://data.cyllide.com/data/nifty50/summary";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("summary",response);

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    previousCloseTV.setText(jsonResponse.getString("Previous close"));
                    openTV.setText(jsonResponse.getString("Open"));
                    dailyRangeTV.setText(jsonResponse.getString("Day's range"));
                    yearlyRangeTV.setText(jsonResponse.getString("52-week range"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("summary",error.toString());

            }
        });

        requestQueue.add(stringRequest);
    }
}