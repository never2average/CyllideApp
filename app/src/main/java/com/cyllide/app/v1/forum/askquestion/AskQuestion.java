package com.cyllide.app.v1.forum.askquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.v1.AppConstants;
import com.cyllide.app.v1.R;
import com.cyllide.app.v1.forum.ForumActivity;
import com.cyllide.app.v1.forum.questionlist.questionPage.QuestionAnswerActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class AskQuestion extends AppCompatActivity {

    MaterialButton btn1, btn2, btn3, btn4, askQuestionBtn;
    ImageView closeButton;
    TextInputEditText questionText;
    private RequestQueue askQuestionQueue;
    private Map<String, String> requestHeaders = new ArrayMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);

        btn1=findViewById(R.id.btntag1);
        btn2=findViewById(R.id.btntag2);
        btn3=findViewById(R.id.btntag3);
        btn4=findViewById(R.id.btntag4);
        askQuestionBtn = findViewById(R.id.proceedtoask);
        questionText = findViewById(R.id.question_text);
        closeButton = findViewById(R.id.close_ask_question);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn1.getTag().equals("pressed"))
                {
                    btn1.setStrokeColorResource(R.color.colorPrimary);
                    btn1.setBackgroundColor(getColor(R.color.white));
                    btn1.setTextColor(getColor(R.color.colorPrimary));
                    btn1.setTag("released");
                }
                else
                {
                    btn1.setStrokeColorResource(R.color.white);
                    btn1.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn1.setTextColor(getColor(R.color.white));
                    btn1.setTag("pressed");
                }
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn2.getTag().equals("pressed"))
                {
                    btn2.setStrokeColorResource(R.color.colorPrimary);
                    btn2.setBackgroundColor(getColor(R.color.white));
                    btn2.setTextColor(getColor(R.color.colorPrimary));
                    btn2.setTag("released");
                }
                else
                {
                    btn2.setStrokeColorResource(R.color.white);
                    btn2.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn2.setTextColor(getColor(R.color.white));
                    btn2.setTag("pressed");
                }
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn3.getTag().equals("pressed"))
                {
                    btn3.setStrokeColorResource(R.color.colorPrimary);
                    btn3.setBackgroundColor(getColor(R.color.white));
                    btn3.setTextColor(getColor(R.color.colorPrimary));
                    btn3.setTag("released");
                }
                else
                {
                    btn3.setStrokeColorResource(R.color.white);
                    btn3.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn3.setTextColor(getColor(R.color.white));
                    btn3.setTag("pressed");
                }
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn4.getTag().equals("pressed"))
                {
                    btn4.setStrokeColorResource(R.color.colorPrimary);
                    btn4.setBackgroundColor(getColor(R.color.white));
                    btn4.setTextColor(getColor(R.color.colorPrimary));
                    btn4.setTag("released");
                }
                else
                {
                    btn4.setStrokeColorResource(R.color.white);
                    btn4.setBackgroundColor(getColor(R.color.colorPrimary));
                    btn4.setTextColor(getColor(R.color.white));
                    btn4.setTag("pressed");
                }
            }
        });

        askQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionText.getText().toString().length()>15){
                    askQuestionVolley();
                }
            }
        });
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AskQuestion.this, ForumActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void askQuestionVolley() {
        askQuestionQueue = Volley.newRequestQueue(this);
        requestHeaders.put("token", AppConstants.token);
        requestHeaders.put("body",questionText.getText().toString());
        JsonArray tags = new JsonArray();
        if(btn1.getTag().toString()=="pressed"){
            tags.add(btn1.getText().toString());
        }
        if(btn2.getTag().toString()=="pressed"){
            tags.add(btn2.getText().toString());
        }
        if(btn3.getTag().toString()=="pressed"){
            tags.add(btn3.getText().toString());
        }
        if(btn4.getTag().toString()=="pressed"){
            tags.add(btn4.getText().toString());
        }

        requestHeaders.put("tags",tags.toString());

        String requestEndpoint = getResources().getString(R.string.apiBaseURL)+"query/add";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, requestEndpoint, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("AskError",response);
                try {
                    String qid = new JSONObject(response).getJSONObject("ID").toString();
                    Intent intent = new Intent(getBaseContext(), QuestionAnswerActivity.class);
                    intent.putExtra("questionID",qid);
                    startActivity(intent);
                    finish();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AskError",error.toString());
                Toast.makeText(getBaseContext(),"Could not post question",Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                return requestHeaders;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int mStatusCode = response.statusCode;
                Log.d("whats failing", String.valueOf(mStatusCode));
                return super.parseNetworkResponse(response);
            }
        };
        askQuestionQueue.add(stringRequest);

    }
}
