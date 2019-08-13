package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.net.Uri;
import android.service.autofill.Dataset;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.PortfolioGameCardModel;
import com.cyllide.app.v1.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PortfolioGameCardAdapter extends BaseAdapter {

    private ArrayList<PortfolioGameCardModel> data;
    private Context context;
    RequestQueue summaryQueue;
    Map<String, String> summaryHeaders = new ArrayMap<>();
    RequestQueue statsQueue;
    Map<String, String> statsMap = new ArrayMap<>();
    TextView companyIndustry;
    TextView companySector;
    TextView previousClose, open, marketCap,ticker, peRatio;
    View v;
    int lastIndex=60;




    public PortfolioGameCardAdapter(ArrayList<PortfolioGameCardModel> data, Context context) {
        Log.d("ADAPTER BANA","YES");
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.game_card_nifty50, parent, false);
            companyIndustry = v.findViewById(R.id.companyindustry);
            companySector = v.findViewById(R.id.companysector);
            peRatio=v.findViewById(R.id.dtgc_peratio);
            previousClose=v.findViewById(R.id.dtgc_previousclose);
            open=v.findViewById(R.id.dtgc_open);
            //ask=v.findViewById(R.id.dtgc_ask);
            marketCap=v.findViewById(R.id.dtgc_marketcap);
            ticker = v.findViewById(R.id.ticker_title);


        }
        ArrayList<Entry> yAxisValues = new ArrayList<>();
        for(int i=0;i<100;i++){
            if(i<60){


            yAxisValues.add(new Entry((float)i,(float)(2*i+1)));}


        }



        LineChart lineChart = v.findViewById(R.id.portfolio_game_home_chart);
//        summaryVolley();
//        statsVolley();
        companyIndustry.setText(data.get(position).getCompanyIndustry());
        companySector.setText(data.get(position).getCompanySector());
        peRatio.setText(data.get(position).getPeRatio());
        previousClose.setText(data.get(position).getPreviousClose());
        open.setText(data.get(position).getOpen());
        marketCap.setText(data.get(position).getMarketCap());
        ticker.setText(data.get(position).getTicker());

//        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
//        LineDataSet lineDataSet = new LineDataSet(yAxisValues,"Test");
//        lineDataSet.setDrawCircles(false);
//        lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.colorPrimary));
//        lineChart.getRenderer().getPaintRender().setShader(new LinearGradient(0, 0, lineChart.getMeasuredWidth(), 0, ContextCompat.getColor(context,R.color.colorPrimary),ContextCompat.getColor(context,R.color.colorPrimary), Shader.TileMode.CLAMP));
//        lineDataSets.add(lineDataSet);
//
//
//
//        lineChart.setData(new LineData(lineDataSets));
//        lineChart.getXAxis().setDrawLabels(false);
//
//        Description d = new Description();
//        d.setText("");
//        lineChart.setDescription(d);
//        lineChart.getAxisLeft().setDrawGridLines(false);
//        lineChart.getXAxis().setEnabled(false);
//
//        lineDataSet.setDrawFilled(true);
//        lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_gradient));
//
//        lineChart.getLegend().setEnabled(false);
////        Description dddd = new Description();
////        d.setText("");
////        lineChart.setDescription(dddd);
//
//        lineChart.invalidate();
        getChartData(data.get(position).getTicker(),"1D",v.getContext(),lineChart);
        summaryVolley();
        statsVolley();


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet lineDataSet = new LineDataSet(yAxisValues,"Test");
        lineDataSet.setDrawCircles(false);

        if(yAxisValues.get(lastIndex-1).getY()>=getMean(yAxisValues))
        {
            lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.progressgreen));
            lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_gradient));


        }
        else {
            lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.red));
            lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_red_drawable));

        }
        //lineChart.getRenderer().getPaintRender().setShader(new LinearGradient(0, 0, lineChart.getMeasuredWidth(), 0, ContextCompat.getColor(context,R.color.colorPrimary),ContextCompat.getColor(context,R.color.colorPrimary), Shader.TileMode.CLAMP));
        lineDataSets.add(lineDataSet);

       // lineDataSets.add(whiteLinedataset);



        lineChart.setData(new LineData(lineDataSets));
       // lineChart.setScaleEnabled(false);
        lineChart.getXAxis().setDrawLabels(false);

        Description d = new Description();
        d.setText("");
        lineChart.setDescription(d);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setEnabled(false);

        lineDataSet.setDrawFilled(true);

        lineChart.getLegend().setEnabled(false);

       plotMean(lineChart,yAxisValues,lineDataSets);

        lineChart.invalidate();

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
            }
        });
        Log.d("RETURNINGV","YES");
        return v;
    }



    void summaryVolley(){
        String url = context.getResources().getString(R.string.dataApiBaseURL)+"stocks/details";
        summaryHeaders.put("ticker", AppConstants.currTicker);
        ticker.setText(AppConstants.currTicker);
        summaryQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("Summary",jsonObject.toString());
                    companyIndustry.setText(jsonObject.getString("Industry"));
                    companySector.setText(jsonObject.getString("Sector"));

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
            public Map<String,String> getHeaders(){return summaryHeaders;}
        };
        summaryQueue.add(stringRequest);
    }
    void statsVolley() {
        statsQueue = Volley.newRequestQueue(context);
        statsMap.put("ticker", AppConstants.currTicker);
        String url = context.getResources().getString(R.string.dataApiBaseURL)+"stocks/summary";
        StringRequest statsRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    previousClose.setText("₹ "+jsonObject.getString("Previous close"));
                    open.setText("₹ "+jsonObject.getString("Open"));
                    //ask.setText(jsonObject.getString("Ask"));
                    marketCap.setText("₹ "+jsonObject.getString("Market cap"));
                    peRatio.setText(jsonObject.getString("PE ratio (TTM)"));

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
            public Map<String,String> getHeaders(){return statsMap;}
        };
        statsQueue.add(statsRequest);
    }


    void getChartData(String ticker, String value, final Context context, final LineChart lineChart){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        final Map stringMap = new ArrayMap();
        String url = context.getResources().getString(R.string.dataApiBaseURL)+"stocks/close";
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
                    lineDataSet.setColor(ContextCompat.getColor(context,R.color.colorPrimary));

                    lineDataSets.add(lineDataSet);
                    Log.d("PortfolioActivity","FInished making array lists");

                    lineChart.setData(new LineData(lineDataSets));
                    lineChart.getXAxis().setDrawLabels(false);
                    lineChart.getXAxis().setDrawGridLines(false);
                    lineChart.getAxisRight().setEnabled(false);
                    lineChart.getAxisLeft().setTextColor(ContextCompat.getColor(context,R.color.white));
                    lineChart.getAxisLeft().setGridColor(ContextCompat.getColor(context,R.color.white));
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
                    lineDataSet.setColor(ContextCompat.getColor(context,R.color.white));
                    lineChart.getLegend().setEnabled(false);
                    Description d = new Description();
                    d.setText("");
                    lineChart.setDescription(d);
                    lineChart.invalidate();

                    if(yAxisValues.get(lastIndex-1).getY()>=getMean(yAxisValues))
                    {
                        lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.progressgreen));
                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_gradient));


                    }
                    else {
                        lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.red));
                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_red_drawable));

                    }
                    //lineChart.getRenderer().getPaintRender().setShader(new LinearGradient(0, 0, lineChart.getMeasuredWidth(), 0, ContextCompat.getColor(context,R.color.colorPrimary),ContextCompat.getColor(context,R.color.colorPrimary), Shader.TileMode.CLAMP));
                    lineDataSets.add(lineDataSet);

                    // lineDataSets.add(whiteLinedataset);



                    lineChart.setData(new LineData(lineDataSets));
                    // lineChart.setScaleEnabled(false);
                    lineChart.getXAxis().setDrawLabels(false);

                    lineChart.setDescription(d);
                    lineChart.getAxisLeft().setDrawGridLines(false);
                    lineChart.getXAxis().setEnabled(false);

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

            sum+=list.get(i).getX();

        }
        mean=sum/list.size();
        return mean;

    }

    void plotMean( LineChart lineChart, ArrayList<Entry> yAxisValues,ArrayList<ILineDataSet> lineDataSets ){

        ArrayList<Entry> meanline =new ArrayList<>();
        for (int i=0;i<100;i++)
        {

            meanline.add(new Entry((float)i,getMean(yAxisValues)));
        }
        LineDataSet meanDataSet = new LineDataSet(meanline,"mean");
        meanDataSet.setDrawCircles(false);

        lineDataSets.add(meanDataSet);
        meanDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.colorPrimary));


        lineChart.setData(new LineData(lineDataSets));
        lineChart.invalidate();




    }



}