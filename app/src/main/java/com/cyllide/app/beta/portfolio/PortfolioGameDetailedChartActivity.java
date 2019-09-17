package com.cyllide.app.beta.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;

import pl.droidsonroids.gif.GifImageView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.tabs.TabLayout;

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
    String ticker;
    GifImageView webViewLoading;
    TextView currentPriceTV;
    LineChart lineChart;
    ImageView backBtn;
    TabLayout tabLayout;
    private RequestQueue requestQueue;
    Map<String,String> stringMap = new ArrayMap<>();

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(PortfolioGameDetailedChartActivity.this, PortfolioGameHomeActivity.class);
        startActivity(intent);
        finish();
    }

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
        getChartData(ticker, "1D", this);
        currentPriceTV = findViewById(R.id.current_price_chart_tv);
        getSingleValue(ticker, PortfolioGameDetailedChartActivity.this);
        lineChart = findViewById(R.id.linechart);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_chart_activity);
        AppConstants.currTicker = ticker;
        getChartData(ticker, "1D", this);
        backBtn = findViewById(R.id.portfolio_detailed_chart_back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PortfolioGameDetailedChartActivity.this, PortfolioGameHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("Summary"));
        tabLayout.addTab(tabLayout.newTab().setText("Statistics"));
        tabLayout.addTab(tabLayout.newTab().setText("Income Statement"));
        tabLayout.addTab(tabLayout.newTab().setText("Cash Flow Statement"));
        tabLayout.addTab(tabLayout.newTab().setText("Balance Sheet"));
        final CustomViewPager viewPager =
                findViewById(R.id.view_pager_chart_activity);
        final PagerAdapter adapter = new ChartsPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                viewPager.measure(View.MeasureSpec.getSize(View.MeasureSpec.EXACTLY),View.MeasureSpec.getSize(View.MeasureSpec.EXACTLY));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }

        });



    }

    TextView createCustomTabView(Context context, String name){
        TextView tv = new TextView(context);
        tv.setText(name);
        return tv;
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
        switch (view.getId()) {
            case R.id.chart_button_one_day:
                oneDay.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneDay.setTextColor(ContextCompat.getColor(this,R.color.white));
                getChartData(ticker,"1D",this);
                break;
            case R.id.chart_button_five_days:
                fiveDay.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                fiveDay.setTextColor(ContextCompat.getColor(this,R.color.white));
                getChartData(ticker,"5D",this);
                break;
            case R.id.chart_button_one_month:
                oneMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneMonth.setTextColor(ContextCompat.getColor(this,R.color.white));
                getChartData(ticker,"1M",this);
                break;
            case R.id.chart_button_six_months:
                sixMonth.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                sixMonth.setTextColor(ContextCompat.getColor(this,R.color.white));
                getChartData(ticker,"6M",this);
                break;
            case R.id.chart_button_one_year:
                oneYear.setBackgroundColor(ContextCompat.getColor(this,R.color.colorPrimary));
                oneYear.setTextColor(ContextCompat.getColor(this,R.color.white));
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
                    DecimalFormat df = new DecimalFormat("####0.00");
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


    void getChartData(String ticker, String value, final Context context){
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
                    Log.d("PortfolioGameChart",response);

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
                    lineChart.getXAxis().setDrawGridLines(false);
                    lineChart.getAxisRight().setEnabled(false);
                    lineChart.getAxisLeft().setTextColor(ContextCompat.getColor(getBaseContext(),R.color.white));
                    lineChart.getAxisLeft().setGridColor(ContextCompat.getColor(getBaseContext(),R.color.white));
                    lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {

                            Highlight highlight[] = new Highlight[lineChart.getData().getDataSets().size()];
                            for (int j = 0; j < lineChart.getData().getDataSets().size(); j++) {

                                IDataSet iDataSet = lineChart.getData().getDataSets().get(j);

                                for (int i = 0; i < ((LineDataSet) iDataSet).getValues().size(); i++) {
                                    if (((LineDataSet) iDataSet).getValues().get(i).getX() == e.getX()) {
                                        highlight[j] = new Highlight(e.getX(), e.getY(), j);
                                    }
                                }

                            }
                            lineChart.highlightValues(highlight);
                        }

                        @Override
                        public void onNothingSelected() {
                        }
                    });

                    lineDataSet.setLineWidth(3);
                    lineDataSet.setCircleRadius(5);
                    lineDataSet.setColor(ContextCompat.getColor(getBaseContext(),R.color.colorPrimary));
                    lineChart.getLegend().setEnabled(false);
                    Description d = new Description();
                    d.setText("");
                    lineChart.setDescription(d);
                    lineChart.invalidate();


                    Log.d("PortfolioActivity","FInished setting data");





                    lineChart.setData(new LineData(lineDataSets));
                    lineChart.getXAxis().setDrawLabels(false);
                    lineChart.getXAxis().setDrawGridLines(false);
                    lineChart.getAxisRight().setEnabled(true);
                    lineChart.getAxisLeft().setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    lineChart.getAxisRight().setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    lineChart.getAxisLeft().setGridColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                        @Override
                        public void onValueSelected(Entry e, Highlight h) {

                            Highlight highlight[] = new Highlight[lineChart.getData().getDataSets().size()];
                            for (int j = 0; j < lineChart.getData().getDataSets().size(); j++) {

                                IDataSet iDataSet = lineChart.getData().getDataSets().get(j);

                                for (int i = 0; i < ((LineDataSet) iDataSet).getValues().size(); i++) {
                                    if (((LineDataSet) iDataSet).getValues().get(i).getX() == e.getX()) {
                                        highlight[j] = new Highlight(e.getX(), e.getY(), j);
                                    }
                                }

                            }
                            lineChart.highlightValues(highlight);
                        }

                        @Override
                        public void onNothingSelected() {
                        }
                    });
//                    plotMean(lineChart,yAxisValues,lineDataSets);

                    lineDataSet.setLineWidth(3);
                    lineDataSet.setCircleRadius(5);
                    lineDataSet.setColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    lineChart.getLegend().setEnabled(false);
//                    Description d = new Description();
                    d.setText("");
                    lineChart.setDescription(d);
                    lineChart.invalidate();
//                    lastIndex=yAxisValues.size();

//                    if(yAxisValues.get(lastIndex-1).getY()>=getMean(yAxisValues))
//                    {
//                        lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.progressgreen));
//                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_gradient));
//
//
//                    }
//                    else {
//                        lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.red));
//                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_red_drawable));
//
//                    }
                    //lineChart.getRenderer().getPaintRender().setShader(new LinearGradient(0, 0, lineChart.getMeasuredWidth(), 0, ContextCompat.getColor(context,R.color.colorPrimary),ContextCompat.getColor(context,R.color.colorPrimary), Shader.TileMode.CLAMP));
                    int lastIndex=yAxisValues.size();

                    if(yAxisValues.get(lastIndex-1).getY()>=getMean(yAxisValues))
                    {
                        lineDataSet.setColor(ContextCompat.getColor(context,R.color.progressgreen));
                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(context,R.drawable.chart_gradient));


                    }
                    else {
                        lineDataSet.setColor(ContextCompat.getColor(context,R.color.red));
                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(context,R.drawable.chart_red_drawable));

                    }

                    lineDataSets.add(lineDataSet);

                    // lineDataSets.add(whiteLinedataset);



                    lineChart.setData(new LineData(lineDataSets));
                    // lineChart.setScaleEnabled(false);
                    lineChart.getXAxis().setDrawLabels(false);

                    lineChart.setDescription(d);
                    lineChart.getAxisLeft().setDrawGridLines(true);
                    lineChart.getXAxis().setEnabled(true);

                    lineDataSet.setDrawFilled(true);

                    lineChart.getLegend().setEnabled(false);
                    lineChart.getAxisLeft().setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    lineChart.getAxisRight().setTextColor(ContextCompat.getColor(context,R.color.colorPrimary));
                    lineChart.getAxisLeft().setGridColor(ContextCompat.getColor(context,R.color.colorPrimary));

                    lineDataSet.setDrawFilled(true);

                    lineChart.getLegend().setEnabled(false);





                    Log.d("PortfolioActivity","FInished setting data");








                }
                catch (JSONException e){
                    Log.d("PortfolioGameChart",e.toString());

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

    float getMean(ArrayList<Entry> list){
        int mean;
        int sum=0;
        for(int i= 0;i<list.size();i++) {

            sum+=list.get(i).getY();

        }
        mean=sum/list.size();
        return mean;

    }
}
