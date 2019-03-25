package com.example.kartikbhardwaj.bottom_navigation.Contests;

import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.Contests.PortfolioRV.PortfolioModel;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.tabs.TabLayout;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MonthlyActivity extends AppCompatActivity implements PortfolioPickerDialogFragment.PortfolioPickerClickListener {

    private String curr_selection = "smallcap";
    private int curr_selection_index = 0;
    //TODO: Shift string to res
    private String[] descs = {"Small cap is a term used to classify companies with a relatively small market capitalization. A company’s market capitalization is the market value of its outstanding shares. In India, normally a company below market capitalization of Rs.5000 crores is classified as small cap company.",
    "A company’s market capitalization is the market value of its outstanding shares. In India, normally a company with market capitalization above Rs.5000 crores and less than Rs.20000 crores is considered as mid cap company.",
    "Large cap is a term used to classify companies with a relatively large market capitalization. A company's market capitalization is the market value of its outstanding shares. In India, normally companies with the market capitalization higher than Rs.20,000 crores is considered as Large cap companies.",
    "A company’s market capitalization is the market value of its outstanding shares. A company whose market cap is among the top 500 companies traded in NSE is classified under Nifty 500"};
    RequestQueue requestQueue;
    Map<String,String> requestHeader = new ArrayMap<>();
    private LabelToggle smallCapButton;
    private LabelToggle midCapButton;
    private LabelToggle largeCapButton;
    private LabelToggle niftyButton;
    private TextView descTV, contestSignUpsTV;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ImageView imgView=findViewById(R.id.contestbackbutton);
        smallCapButton = findViewById(R.id.smallcap_button);
        midCapButton = findViewById(R.id.midcap_button);
        largeCapButton = findViewById(R.id.largecap_button);
        niftyButton = findViewById(R.id.nifty_button);
        contestSignUpsTV = findViewById(R.id.contest_sign_ups);

        descTV = findViewById(R.id.desc_tv);
        updateDisplay();

        smallCapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection = "smallcap";
                curr_selection_index = 0;
                getVolleyData(curr_selection);
                updateDisplay();
            }
        });

        midCapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection="midcap";
                curr_selection_index = 1;
                getVolleyData(curr_selection);
                updateDisplay();
            }
        });

        largeCapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection="largecap";
                curr_selection_index = 2;
                getVolleyData(curr_selection);
                updateDisplay();
            }
        });

        niftyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection="nifty500";
                curr_selection_index = 3;
                getVolleyData(curr_selection);
                updateDisplay();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));

        

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Button joinButton  = findViewById(R.id.contest_join_button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MonthlyActivity.this, "Wait for a magic trick!",Toast.LENGTH_LONG).show();

            }
        });

        getVolleyData(curr_selection);


    }

    private void getVolleyData(String curr_selection){
        String url = "http://api.cyllide.com/api/client/contest/list";
        requestQueue = Volley.newRequestQueue(MonthlyActivity.this);
        requestHeader.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        //TODO remove hardcoded token
        requestHeader.put("capex",curr_selection);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    String responseObject = new JSONObject(response).getJSONArray("message").getJSONObject(0).getString("signUps");
                    contestSignUpsTV.setText("No. of Participants : "+responseObject);
                    Log.d("MonthlyActivity",responseObject);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MonthlyActivityResponse",error.toString());

            }
        })
        {
            @Override
            public Map<String, String> getHeaders () {
                return requestHeader;
            }
        };
//                DialogFragment dialog = new PortfolioPickerDialogFragment();
//                dialog.show(getSupportFragmentManager(), "PortfolioPicker");

        requestQueue.add(stringRequest);

    }

    private void updateDisplay(){
        descTV.setText(descs[curr_selection_index]);
    }

    @Override
    public void onItemClick(PortfolioModel portfolio) {

        Log.e("MonthlyActivity", "portfolio "+ portfolio.getPortfolioName()+" was chosen");

    }
}
