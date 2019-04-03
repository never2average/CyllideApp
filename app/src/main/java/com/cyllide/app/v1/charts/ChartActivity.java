package com.cyllide.app.v1.charts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import pl.droidsonroids.gif.GifImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.CustomWebView;
import com.cyllide.app.v1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


public class ChartActivity extends AppCompatActivity {

    Button oneDay,fiveDay,oneMonth,sixMonth,oneYear;
    WebView webView;
    String ticker;
    RequestQueue summaryRequestQueue;
    RequestQueue incomeSheetRequestQueue;
    RequestQueue balanceSheetRequestQueue;
    private Map<String, String> summaryRequestHeaders = new ArrayMap<String, String>();
    private Map<String, String> incomeSheetRequestHeaders = new ArrayMap<String, String>();
    private Map<String, String> balanceSheetRequestHeaders = new ArrayMap<String, String>();
    TextView summaryPreviousClose, summaryOpen, summaryBid, summaryAsk, summaryVolume,
    summaryMarketCap, summaryBeta, summaryDayRange, incomeGrossProfit, incomeOperatingIncome,
    incomeOperationalIncome, balanceAssets, balanceLiabilities,
    balanceNetTangibleAssets;
    GifImageView webViewLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ticker = intent.getStringExtra("ticker").toUpperCase();
        setContentView(R.layout.activity_chart);
        oneDay = findViewById(R.id.chart_button_one_day);
        fiveDay = findViewById(R.id.chart_button_five_days);
        oneMonth = findViewById(R.id.chart_button_one_month);
        sixMonth = findViewById(R.id.chart_button_six_months);
        oneYear = findViewById(R.id.chart_button_one_year);
        webViewLoading = findViewById(R.id.chart_activity_loading);
        webView = (WebView) findViewById(R.id.web_view_chart);
        webView.setWebViewClient(new CustomWebView(webViewLoading,webView));
        webView.getSettings().setJavaScriptEnabled(true);
//        webView.loadUrl("http://data.cyllide.com/data/chart/\""+ticker+"\"/\""+"1D\"");
        webView.loadUrl("http://data.cyllide.com/data/chart/\"RELIANCE\"/\"1D\"");
        webView.getSettings().setDomStorageEnabled(true);
        balanceAssets = findViewById(R.id.balance_total_assets);


        summaryAsk = findViewById(R.id.summary_ask);
        summaryBeta = findViewById(R.id.summary_beta);
        summaryPreviousClose = findViewById(R.id.summary_previous_close);
        summaryOpen = findViewById(R.id.summary_open);
        summaryBid = findViewById(R.id.summary_bid);
        summaryVolume = findViewById(R.id.summary_volume);
        summaryMarketCap = findViewById(R.id.summary_market_cap);
        summaryDayRange = findViewById(R.id.summary_days_range);

        incomeGrossProfit = findViewById(R.id.income_gross_profit);
        incomeOperatingIncome = findViewById(R.id.income_net_operating_income);
        incomeOperationalIncome = findViewById(R.id.income_operational_income);

        balanceAssets = findViewById(R.id.balance_total_assets);
        balanceLiabilities = findViewById(R.id.balance_total_liabilities);
        balanceNetTangibleAssets = findViewById(R.id.balance_net_tangible_assets);

        String summaryRequestEndPoint = "http://data.cyllide.com/data/stock/summary";
        String incomeSheetRequestEndPoint = "http://data.cyllide.com/data/stock/income";
        String balanceSheetRequestEndPoint = "http://data.cyllide.com/data/stock/balance";


        summaryRequestHeaders.put("ticker",ticker.toUpperCase());
        balanceSheetRequestHeaders.put("ticker",ticker.toUpperCase());
        incomeSheetRequestHeaders.put("ticker", ticker.toUpperCase());
        summaryRequestQueue = Volley.newRequestQueue(this);
        balanceSheetRequestQueue = Volley.newRequestQueue(this);
        incomeSheetRequestQueue = Volley.newRequestQueue(this);


        webView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        StringRequest summaryRequest = new StringRequest(Request.Method.GET, summaryRequestEndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Responses",response);

                try {
                    JSONObject responseObject = new JSONObject(response);

                    summaryAsk.setText("₹ "+responseObject.getString("Ask"));
                    summaryPreviousClose.setText("₹ "+responseObject.getString("Previous close"));
                    summaryOpen.setText("₹ "+responseObject.getString("Open"));
                    summaryBid.setText("₹ "+responseObject.getString("Bid"));
                    summaryVolume.setText(responseObject.getString("Volume"));
                    summaryMarketCap.setText("₹ "+responseObject.getString("Market cap"));
                    summaryBeta.setText("₹ "+responseObject.getString("Beta (3Y monthly)"));
                    summaryDayRange.setText("₹ "+responseObject.getString("Day's range"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Summary Error",error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return summaryRequestHeaders;
            }

        };

        StringRequest incomeSheetRequest = new StringRequest(Request.Method.GET, incomeSheetRequestEndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Responses",response);

                try {
                    JSONObject responseObject = new JSONObject(response);

                    incomeGrossProfit.setText("₹ "+responseObject.getString("Gross profit"));
                    incomeOperatingIncome.setText("₹ "+responseObject.getString("Operating income or loss"));
                    incomeOperationalIncome.setText("₹ "+responseObject.getString("Net income from continuing ops"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("incomeSheet Error",error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return incomeSheetRequestHeaders;
            }

        };


        StringRequest balanceSheetRequest = new StringRequest(Request.Method.GET, balanceSheetRequestEndPoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("Responses",response);

                try {
                    JSONObject responseObject = new JSONObject(response);

                    balanceAssets.setText("₹ "+responseObject.getString("Total assets"));
                    balanceLiabilities.setText("₹ "+responseObject.getString("Total liabilities"));
                    incomeOperationalIncome.setText("₹ "+responseObject.getString("Net tangible assets"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("balanceSheet Error",error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return balanceSheetRequestHeaders;
            }

        };
        summaryRequestQueue.add(summaryRequest);
        incomeSheetRequestQueue.add(incomeSheetRequest);
        balanceSheetRequestQueue.add(balanceSheetRequest);




}
    public void setJavaScriptInterface(String frequency){
        webView.addJavascriptInterface(new JavaScriptChartInterface(this,ticker,frequency), "Android");
        webView.loadUrl("http://data.cyllide.com/data/chart/\""+ticker+"\"/\""+frequency+"\"");

    }
    public void onFrequencyClick(View view){
        oneDay.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        fiveDay.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        oneMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        oneYear.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        sixMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.white));
        oneDay.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        fiveDay.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        oneMonth.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        sixMonth.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        oneYear.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        switch (view.getId()) {
            case R.id.chart_button_one_day:
                oneDay.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneDay.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("1D");
                break;
            case R.id.chart_button_five_days:
                fiveDay.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                fiveDay.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("5D");
                break;
            case R.id.chart_button_one_month:
                oneMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneMonth.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("1M");
                break;
            case R.id.chart_button_six_months:
                sixMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                sixMonth.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("6M");
                break;
            case R.id.chart_button_one_year:
                oneYear.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneYear.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("1Y");
                break;
        }

    }
}
