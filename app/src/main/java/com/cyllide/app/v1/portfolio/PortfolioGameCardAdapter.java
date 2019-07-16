package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import com.cyllide.app.v1.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PortfolioGameCardAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;
    RequestQueue summaryQueue;
    Map<String, String> summaryHeaders = new ArrayMap<>();
    RequestQueue statsQueue;
    Map<String, String> statsMap = new ArrayMap<>();
    TextView companyIndustry;
    TextView companySector;
    TextView previousClose, open, ask, marketCap, peRatio;




    public PortfolioGameCardAdapter(List<String> data, Context context) {
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

        View v = convertView;
        if (v == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            v = inflater.inflate(R.layout.game_card_nifty50, parent, false);
            companyIndustry = v.findViewById(R.id.companyindustry);
            companySector = v.findViewById(R.id.companysector);
            peRatio=v.findViewById(R.id.dtgc_peratio);
            previousClose=v.findViewById(R.id.dtgc_previousclose);
            open=v.findViewById(R.id.dtgc_open);
            ask=v.findViewById(R.id.dtgc_ask);
            marketCap=v.findViewById(R.id.dtgc_marketcap);


        }
        ArrayList<Entry> yAxisValues = new ArrayList<>();
        for(int i=0;i<100;i++){
            yAxisValues.add(new Entry((float)i,(float)(2*i+1)));
        }
        LineChart lineChart = v.findViewById(R.id.portfolio_game_home_chart);
        summaryVolley();
        statsVolley();

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();
        LineDataSet lineDataSet = new LineDataSet(yAxisValues,"Test");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(ContextCompat.getColor(v.getContext(),R.color.colorPrimary));

        lineDataSets.add(lineDataSet);

        lineChart.setData(new LineData(lineDataSets));
        lineChart.getXAxis().setDrawLabels(false);
        lineChart.getAxisLeft().setDrawGridLines(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(v.getContext(),R.drawable.chart_gradient));
        lineChart.getLegend().setEnabled(false);
        Description d = new Description();
        d.setText("");
        lineChart.setDescription(d);
        lineChart.invalidate();

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Layer type: ", Integer.toString(v.getLayerType()));
                Log.i("Hardware Accel type:", Integer.toString(View.LAYER_TYPE_HARDWARE));
            }
        });
        return v;
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
                    ask.setText(jsonObject.getString("Ask"));
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

}