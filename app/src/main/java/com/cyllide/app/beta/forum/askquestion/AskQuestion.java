package com.cyllide.app.beta.forum.askquestion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.ArrayMap;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.cyllide.app.beta.AppConstants;
import com.cyllide.app.beta.R;
import com.cyllide.app.beta.forum.ForumActivity;
import com.cyllide.app.beta.forum.questionlist.questionPage.QuestionAnswerActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.JsonArray;
import com.nex3z.togglebuttongroup.MultiSelectToggleGroup;
import com.nex3z.togglebuttongroup.button.LabelToggle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

public class AskQuestion extends AppCompatActivity {

    MaterialButton btn1, btn2, btn3, btn4, askQuestionBtn;
    ImageView closeButton;
    TextInputEditText questionText;
    private RequestQueue askQuestionQueue;
    private Map<String, String> requestHeaders = new ArrayMap<String, String>();
    ArrayList<String> forumTags = new ArrayList<>();
    MultiSelectToggleGroup toggleTags;
    JsonArray tags = new JsonArray();
    ArrayList<String > selectedTags = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);


        askQuestionBtn = findViewById(R.id.proceedtoask);
        questionText = findViewById(R.id.question_text);
        closeButton = findViewById(R.id.close_ask_question);


        askQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(questionText.getText().toString().length()>9){
                    askQuestionVolley();
                }
                else{
                    Snackbar snackbar = Snackbar
                            .make(findViewById(R.id.root_layout),"Question must be at least 10 characters long", Snackbar.LENGTH_LONG);
                    snackbar.show();
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
        forumTagsVolley();
    }

    private void askQuestionVolley() {
        askQuestionQueue = Volley.newRequestQueue(this);
        requestHeaders.put("token", AppConstants.token);
        requestHeaders.put("body",questionText.getText().toString());
//        if(btn1.getTag().toString()=="pressed"){
//            tags.add(btn1.getText().toString());
//        }
//        if(btn2.getTag().toString()=="pressed"){
//            tags.add(btn2.getText().toString());
//        }
//        if(btn3.getTag().toString()=="pressed"){
//            tags.add(btn3.getText().toString());
//        }
//        if(btn4.getTag().toString()=="pressed"){
//            tags.add(btn4.getText().toString());
//        }
        for(String tag: selectedTags){
            tags.add(tag);
        }
        Log.d("Tags",tags.toString());
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

    void forumTagsVolley(){
        final RequestQueue forumTagsQueue = Volley.newRequestQueue(AskQuestion.this);
        String url = getString(R.string.apiBaseURL) + "forum/tags";
        StringRequest forumRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    toggleTags = findViewById(R.id.tags);

                    JSONArray jsonArray = new JSONObject(response).getJSONArray("data");
                    for(int i=0;i<jsonArray.length();i++){
                        forumTags.add(jsonArray.getString(i));
                    }
                    Log.d("ForumActivity", String.valueOf(forumTags.size()));
                    for(int i = 0; i<forumTags.size(); i++) {
                        int pixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());

                        LabelToggle lt = new LabelToggle(AskQuestion.this);
                        lt.setId(i);
                        final int id_ = lt.getId();
                        lt.setText(forumTags.get(i));
                        LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        buttonLayoutParams.setMargins(pixels,pixels,pixels,pixels);
                        lt.setPadding(pixels,pixels,pixels,pixels);
                        lt.setLayoutParams(buttonLayoutParams);
                        lt.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,6, getResources().getDisplayMetrics()));

//            lt.setMargin
                        toggleTags.addView(lt);



                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                toggleTags.setOnCheckedChangeListener(new MultiSelectToggleGroup.OnCheckedStateChangeListener() {
                    @Override
                    public void onCheckedStateChanged(MultiSelectToggleGroup group, int checkedId, boolean isChecked) {
                        Log.d("CheckedID",Integer.toString(checkedId));


                        if(!selectedTags.contains(forumTags.get(checkedId))){
                            selectedTags.add(forumTags.get(checkedId));
                        }
                        else{
                            selectedTags.remove(forumTags.get(checkedId));
                        }

                        Log.d("ForumActivity",Integer.toString(selectedTags.size()));

                    }
                });

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        forumTagsQueue.add(forumRequest);
    }
}
