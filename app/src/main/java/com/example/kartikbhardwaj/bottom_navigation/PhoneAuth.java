package com.example.kartikbhardwaj.bottom_navigation;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PhoneAuth extends AppCompatActivity {

    MaterialButton materialButton;
    TextInputEditText phone, sc_name;
    String input_scName, input_phoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        materialButton = findViewById(R.id.btn_send_otp);
        phone = findViewById(R.id.input_phoneNo);
        sc_name = findViewById(R.id.input_scName);
        materialButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input_phoneNo = String.valueOf(phone.getText());
                input_scName = String.valueOf(sc_name.getText());

                Log.d("OTPAUTH",input_phoneNo);
//                readSMS();
                boolean isPhoneValid = checkvalid(input_phoneNo);
                if(isPhoneValid){
                    //Volley Code Goes Here
                }
                else{
                    Toast.makeText(PhoneAuth.this,"InvalidPhoneNumber",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    protected void readSMS() {
        StringBuilder smsBuilder = new StringBuilder();
        final String SMS_URI_INBOX = "content://sms/inbox";
        final String SMS_URI_ALL = "content://sms/";
        try {
            Uri uri = Uri.parse(SMS_URI_INBOX);
            String[] projection = new String[]{"_id", "address", "person", "body", "date", "type"};
            Cursor cur = getContentResolver().query(uri, projection, "address='+123456789'", null, "date desc");
            if (cur.moveToFirst()) {
                int index_Address = cur.getColumnIndex("address");
                int index_Person = cur.getColumnIndex("person");
                int index_Body = cur.getColumnIndex("body");
                int index_Date = cur.getColumnIndex("date");
                int index_Type = cur.getColumnIndex("type");
                do {
                    String strAddress = cur.getString(index_Address);
                    int intPerson = cur.getInt(index_Person);
                    String strbody = cur.getString(index_Body);
                    Log.e("Messages", strbody);
                    long longDate = cur.getLong(index_Date);
                    int int_Type = cur.getInt(index_Type);

                    smsBuilder.append("[ ");
                    smsBuilder.append(strAddress + ", ");
                    smsBuilder.append(intPerson + ", ");
                    smsBuilder.append(strbody + ", ");
                    smsBuilder.append(longDate + ", ");
                    smsBuilder.append(int_Type);
                    smsBuilder.append(" ]\n\n");
                } while (cur.moveToNext());

                if (!cur.isClosed()) {
                    cur.close();
                    Log.e("Messages", "Closed");
                    cur = null;
                }
            } else {
                Log.e("Messages", "Not Found");
                smsBuilder.append("no result!");
            } // end if
        } catch (Exception e) {
            Log.e("Messages", "ERRROORRR", e);

        }
    }

    public boolean checkvalid(String s)
    {
        if(s.length()!=10 && s.length() != 13)
        {
            return false;
        }
        if(s.length()==13)
        {
            String ss = s.substring(0,3);
            if(!(ss.equals("+91")))
                return false;
            input_phoneNo = s.substring(3);
        }
        else{
            for(int i = 0;i < s.length(); i++)
            {
                if(!(Character.isDigit(s.charAt(i))))
                {
                    return false;
                }
            }
        }
        return true;
    }
}
