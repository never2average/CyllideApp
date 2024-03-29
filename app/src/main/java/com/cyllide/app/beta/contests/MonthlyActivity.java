package com.cyllide.app.beta.contests;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.MainActivity;
import com.cyllide.app.beta.contests.portfolioRV.LeaderboardsActivity;
import com.cyllide.app.beta.contests.portfolioRV.PortfolioModel;
import com.cyllide.app.beta.R;
import com.google.android.material.tabs.TabLayout;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MonthlyActivity extends AppCompatActivity implements PortfolioPickerDialogFragment.PortfolioPickerClickListener {

    private String curr_selection = "nifty500";
    private int curr_selection_index = 0;
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
    private Button joinButton, viewButton;
    private LinearLayout linearLayout;
    LinearLayout.LayoutParams param, invisibleParam;
    ImageView imgView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        imgView=findViewById(R.id.contestbackbutton);
        niftyButton = findViewById(R.id.nifty_button);
        contestSignUpsTV = findViewById(R.id.contest_sign_ups);

        linearLayout = findViewById(R.id.if_portfolio_already_exists);

        descTV = findViewById(R.id.desc_tv);
        curr_selection_index=3;
        updateDisplay();

        niftyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                curr_selection="nifty500";
                curr_selection_index = 3;
                AppConstants.capex="nifty500";
                getVolleyData(curr_selection);
                updateDisplay();
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("Monthly"));


        param = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );


        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MonthlyActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        joinButton = findViewById(R.id.contest_join_button);
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialog = new PortfolioPickerDialogFragment();
                dialog.show(getSupportFragmentManager(), "PortfolioPicker");

            }
        });

        viewButton = findViewById(R.id.contest_view_button);

        viewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent leaderboardIntent = new Intent(MonthlyActivity.this, LeaderboardsActivity.class);
                startActivity(leaderboardIntent);
                finish();
            }
        });

        getVolleyData(curr_selection);


    }

    private void getVolleyData(String curr_selection){
        String url = getResources().getString(R.string.apiBaseURL)+"contest/list";
        requestQueue = Volley.newRequestQueue(MonthlyActivity.this);
        requestHeader.put("token",AppConstants.token);
        requestHeader.put("capex",curr_selection);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("error1",response);
                    JSONObject responseObject = new JSONObject(response).getJSONArray("message").getJSONObject(0);
                    contestSignUpsTV.setText("No. of Participants : "+responseObject.getString("signUps"));
                    if(responseObject.getBoolean("isAlreadyIn") == true) {
                        viewButton.setVisibility(View.VISIBLE);
                    }
                    else{
                        viewButton.setVisibility(View.GONE);
                    }
                    AppConstants.contestID=responseObject.getJSONObject("_id").getString("$oid");
                    Log.d("MonthlyActivity",responseObject.toString());


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

        requestQueue.add(stringRequest);

    }

    private void updateDisplay(){
        descTV.setText(descs[curr_selection_index]);
    }

    @Override
    public void onItemClick(PortfolioModel portfolio) {
        Log.e("MonthlyActivity", "portfolio "+ portfolio.getPortfolioName()+" was chosen");
    }

    @Override
    public void onBackPressed(){
        Intent returnHome = new Intent(this,MainActivity.class);
        startActivity(returnHome);
        finish();
    }
}
