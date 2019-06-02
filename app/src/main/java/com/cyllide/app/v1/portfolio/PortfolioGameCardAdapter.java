package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.core.content.ContextCompat;

import com.cyllide.app.v1.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class PortfolioGameCardAdapter extends BaseAdapter {

    private List<String> data;
    private Context context;

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
        }
        ArrayList<Entry> yAxisValues = new ArrayList<>();
        for(int i=0;i<100;i++){
            yAxisValues.add(new Entry((float)i,(float)(2*i+1)));
        }
        LineChart lineChart = v.findViewById(R.id.portfolio_game_home_chart);

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
}