package com.example.kartikbhardwaj.bottom_navigation.stories;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewsPageActivity extends AppCompatActivity {
    private String name,description,imageURL,date,source, url, author;
    private TextView nameTv, descTv, sourceTv, dateTv, authorTv, newsContentTv,loginText;
    private SimpleDraweeView image;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(getApplicationContext());
        setContentView(R.layout.activity_news_page);
        nameTv=findViewById(R.id.newsNameTV);
        dateTv=findViewById(R.id.newsDateTV);
        sourceTv=findViewById(R.id.newsSourceTV);
        image=findViewById(R.id.newsThumbImage);
        authorTv=findViewById(R.id.newsAuthorTV);
        newsContentTv = findViewById(R.id.newsContent);

        name= getIntent().getStringExtra("newsname");
        description= getIntent().getStringExtra("newsdesc");
        imageURL= getIntent().getStringExtra("newsimageurl");
        date=getIntent().getStringExtra("newsdate");
        source=getIntent().getStringExtra("newssource");
        url=getIntent().getStringExtra("newsurl");
        author=getIntent().getStringExtra("newsauthor");
        nameTv.setText(name);
        sourceTv.setText(source);
        dateTv.setText(date);
        image.setImageURI(imageURL);
        SharedPreferences sharedPreferences = getSharedPreferences("AUTHENTICATION", 0);
        final String token = sharedPreferences.getString("token", "Not found!");
        Log.e("RealityCheck",token);
        final Map<String, String> mHeaders = new HashMap<String, String>();
        mHeaders.put("token",token);
        mHeaders.put("url",url);
        if(author.equals("null")){
            authorTv.setText("");
        }
        else{
            authorTv.setText(author);
        }

        try {
            RequestQueue requestQueue;
            requestQueue = Volley.newRequestQueue(getBaseContext());
            String URL = "http://api.cyllide.com/api/news/get";



            final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        newsContentTv.setText(jsonObject.getString("message"));

                        Log.e("RealityCheck",response);
                        Log.e("RealityCheck","Inside onResponse");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            })


            {
                @Override
                public Map<String, String> getHeaders () {
                    return mHeaders;
                }



                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse nr) {
                    int n = nr.statusCode;
                    Log.d("Res Code",""+n);
                    return super.parseNetworkResponse(nr);
                }

            };

            requestQueue.add(stringRequest);
            Log.e("RealityCheck","Request sent");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

        //Volley Code Goes Here
