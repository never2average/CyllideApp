package com.cyllide.app.v1.portfolio.tabs;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView previousClose, open, bid, ask, daysRange, yearlyRange, volume, avgVolume, marketCap, beta, peRatio, eps, earnings, dividendYield, exDividend, yearlyEst;
    private RequestQueue statsQueue;
    Map<String,String> statsMap = new ArrayMap<>();
    private OnFragmentInteractionListener mListener;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FuturesChartsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
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
        statsVolley();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stats, container, false);
        previousClose=view.findViewById(R.id.dtgc_previousclose);
        open=view.findViewById(R.id.dtgc_open);
        bid=view.findViewById(R.id.dtgc_bid);
        ask=view.findViewById(R.id.dtgc_ask);
        daysRange=view.findViewById(R.id.dtgc_daysrange);
        yearlyRange=view.findViewById(R.id.dtgc_52weekrange);
        volume=view.findViewById(R.id.dtgc_volume);
        avgVolume=view.findViewById(R.id.dtgc_avgvolume);
        marketCap=view.findViewById(R.id.dtgc_marketcap);
        beta=view.findViewById(R.id.dtgc_beta);
        peRatio=view.findViewById(R.id.dtgc_peratio);
        eps=view.findViewById(R.id.dtgc_eps);
        earnings=view.findViewById(R.id.dtgc_earnings);
        dividendYield=view.findViewById(R.id.dtgc_fwddividendyield);
        exDividend=view.findViewById(R.id.dtgc_exdividentdate);
        yearlyEst=view.findViewById(R.id.dtgc_1ytargetest);
        statsVolley();
        return view;
    }

    void statsVolley() {
        statsQueue = Volley.newRequestQueue(getContext());
        statsMap.put("ticker", AppConstants.currTicker);
        String url = getContext().getResources().getString(R.string.dataApiBaseURL)+"stocks/summary";
        StringRequest statsRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    previousClose.setText("₹ "+jsonObject.getString("Previous close"));
                    open.setText("₹ "+jsonObject.getString("Open"));
                    bid.setText(jsonObject.getString("Bid"));
                    ask.setText(jsonObject.getString("Ask"));
                    daysRange.setText("₹ "+jsonObject.getString("Day's range"));
                    yearlyRange.setText("₹ "+jsonObject.getString("52-week range"));
                    volume.setText(jsonObject.getString("Volume"));
                    avgVolume.setText(jsonObject.getString("Avg. volume"));
                    marketCap.setText("₹ "+jsonObject.getString("Market cap"));
                    beta.setText(jsonObject.getString("Beta (3Y monthly)"));
                    peRatio.setText(jsonObject.getString("PE ratio (TTM)"));
                    eps.setText(jsonObject.getString("EPS (TTM)"));
                    earnings.setText(jsonObject.getString("Earnings date"));
                    dividendYield.setText(jsonObject.getString("Forward dividend & yield"));
                    exDividend.setText(jsonObject.getString("Ex-dividend date"));
                    yearlyEst.setText(jsonObject.getString("1y target est"));
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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
