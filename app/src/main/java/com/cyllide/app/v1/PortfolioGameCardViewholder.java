package com.cyllide.app.v1;

import android.content.Context;
import android.content.Intent;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.portfolio.PortfolioGameDetailedChartActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class PortfolioGameCardViewholder extends RecyclerView.ViewHolder {

    private ArrayList<PortfolioGameCardModel> data;
    private Context context;
    private RequestQueue summaryQueue;
    private Map<String, String> summaryHeaders = new ArrayMap<>();
    private RequestQueue statsQueue;
    private Map<String, String> statsMap = new ArrayMap<>();
    private TextView companyIndustry;
    private TextView companySector;
    private TextView previousClose, open, marketCap,ticker, peRatio;
//    View v;
private LineChart lineChart;
    private ImageView infoButton;
    private int lastIndex;



    public PortfolioGameCardViewholder(@NonNull View itemView) {
        super(itemView);
//        v = itemView;
        companyIndustry = itemView.findViewById(R.id.companyindustry);
        companySector = itemView.findViewById(R.id.companysector);
        peRatio=itemView.findViewById(R.id.dtgc_peratio);
        previousClose=itemView.findViewById(R.id.dtgc_previousclose);
        open=itemView.findViewById(R.id.dtgc_open);
        //ask=v.findViewById(R.id.dtgc_ask);
        marketCap=itemView.findViewById(R.id.dtgc_marketcap);
        ticker = itemView.findViewById(R.id.ticker_title);
        infoButton = itemView.findViewById(R.id.game_card_info_button);
        context = itemView.getContext();
        lineChart = itemView.findViewById(R.id.portfolio_game_home_chart);




    }
    PortfolioGameCardModel model;
    public void populate(final PortfolioGameCardModel model){
        this.model = model;
        Log.d("HERE",model.getTicker());

        companyIndustry.setText(model.getCompanyIndustry());
        companySector.setText(model.getCompanySector());
        peRatio.setText(model.getPeRatio());
        previousClose.setText(model.getPreviousClose());
        open.setText(model.getOpen());
        marketCap.setText(model.getMarketCap());
        ticker.setText(model.getTicker());
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PortfolioGameDetailedChartActivity.class);
                i.putExtra("ticker",model.getTicker());
                context.startActivity(i);
            }
        });


        getChartData(model.getTicker(),"1D",context,lineChart);
//        summaryVolley();
//        statsVolley();


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();



        lineChart.setData(new LineData(lineDataSets));
        lineChart.getXAxis().setDrawLabels(false);

        Description d = new Description();
        d.setText("");
        lineChart.setDescription(d);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineChart.getXAxis().setEnabled(false);


        lineChart.getLegend().setEnabled(false);


        lineChart.invalidate();

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
            }
        });
        Log.d("RETURNINGV","YES");
    }

    void summaryVolley(){
        String url = context.getResources().getString(R.string.dataApiBaseURL)+"stocks/details";
        summaryHeaders.put("ticker", AppConstants.currTicker);
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
                    plotMean(lineChart,yAxisValues,lineDataSets);

                    lineDataSet.setLineWidth(3);
                    lineDataSet.setCircleRadius(5);
                    lineDataSet.setColor(ContextCompat.getColor(context,R.color.white));
                    lineChart.getLegend().setEnabled(false);
                    Description d = new Description();
                    d.setText("");
                    lineChart.setDescription(d);
                    lineChart.invalidate();
                    lastIndex=yAxisValues.size();

                    if(yAxisValues.get(lastIndex-1).getY()>=getMean(yAxisValues))
                    {
                        lineDataSet.setColor(ContextCompat.getColor(context,R.color.progressgreen));
                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(context,R.drawable.chart_gradient));


                    }
                    else {
                        lineDataSet.setColor(ContextCompat.getColor(itemView.getContext(),R.color.red));
                        lineDataSet.setFillDrawable(ContextCompat.getDrawable(itemView.getContext(),R.drawable.chart_red_drawable));

                    }
                    //lineChart.getRenderer().getPaintRender().setShader(new LinearGradient(0, 0, lineChart.getMeasuredWidth(), 0, ContextCompat.getColor(context,R.color.colorPrimary),ContextCompat.getColor(context,R.color.colorPrimary), Shader.TileMode.CLAMP));
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

    void plotMean( LineChart lineChart, ArrayList<Entry> yAxisValues,ArrayList<ILineDataSet> lineDataSets ){
        float mean =getMean(yAxisValues);
        ArrayList<Entry> meanline =new ArrayList<>();
        for (int i=0;i<200;i++)
        {

            meanline.add(new Entry((float)i,mean));
        }
        LineDataSet meanDataSet = new LineDataSet(meanline,"mean");
        meanDataSet.setDrawCircles(false);

        lineDataSets.add(meanDataSet);
        meanDataSet.setColor(ContextCompat.getColor(itemView.getContext(),R.color.colorPrimary));







    }




}
