package com.example.kartikbhardwaj.bottom_navigation.stories;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.kartikbhardwaj.bottom_navigation.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class NewsData extends AppCompatActivity {

    String url = "https://newsapi.org/v2/top-headlines?country=in&category=business&apiKey=f6bddf738e64468280f0a7173b265b41";
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newsdata); //ForTesting ShouldBeRemovedAfterwards

        textView=findViewById(R.id.jsonarraylist);  //ForTesting ShouldBeRemovedAfterwards

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>()

                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            if (response.getJSONObject("status").equals("ok")) { }
                            else
                                {
                                    try
                                    {
                                        String headers[]={"title","description","url","urlToImage","publishedAt"};

                                        List<List<String>> array = new ArrayList<List<String>>();

                                        for(int i=0;i<response.getJSONArray("articles").length();i++)
                                        {
                                            array.add(new ArrayList<String>());
                                            for (int j=0;j<headers.length;j++)
                                            {
                                                array.get(i).add((response.getJSONArray("articles").getJSONObject(i).getJSONObject(headers[j])).toString());
                                                textView.setText(array.get(i).get(j)); //ForTesting ShouldBeRemovedAfterwards

                                            }
                                        }

                                        //Toast.makeText(NewsData.this, "ResponseSuccesful", Toast.LENGTH_LONG).show();
                                     }
                                     catch (JSONException e)
                                     {
                                        e.printStackTrace();
                                        //Toast.makeText(NewsData.this, "ResponseUnsuccesful", Toast.LENGTH_LONG).show();
                                     }
                                }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },
                        new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                    }
                });

// Access the RequestQueue through your singleton class.
        NewsDataSingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}
