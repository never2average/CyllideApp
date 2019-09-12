package com.cyllide.app.beta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class PasswordChangeActivity extends AppCompatActivity {

     TextInputLayout currPlayout;
     TextInputLayout newPlayout;
    TextInputLayout againPlayout;
    TextInputEditText currpaswrd;
     TextInputEditText newpaswrd;
    TextInputEditText againpaswrd;
    Button savePaswrd;
    String currentPaswrd_string;
    String newpaswrd_string;
    String againnewpaswrd_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_change);


        currPlayout=findViewById(R.id.EnterCurrPaswrdlayout);
        newPlayout=findViewById(R.id.EnternewPaswrdlayout);
        againPlayout=findViewById(R.id.entrpaswrdAgainlayout);
        currpaswrd=findViewById(R.id.EnterCurrPaswrd);
        newpaswrd=findViewById(R.id.EnternewPaswrd);
        againpaswrd=findViewById(R.id.entrpaswrdAgain);
        savePaswrd=findViewById(R.id.save_paswrd);




        newpaswrd_string=newpaswrd.getText().toString();
        currentPaswrd_string=currpaswrd.getText().toString();
        againnewpaswrd_string=againpaswrd.getText().toString();
        currpaswrd.setShowSoftInputOnFocus(false);
        newpaswrd.setShowSoftInputOnFocus(false);
        againpaswrd.setShowSoftInputOnFocus(false);


        savePaswrd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordChangeStatus.passwordChangeStatusfunc(true);


                Intent profileIntent = new Intent(PasswordChangeActivity.this, ProfileActivity.class);
                startActivity(profileIntent);
                finish();

//                if(!currentPaswrd_string.equals("")){
//                    currPlayout.setErrorEnabled(true);
//                    currPlayout.setError("Please enter your current Password");
//                }
//                if(!newpaswrd_string.equals("")){
//                    newPlayout.setEnabled(true);
//
//                    newPlayout.setError("Please Enter Your New Password");
//                }
//                if(!againnewpaswrd_string.equals(""))
//                {    againPlayout.setErrorEnabled(true);
//                    againPlayout.setError("Please Confirm Your Password");
//
//                }

//           final Snackbar snackbar = Snackbar.make(findViewById(R.id.Password_change_Activity),"Your Password has been saved ",Snackbar.LENGTH_LONG);
//             snackbar.setAction("Dismiss", new View.OnClickListener() {
//                 @Override
//                 public void onClick(View v) {
//                     snackbar.dismiss();
//
//                 }
//             });
//             snackbar.show();
////

            }
        });



    }
}
