package com.example.kartikbhardwaj.bottom_navigation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;

import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class PhoneAuthActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    EditText otp, mob;
    Button get_otp, verify_otp;
    private FirebaseAuth mAuth;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    TextView t;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);

        mAuth = FirebaseAuth.getInstance();

        otp = (EditText) findViewById(R.id.otp_edt);
        mob = (EditText) findViewById(R.id.mobile_edt);
        get_otp = (Button) findViewById(R.id.otp_button);
        verify_otp = (Button) findViewById(R.id.verify_btn);

        otp.setVisibility(View.INVISIBLE);
        verify_otp.setVisibility(View.INVISIBLE);

        get_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + mob.getText().toString(),
                        60,
                        TimeUnit.SECONDS,
                        PhoneAuthActivity.this,
                        mCallbacks);

                otp.setVisibility(View.VISIBLE);
                verify_otp.setVisibility(View.VISIBLE);
                get_otp.setVisibility(View.INVISIBLE);
                mob.setVisibility(View.INVISIBLE);

            }
        });

        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, otp.getText().toString());
                signInWithPhoneAuthCredential(phoneAuthCredential);


            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

                Toast.makeText(getBaseContext(), "Verification complete..", Toast.LENGTH_LONG).show();

                mVerificationInProgress = false;

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                Toast.makeText(getBaseContext(), "Verification failed..", Toast.LENGTH_LONG).show();
                t.setText(e.toString());

                mVerificationInProgress = false;


                if (e instanceof FirebaseAuthInvalidCredentialsException) {

                    Toast.makeText(getBaseContext(), "Invalid Phone number..", Toast.LENGTH_LONG).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {

                    Toast.makeText(getBaseContext(), "Too many requests..", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                Toast.makeText(getBaseContext(), "Verification code sent..", Toast.LENGTH_LONG).show();


                mVerificationId = verificationId;
                mResendToken = token;

            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getBaseContext(), "Successfully verified..", Toast.LENGTH_LONG).show();

                            FirebaseUser user = task.getResult().getUser();

                        } else {

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {

                                Toast.makeText(getBaseContext(), "Invalid OTP..", Toast.LENGTH_LONG).show();

                            }

                        }
                    }
                });
    }

}
