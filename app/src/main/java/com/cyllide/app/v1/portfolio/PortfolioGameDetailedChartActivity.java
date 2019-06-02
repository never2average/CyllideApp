package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import pl.droidsonroids.gif.GifImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.cyllide.app.v1.charts.JavaScriptChartInterface;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;


public class PortfolioGameDetailedChartActivity extends AppCompatActivity {

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
    TextView currentPriceTV;
    LineChart lineChart;
    private RequestQueue requestQueue;
    Map<String,String> stringMap = new ArrayMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ticker = intent.getStringExtra("ticker").toUpperCase();
        setContentView(R.layout.activity_chart_detailed_game_portfolio);
        oneDay = findViewById(R.id.chart_button_one_day);
        fiveDay = findViewById(R.id.chart_button_five_days);
        oneMonth = findViewById(R.id.chart_button_one_month);
        sixMonth = findViewById(R.id.chart_button_six_months);
        oneYear = findViewById(R.id.chart_button_one_year);
        webViewLoading = findViewById(R.id.chart_activity_loading);
        webView = (WebView) findViewById(R.id.web_view_chart);
        webView.setWebViewClient(new CustomWebView(webViewLoading,webView));
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getResources().getString(R.string.dataApiBaseURL)+"chart/\""+ticker+"\"/\"1D\"");
        getChartData(ticker,"1D",this);
        webView.getSettings().setDomStorageEnabled(true);
        balanceAssets = findViewById(R.id.balance_total_assets);
        currentPriceTV = findViewById(R.id.current_price_chart_tv);
        getSingleValue(ticker, PortfolioGameDetailedChartActivity.this);
        summaryAsk = findViewById(R.id.summary_ask);
        summaryBeta = findViewById(R.id.summary_beta);
        summaryPreviousClose = findViewById(R.id.summary_previous_close);
        summaryOpen = findViewById(R.id.summary_open);
        summaryBid = findViewById(R.id.summary_bid);
        summaryVolume = findViewById(R.id.summary_volume);
        summaryMarketCap = findViewById(R.id.summary_market_cap);
        summaryDayRange = findViewById(R.id.summary_days_range);
        lineChart = findViewById(R.id.linechart);
        getChartData(ticker,"1D",this);

        incomeGrossProfit = findViewById(R.id.income_gross_profit);
        incomeOperatingIncome = findViewById(R.id.income_net_operating_income);
        incomeOperationalIncome = findViewById(R.id.income_operational_income);

        balanceAssets = findViewById(R.id.balance_total_assets);
        balanceLiabilities = findViewById(R.id.balance_total_liabilities);
        balanceNetTangibleAssets = findViewById(R.id.balance_net_tangible_assets);

        String summaryRequestEndPoint = getResources().getString(R.string.dataApiBaseURL)+"stocks/summary";
        String incomeSheetRequestEndPoint = getResources().getString(R.string.dataApiBaseURL)+"stocks/income";
        String balanceSheetRequestEndPoint = getResources().getString(R.string.dataApiBaseURL)+"stocks/balance";


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
        webView.loadUrl(getResources().getString(R.string.apiBaseURL)+"chart/\""+ticker+"\"/\""+frequency+"\"");
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
                getChartData(ticker,"1D",this);
                break;
            case R.id.chart_button_five_days:
                fiveDay.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                fiveDay.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("5D");
                getChartData(ticker,"5D",this);
                break;
            case R.id.chart_button_one_month:
                oneMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneMonth.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("1M");
                getChartData(ticker,"1M",this);
                break;
            case R.id.chart_button_six_months:
                sixMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                sixMonth.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("6M");
                getChartData(ticker,"6M",this);
                break;
            case R.id.chart_button_one_year:
                oneYear.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneYear.setTextColor(ContextCompat.getColor(this,R.color.white));
                setJavaScriptInterface("1Y");
                getChartData(ticker,"1Y",this);
                break;
        }

    }

    void getSingleValue(String ticker, Context context){
        requestQueue = Volley.newRequestQueue(context);
        String url = getResources().getString(R.string.dataApiBaseURL)+"stocks/close";
        stringMap.put("value","1D");
        stringMap.put("ticker",ticker);
        stringMap.put("singleval","True");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Double data = jsonObject.getDouble("data");
                    Double movement = jsonObject.getDouble("movement");
                    DecimalFormat df = new DecimalFormat("####0.000");
                    Date date = new Date(jsonObject.getLong("timestamp"));
                    DateFormat dateformatter = new SimpleDateFormat("HH");
                    int hour = Integer.parseInt(dateformatter.format(date));
                    if (hour < 16 && hour >= 9) {
                        if (jsonObject.getDouble("movement") >= 0) {
                            currentPriceTV.setTextColor(Color.parseColor("#00ff00"));
                            currentPriceTV.setText(df.format(data) + "(+" + df.format(movement) + "%)" + "▲");
                        } else {
                            currentPriceTV.setTextColor(Color.parseColor("#ff0000"));
                            currentPriceTV.setText(df.format(data) + "(" + df.format(movement) + "%)" + "▼");
                        }
                    } else {
                        currentPriceTV.setTextColor(ContextCompat.getColor(currentPriceTV.getContext(), R.color.dark_gray));
                        if (jsonObject.getDouble("movement") >= 0) {
                            currentPriceTV.setText(df.format(data) + "(+" + df.format(movement) + "%)" + "▲");
                        } else {
                            currentPriceTV.setText(df.format(data) + "(+" + df.format(movement) + "%)" + "▼");
                        }
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("resp", error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return stringMap;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }




    void getChartData(String ticker, String value, Context context){
        requestQueue = Volley.newRequestQueue(context);
        String url = getResources().getString(R.string.dataApiBaseURL)+"stocks/close";
        stringMap.put("value",value);
        stringMap.put("ticker",ticker);
        stringMap.put("singleval","False");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray responseArray = new JSONObject(response).getJSONArray("data");
                    Log.d("PortfolioGameDetailedChartActivity",response);

                    int length = responseArray.length();
                    ArrayList<Entry> yAxisValues = new ArrayList<>();
                    ArrayList<String> xAxisValues = new ArrayList<>();
                    int c=0;
                    for(int i=0; i<length;i++){

                        try {
                            Double yValue = responseArray.getDouble(i);
                            float y = Float.parseFloat(Double.toString(yValue));
                            float x =  Float.parseFloat(Integer.toString(i));
                            yAxisValues.add(new Entry(x,y));
                            xAxisValues.add(c, Float.toString(x));
                            c++;
                        }
                        catch(JSONException e){
                            continue;
                        }
                    }
                    String[] xaxes = new String[xAxisValues.size()];
                    for(int i=0;i<xAxisValues.size();i++){
                        xaxes[i] = xAxisValues.get(i);
                    }

                    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
                    LineDataSet lineDataSet = new LineDataSet(yAxisValues,"Test");
                    lineDataSet.setDrawCircles(false);
                    lineDataSet.setColor(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));

                    lineDataSets.add(lineDataSet);
                    Log.d("PortfolioActivity","FInished making array lists");

                    lineChart.setData(new LineData(lineDataSets));
                    lineChart.getXAxis().setDrawLabels(false);
                    lineChart.getAxisLeft().setDrawGridLines(false);
                    lineDataSet.setDrawFilled(true);
                    lineDataSet.setFillDrawable(ContextCompat.getDrawable(getBaseContext(),R.drawable.chart_gradient));
                    lineChart.getLegend().setEnabled(false);
                    Description d = new Description();
                    d.setText("");
                    lineChart.setDescription(d);
                    lineChart.invalidate();


                    Log.d("PortfolioActivity","FInished setting data");




                }
                catch (JSONException e){
                    Log.d("PortfolioGameDetailedChartActivity",e.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("resp", error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return stringMap;
            }
        };
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }


}
