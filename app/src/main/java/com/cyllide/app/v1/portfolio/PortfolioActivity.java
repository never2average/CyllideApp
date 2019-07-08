package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.ArrayMap;
import androidx.core.content.ContextCompat;
import pl.droidsonroids.gif.GifImageView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.ConnectionStatus;
import com.cyllide.app.v1.CustomWebView;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;


public class PortfolioActivity extends AppCompatActivity {

    ImageView stockAnalysis, orderHistory, portfolioPositions, backButton;
    FrameLayout fl;
    LabelToggle oneDay, oneWeek, oneMonth, oneYear, sixMonths;
    RequestQueue requestQueue;
    TextView previousCloseTV,openTV,dailyRangeTV,yearlyRangeTV;
    private GifImageView progressLoader;
    private WebView webView;

    Map<String,String> lineChartRequestHeader = new ArrayMap<>();
    RequestQueue lineChartRequestQueue;
    LineChart lineChart;
    ArrayList<Entry> yAxisValues = new ArrayList<>();
    ArrayList<String> xAxisValues = new ArrayList<>();


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
               Intent intent = new Intent(PortfolioActivity.this, PortfolioGameHomeActivity.class);
               startActivity(intent);
               finish();
           }
       });




        lineChart = findViewById(R.id.linechart);


        setGraph("1D");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("file:///android_asset/oneday.html");

        oneDay = findViewById(R.id.nifty_one_day);
        oneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionStatus.connectionstatus){
                    setGraph("1D");
                webView.loadUrl("file:///android_asset/oneday.html");}
                else{
                    Toast.makeText(PortfolioActivity.this,"Internet Connecion Lost",Toast.LENGTH_LONG).show();
                }

            }
        });

        oneWeek = findViewById(R.id.nifty_one_wk);
        oneWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGraph("1M");
                if(ConnectionStatus.connectionstatus){
                webView.loadUrl("file:///android_asset/oneweek.html");}
                else {
                    Toast.makeText(PortfolioActivity.this,"Internet Connecion Lost",Toast.LENGTH_LONG).show();


                }
            }
        });

        oneMonth = findViewById(R.id.nifty_one_mon);
        oneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionStatus.connectionstatus){
                    setGraph("1M");
                webView.loadUrl("file:///android_asset/onemonth.html");}
                else {
                    Toast.makeText(PortfolioActivity.this,"Internet Connecion Lost",Toast.LENGTH_LONG).show();

                }
            }
        });

        sixMonths = findViewById(R.id.nifty_six_mon);
        sixMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionStatus.connectionstatus){
                    setGraph("6M");
                webView.loadUrl("file:///android_asset/sixmonths.html");}
                else{
                    Toast.makeText(PortfolioActivity.this,"Internet Connecion Lost",Toast.LENGTH_LONG).show();

                }
            }
        });

        oneYear = findViewById(R.id.nifty_one_yr);
        oneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionStatus.connectionstatus){
                    setGraph("1Y");
                webView.loadUrl("file:///android_asset/oneyear.html");}
                else{
                    Toast.makeText(PortfolioActivity.this,"Internet Connecion Lost",Toast.LENGTH_LONG).show();

                }
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
                Intent intent = new Intent(PortfolioActivity.this, MainActivity.class);
                intent.putExtra("id","stockAnalysis");
                startActivity(intent);
                finish();
            }
        });

        orderHistory = findViewById(R.id.order_history);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioActivity.this, MainActivity.class);
                intent.putExtra("id","orderHistory");
                startActivity(intent);
                finish();
            }
        });

        portfolioPositions = findViewById(R.id.portfolio_positions);
        portfolioPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioActivity.this, MainActivity.class);
                intent.putExtra("id","portfolioPositions");
                startActivity(intent);
                finish();
            }
        });
        getNiftySummary();
    }


    private void getNiftySummary() {

        requestQueue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.dataApiBaseURL)+"nifty50summary";
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


    private void setGraph(String header){


        lineChartRequestQueue = Volley.newRequestQueue(this);
        String url = "https://0e9xuk8re9.execute-api.ap-south-1.amazonaws.com/prod/data/nifty50";
        lineChartRequestHeader.put("value",header);


        StringRequest lineChartRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Responses",response);

                try {
                    Log.d("ChartActivityIndex",response);
                    JSONArray responseObject = new JSONObject(response).getJSONArray("data");
                    int length = responseObject.length();
                    yAxisValues = new ArrayList<>();
                    xAxisValues = new ArrayList<>();
                    int c=0;
                    for(int i=1; i<length;i++){
                        JSONArray valuePair = responseObject.getJSONArray(i);
                        try {
                            float y = Float.parseFloat(Integer.toString(valuePair.getInt(0)));
                            float x =  Float.parseFloat(Double.toString(valuePair.getDouble(1)));
                            yAxisValues.add(new Entry(y,x));
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
                    lineDataSet.setDrawFilled(true);
                    lineDataSet.setFillDrawable(ContextCompat.getDrawable(getBaseContext(),R.drawable.chart_gradient));
                    lineDataSet.setColor(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));

                    lineDataSets.add(lineDataSet);
                    Log.d("PortfolioActivity","FInished making array lists");

                    lineChart.setData(new LineData(lineDataSets));
                    lineChart.getXAxis().setDrawLabels(false);
                    lineChart.getAxisLeft().setDrawGridLines(false);
                    lineChart.getLegend().setEnabled(false);
                    Description d = new Description();
                    d.setText("");
                    lineChart.setDescription(d);
                    lineChart.invalidate();


                    Log.d("PortfolioActivity","FInished setting data");



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
                return lineChartRequestHeader;
            }

        };


        lineChartRequestQueue.add(lineChartRequest);



    }
}
