package com.cyllide.app.v1;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

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

import pl.droidsonroids.gif.GifImageView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OverviewChartsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OverviewChartsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OverviewChartsFragment extends Fragment {

    Button oneDay,fiveDay,oneMonth,sixMonth,oneYear;
    String ticker;
    GifImageView webViewLoading;
    TextView currentPriceTV;
    LineChart lineChart;
    TabLayout tabLayout;
    private RequestQueue requestQueue;
    Map<String,String> stringMap = new ArrayMap<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public OverviewChartsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OverviewChartsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OverviewChartsFragment newInstance(String param1, String param2) {
        OverviewChartsFragment fragment = new OverviewChartsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    public void onFrequencyClick(View view){
        oneDay.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
        fiveDay.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
        oneMonth.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
        oneYear.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
        sixMonth.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.white));
        oneDay.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
        fiveDay.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
        oneMonth.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
        sixMonth.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
        oneYear.setTextColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
        switch (view.getId()) {
            case R.id.chart_button_one_day:
                oneDay.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
                oneDay.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                getChartData(ticker,"1D",view.getContext());
                break;
            case R.id.chart_button_five_days:
                fiveDay.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
                fiveDay.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                getChartData(ticker,"5D",view.getContext());
                break;
            case R.id.chart_button_one_month:
                oneMonth.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
                oneMonth.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                getChartData(ticker,"1M",view.getContext());
                break;
            case R.id.chart_button_six_months:
                sixMonth.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
                sixMonth.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                getChartData(ticker,"6M",view.getContext());
                break;
            case R.id.chart_button_one_year:
                oneYear.setBackgroundColor(ContextCompat.getColor(view.getContext(),R.color.colorPrimary));
                oneYear.setTextColor(ContextCompat.getColor(view.getContext(),R.color.white));
                getChartData(ticker,"1Y",view.getContext());
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
                    lineDataSet.setColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));

                    lineDataSets.add(lineDataSet);
                    Log.d("PortfolioActivity","FInished making array lists");

                    lineChart.setData(new LineData(lineDataSets));
                    lineChart.getXAxis().setDrawLabels(false);
                    lineChart.getAxisLeft().setDrawGridLines(false);
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

                    lineDataSet.setDrawFilled(true);
                    lineDataSet.setFillDrawable(ContextCompat.getDrawable(getContext(),R.drawable.chart_gradient));
                    lineChart.getLegend().setEnabled(false);
                    Description d = new Description();
                    d.setText("");
                    lineChart.setDescription(d);
                    lineChart.invalidate();


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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_overview_charts, container, false);


        oneDay = view.findViewById(R.id.chart_button_one_day);
        fiveDay = view.findViewById(R.id.chart_button_five_days);
        oneMonth = view.findViewById(R.id.chart_button_one_month);
        sixMonth = view.findViewById(R.id.chart_button_six_months);
        oneYear = view.findViewById(R.id.chart_button_one_year);
        webViewLoading = view.findViewById(R.id.chart_activity_loading);
        getChartData(ticker, "1D", view.getContext());
        currentPriceTV = view.findViewById(R.id.current_price_chart_tv);
        getSingleValue(ticker, view.getContext());
        lineChart = view.findViewById(R.id.linechart);
        oneDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFrequencyClick(view);
            }
        });
        oneMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFrequencyClick(view);
            }
        });
        fiveDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFrequencyClick(view);
            }
        });
        sixMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFrequencyClick(view);
            }
        });
        oneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFrequencyClick(view);
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
