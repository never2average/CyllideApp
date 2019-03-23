package com.example.kartikbhardwaj.bottom_navigation.Portfolio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kartikbhardwaj.bottom_navigation.PasswordChangeStatus;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqAdapter;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.FaqModal;
import com.example.kartikbhardwaj.bottom_navigation.faq_view.Faq_Activity;

import java.util.ArrayList;
import java.util.List;
import com.example.kartikbhardwaj.bottom_navigation.R;
import com.google.android.material.button.MaterialButton;


public class MyPortfolio extends AppCompatActivity {
    EditText newportfolioName;
    MaterialButton createPortfolio;
    String portfolioName;
    TextView errorhint;

    RecyclerView RV;


    String portfolionames[]={"p******","p******","p******","p******","p******","p******","p******","p******","p******","p******"};
    String returnsValue[]={"$$","$$","$$","$$","$$","$$","$$","$$","$$","$$"};
    private List<MyPortfolioModel> dummyData() {
        List<MyPortfolioModel> data = new ArrayList<>(12);
        for (int i = 0; i < 10; i++) {
            data.add(new MyPortfolioModel(portfolionames[i], returnsValue[i]));
        }//data is the list of objects to be set in the list item
        return data;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_portfolio);
        RV = findViewById(R.id.my_portfolio);
        final Context context = MyPortfolio.this;


        createPortfolio=findViewById(R.id.create_button);
        newportfolioName = findViewById(R.id.portfolio_name);



        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(context));
        List<MyPortfolioModel> data = dummyData();
        final MyPortfolioAdapter mAdapter = new MyPortfolioAdapter(data);
        RV.setAdapter(mAdapter);


     createPortfolio.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             portfolioName=newportfolioName.getText().toString();


             if(portfolioName.equals(""))
             {
                 errorhint.setText("This field cannot be empty ");

             }
               else

             {

                 Intent portfolioIntent = new Intent(MyPortfolio.this,PortfolioActivity.class);
                 portfolioIntent.putExtra("buttonstatus","on");
                 portfolioIntent.putExtra("newStockName",newportfolioName.getText().toString());
                 PasswordChangeStatus.buttonstatus=true;
                 startActivity(portfolioIntent);
             }

         }
        }
     );

    }
}
