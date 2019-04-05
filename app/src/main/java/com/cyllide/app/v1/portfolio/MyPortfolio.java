package com.cyllide.app.v1.portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.ConnectionStatus;
import com.cyllide.app.v1.MainActivity;
import com.cyllide.app.v1.R;
import com.google.android.material.button.MaterialButton;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MyPortfolio extends AppCompatActivity {
    EditText newportfolioNameTV;
    MaterialButton createPortfolio;
    private RequestQueue portfolioListRequestQueue;
    String selectedToggle = "None";
    LabelToggle smallCapLabelToggle, midCapLabelToggle, largeCapLabelToggle, niftyCapLabelToggle;
    private RequestQueue createPortfolioRequestQueue;
    ImageView backbutton;
    Map<String,String> createPortfolioRequestHeader = new ArrayMap<>();
    RecyclerView myPortfolioListRV;
    Map<String,String> myPortfolioRequestHeader = new ArrayMap<>();
    List<MyPortfolioModel> myPortfolioModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_portfolio);
        myPortfolioListRV = findViewById(R.id.my_portfolio);
        final Context context = MyPortfolio.this;


        createPortfolio=findViewById(R.id.my_portfolio_create_new_btn);
        newportfolioNameTV = findViewById(R.id.portfolio_name);
        smallCapLabelToggle = findViewById(R.id.my_portfolio_capex_small_cap_toggle_btn);
        midCapLabelToggle = findViewById(R.id.my_portfolio_capex_mid_cap_toggle_btn);
        largeCapLabelToggle = findViewById(R.id.my_portfolio_capex_large_cap_toggle_btn);
        niftyCapLabelToggle = findViewById(R.id.my_portfolio_capex_nifty_cap_toggle_btn);
        backbutton = findViewById(R.id.activity_my_portfolio_back_button);

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyPortfolio.this, MainActivity.class));
            }
        });

        smallCapLabelToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedToggle = "smallcap";
            }
        });
        midCapLabelToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedToggle = "midcap";
            }
        });
        largeCapLabelToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedToggle = "largecap";
            }
        });
        niftyCapLabelToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedToggle = "nifty500";
            }
        });






        myPortfolioListRV.setHasFixedSize(true);
        myPortfolioListRV.setLayoutManager(new LinearLayoutManager(context));
        getPortfolioListVolley(myPortfolioListRV);


        createPortfolio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(ConnectionStatus.connectionstatus){
                Log.d("capex",selectedToggle);
                String newPortfolioName = newportfolioNameTV.getText().toString();
                if(newPortfolioName == null){
                    Toast.makeText(MyPortfolio.this,"Please enter a Portfolio name!",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(newPortfolioName.length()<8){
                    Toast.makeText(MyPortfolio.this,"Portfolio name must be atleast 8 characters long!",Toast.LENGTH_SHORT).show();
                    return;
                }
                createNewPortfolio(newPortfolioName,selectedToggle);}
                else{
                    Toast.makeText(MyPortfolio.this,"Internet Connection Lost ",Toast.LENGTH_LONG).show();
                }



            }
        });




    }

    private void getPortfolioListVolley(final RecyclerView myPortfolioListRV){
        String url = getResources().getString(R.string.apiBaseURL)+"portfolio/display/all";
        portfolioListRequestQueue = Volley.newRequestQueue(MyPortfolio.this);
        myPortfolioRequestHeader.put("token",AppConstants.token);

        StringRequest myPortfolioStringRequest = new StringRequest(StringRequest.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONObject(response).getJSONArray("data");
                    populateRV(jsonResponse, myPortfolioListRV);


                } catch (JSONException e) {
                    Log.d("myPortfolio",e.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return myPortfolioRequestHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        portfolioListRequestQueue.add(myPortfolioStringRequest);




    }

    private void populateRV(JSONArray jsonArray, RecyclerView recyclerView) {
        myPortfolioModelList = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++){
            try {
                myPortfolioModelList.add(
                        new MyPortfolioModel(
                                jsonArray.getJSONObject(i).getString("portfolioName"),
                                jsonArray.getJSONObject(i).getString("portfolioCapex"),
                                jsonArray.getJSONObject(i).getJSONObject("_id").getString("$oid")
                        )
                );
                Log.d("portfolioHistory",jsonArray.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        MyPortfolioAdapter myPortfolioAdapter = new MyPortfolioAdapter(myPortfolioModelList);
        recyclerView.setAdapter(myPortfolioAdapter);
    }

    private void createNewPortfolio(String portfolioName, String capex){
        createPortfolioRequestQueue = Volley.newRequestQueue(MyPortfolio.this);
        createPortfolioRequestHeader.put("token","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjoiUHJpeWVzaCIsImV4cCI6MTU4NDQ4NjY0OX0.jyjFESTNyiY6ZqN6FNHrHAEbOibdg95idugQjjNhsk8");
        createPortfolioRequestHeader.put("name",portfolioName);
        createPortfolioRequestHeader.put("capex",capex);
        String url = getResources().getString(R.string.apiBaseURL)+"portfolio/create";
        StringRequest createPortfolioStringRequest = new StringRequest(StringRequest.Method.POST,url,  new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(jsonResponse.getString("data").equals("Portfolio Creation Successful")){
                        AppConstants.portfolioID = jsonResponse.getString("id");
                        Intent portfolioDetailsIntent = new Intent(MyPortfolio.this, PortfolioActivity.class);
                        startActivity(portfolioDetailsIntent);
                    }
                    else{
                        Toast.makeText(MyPortfolio.this,"Could Not Create Portfolios",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Log.d("error",e.toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Question Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                return createPortfolioRequestHeader;
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        createPortfolioRequestQueue.add(createPortfolioStringRequest);


    }

}
