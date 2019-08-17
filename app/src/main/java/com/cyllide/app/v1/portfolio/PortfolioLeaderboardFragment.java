package com.cyllide.app.v1.portfolio;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.ArrayMap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
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
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;

public class PortfolioLeaderboardFragment extends Fragment {
    RecyclerView recyclerView;
    ArrayList<LeaderBoardModel> data=new ArrayList<LeaderBoardModel>();
    Map<String,String> LeaderBoardMap;
    LinearLayout position1ll, position2ll,position3ll;
    CircleImageView position1cv, position2cv, position3cv;
    TextView position1tv, position2tv, position3tv;
    Context context;
    Handler handler = new Handler();
    Runnable runnable;
    int delay = 60*1000;

    @Override
    public void onResume() {
        handler.postDelayed( runnable = new Runnable() {
            public void run() {
                getLeaderboard();
                handler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    public void onPause() {
        handler.removeCallbacks(runnable);
        super.onPause();
    }

    public PortfolioLeaderboardFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_portfolio_leaderboard,null);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.pg_leaderboard_rv);
        position1ll = view.findViewById(R.id.layout_pos_1);
        position2ll = view.findViewById(R.id.layout_pos_2);
        position3ll = view.findViewById(R.id.layout_pos_3);

        position1cv = view.findViewById(R.id.profilePic_pos_1);
        position2cv = view.findViewById(R.id.profilePic_pos_2);
        position3cv = view.findViewById(R.id.profilePic_pos_3);

        position1tv = view.findViewById(R.id.name_pos_1);
        position2tv = view.findViewById(R.id.name_pos_2);
        position3tv = view.findViewById(R.id.name_pos_3);
      getLeaderboard();

    }

    void getLeaderboard()
    {
        data = new ArrayList<>();
        String uri=  getResources().getString(R.string.apiBaseURL) + "contest/leaderboard";
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        LeaderBoardMap = new ArrayMap();
        LeaderBoardMap.put("token", AppConstants.token);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray responseArray = new JSONObject(response).getJSONArray("data");
                    int length = responseArray.length();
                    for(int i=length-1;i>=0;i--)
                    {
                        data.add(new LeaderBoardModel(responseArray.getJSONObject(i).getString("userName"),responseArray.getJSONObject(i).getString("cyllidePoints"),responseArray.getJSONObject(i).getString("profilePic"),i+1));

                    }
                    position1ll.setVisibility(View.GONE);
                    position2ll.setVisibility(View.GONE);
                    position3ll.setVisibility(View.GONE);
                    if(length >=3){
                        position1ll.setVisibility(View.VISIBLE);
                        Glide.with(context).load(Uri.parse(data.get(1).getProfileUri())).into(position1cv);
                        image(position1cv,data.get(1));
                        position1tv.setText(data.get(1).getName());


                        position2ll.setVisibility(View.VISIBLE);
                        Glide.with(context).load(Uri.parse(data.get(0).getProfileUri())).into(position2cv);
                        image(position2cv,data.get(0));

                        position2tv.setText(data.get(0).getName());

                        position3ll.setVisibility(View.VISIBLE);
                        Glide.with(context).load(Uri.parse(data.get(2).getProfileUri())).into(position3cv);
                        image(position3cv,data.get(2));

                        position3tv.setText(data.get(2).getName());

                    }
                    else if(length >=2){
                        position3ll.setVisibility(View.VISIBLE);
                        Glide.with(context).load(Uri.parse(data.get(1).getProfileUri())).into(position3cv);
                        image(position3cv,data.get(1));

                        position3tv.setText(data.get(1).getName());


                        position2ll.setVisibility(View.VISIBLE);
                        Glide.with(context).load(Uri.parse(data.get(0).getProfileUri())).into(position2cv);
                        image(position2cv,data.get(0));

                        position2tv.setText(data.get(0).getName());

                        position1ll.setVisibility(View.GONE);

                    }
                    else if(length>=1){
                        position1ll.setVisibility(View.VISIBLE);
                        Glide.with(context).load(Uri.parse(data.get(0).getProfileUri())).into(position1cv);
                        position1tv.setText(data.get(0).getName());
                        image(position1cv,data.get(0));


                        position2ll.setVisibility(View.GONE);
                        position3ll.setVisibility(View.GONE);



                    }
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    LeaderBoardAdapter adapter =new LeaderBoardAdapter(data);
                    recyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("kartik", error.toString());
            }
        }){
            @Override
            public Map<String,String> getHeaders(){
                return LeaderBoardMap;
            }
        };
        requestQueue.add(stringRequest);
    }

    void image(CircleImageView profileImag, LeaderBoardModel model) {

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color = generator.getColor(AppConstants.username);
        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .width(100)
                .height(100)
                .endConfig()
                .buildRect(Character.toString(model.getName().charAt(0)).toUpperCase(), color);
        if (model.getProfileUri().equals("https://firebasestorage.googleapis.com/v0/b/cyllide.appspot.com/o/defaultuser.png?alt=media&token=0453d4ba-82e8-4b6c-8415-2c3761d8b345")) {
            profileImag.setImageDrawable(drawable);
        }

    }
}
