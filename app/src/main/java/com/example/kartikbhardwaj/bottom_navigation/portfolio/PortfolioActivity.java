package com.example.kartikbhardwaj.bottom_navigation.portfolio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.nex3z.togglebuttongroup.button.LabelToggle;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import org.json.JSONException;
import org.json.JSONObject;



public class PortfolioActivity extends AppCompatActivity  implements InternetConnectivityListener {

    ImageView stockAnalysis, orderHistory, portfolioPositions;
    FrameLayout fl;
    LabelToggle oneDay, oneWeek, oneMonth, oneYear, sixMonths;
    RequestQueue requestQueue;
    TextView previousCloseTV,openTV,dailyRangeTV,yearlyRangeTV;
    int connectionStatus1=0;
    int ConnectionStatus=1;
    InternetAvailabilityChecker internetAvailabilityChecker;

WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

         webView =  findViewById(R.id.web_view_chart_portfolio);
        webView.getSettings().setJavaScriptEnabled(true);


        InternetAvailabilityChecker.init(this);

        internetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        internetAvailabilityChecker.addInternetConnectivityListener(this);


        webView.loadUrl("file:///android_asset/oneday.html");

        oneDay = findViewById(R.id.nifty_one_day);
        oneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ConnectionStatus==1){

                webView.loadUrl("file:///android_asset/oneday.html");}
                else {
                    Toast.makeText(PortfolioActivity.this,"Internet Not Connected",Toast.LENGTH_LONG).show();

                }
            }
        });

        oneWeek = findViewById(R.id.nifty_one_wk);
        oneWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ConnectionStatus==1){



                webView.loadUrl("file:///android_asset/oneweek.html");}
                else {                    Toast.makeText(PortfolioActivity.this,"Internet Not Connected",Toast.LENGTH_LONG).show();
                }
            }
        });

        oneMonth = findViewById(R.id.nifty_one_mon);
        oneMonth.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                if(ConnectionStatus==1){


                webView.loadUrl("file:///android_asset/onemonth.html");}
                else{

                    Toast.makeText(PortfolioActivity.this,"Internet Connection Lost .Please check your Network ",Toast.LENGTH_LONG).show();


                }
            }
        });

        sixMonths = findViewById(R.id.nifty_six_mon);
        sixMonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ConnectionStatus==1){


                webView.loadUrl("file:///android_asset/sixmonths.html");}
                else{                    Toast.makeText(PortfolioActivity.this,"Internet Connection Lost .Please check your Network ",Toast.LENGTH_LONG).show();
                }
            }
        });

        oneYear = findViewById(R.id.nifty_one_yr);
        oneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ConnectionStatus==1){


                webView.loadUrl("file:///android_asset/oneyear.html");}

                else{
                    Toast.makeText(PortfolioActivity.this,"Internet Connection Lost .Please check your Network ",Toast.LENGTH_LONG).show();


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

                if(ConnectionStatus==1){

                Intent intent = new Intent(PortfolioActivity.this, PortfolioDetailActivity.class);
                intent.putExtra("id","stockAnalysis");
                startActivity(intent);}else
                {
                    Toast.makeText(PortfolioActivity.this,"Internet Not Connected",Toast.LENGTH_LONG).show();
                }


            }
        });

        orderHistory = findViewById(R.id.order_history);
        orderHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConnectionStatus==1){
                Intent intent = new Intent(PortfolioActivity.this, PortfolioDetailActivity.class);
                intent.putExtra("id","orderHistory");
                startActivity(intent);}
                else
                {
                    Toast.makeText(PortfolioActivity.this,"Internet Not Connected",Toast.LENGTH_LONG).show();

                }

            }
        });

        portfolioPositions = findViewById(R.id.portfolio_positions);
        portfolioPositions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ConnectionStatus==1){
                Intent intent = new Intent(PortfolioActivity.this, PortfolioDetailActivity.class);
                intent.putExtra("id","portfolioPositions");
                startActivity(intent);}
                else {
                    Toast.makeText(PortfolioActivity.this,"Internet Not Connected",Toast.LENGTH_LONG).show();

                }

            }
        });


        getNiftySummary();



    }

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

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

        if(isConnected!=true)
        {
            Toast.makeText(PortfolioActivity.this,"internet Connection Lost "+String.valueOf(connectionStatus1),Toast.LENGTH_LONG).show();
            ConnectionStatus=0;


        } if(isConnected==true&&(connectionStatus1>=1)){

            Toast.makeText(PortfolioActivity.this,"Back Online .",Toast.LENGTH_LONG).show();
            ConnectionStatus=1;

        }
        //Toast.makeText(PortfolioActivity.this,String.valueOf(ConnectionStatus),Toast.LENGTH_LONG).show();
        Log.d("library","statechanged");
        connectionStatus1++;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        InternetAvailabilityChecker.init(this);

        internetAvailabilityChecker
                .removeInternetConnectivityChangeListener(this);

    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        internetAvailabilityChecker
                .removeInternetConnectivityChangeListener(this);
    }

}
