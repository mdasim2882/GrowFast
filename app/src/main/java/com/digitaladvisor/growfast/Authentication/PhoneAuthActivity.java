package com.digitaladvisor.growfast.Authentication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.digitaladvisor.growfast.R;
import com.digitaladvisor.growfast.WelcomePagesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;



/*
 * Here a Log.d(....) is only used for debugging purpose
 *
 * */


public class PhoneAuthActivity extends AppCompatActivity {
    public final String LOGIN_STATS = "loginStats";
    public final String ISLOGIN = "islogin";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String verificationCodeBySystem;
    private boolean codeSentStatus;
    private final String TAG = getClass().getSimpleName();
    private boolean autoverifiedStatus;
    private boolean vfOnPressed;
    private String inputOTP;
    EditText mobileno;
    EditText otpno;
    TextView app_name_;
    TextInputLayout layoutmob, layoutOTP;
    Button btnsend, btnvalidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_auth);
        initializeLayoutElements();

        initializeCallback();
    }

    private void initializeCallback() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "onVerificationCompleted: Automatically Verified " + credential);
                String code = credential.getSmsCode();
                /* Check if OTP is received on the current device is not null
                 *  Store it into the CommonUserInfoDetails.inputOTP
                 *  Verify it with PhoneAuth Credentials
                 * */
                String f = FirebaseAuth.getInstance().getUid();
                Log.d(TAG, "onVerificationCompleted: FUID " + f);
                if (code != null) {
                    inputOTP = code;
                    Log.d(TAG, " RECEIVED OTP ON DEVICE: " + inputOTP);
                    verifyCode(code);
                }
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.d(TAG, "onVerificationFailed: called");
                if (codeSentStatus) {
                    autoverifiedStatus = false;
                }

                Log.d(TAG, "onVerificationFailed Called: LOG MESSAGE : \n" + e.getMessage());
            }

            @Override
            public void onCodeSent(@NonNull String s,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(s, token);
                Log.d(TAG, "onCodeSent: Called" + s);
                codeSentStatus = true;
                verificationCodeBySystem = s;
                autoverifiedStatus = false;
                startTimerResendCode();
            }
        };
    }

    private void initializeLayoutElements() {
        mobileno = findViewById(R.id.contactno);
        mobileno.setSelection(mobileno.getText().length());
        otpno = findViewById(R.id.otpnum);
        btnsend = findViewById(R.id.verifybutton);
        btnvalidate = findViewById(R.id.verifybutton);

        layoutmob = findViewById(R.id.ll_mobno);
        layoutOTP = findViewById(R.id.ll_otpno);
    }

    public void verifyUsingButton(View view) {
        String input = mobileno.getText().toString();
        if (checkMobnoValidityInputs())
            sendVerificationCodeToUser(input);
    }

    public void authenticateOTP(View view) {
        String input = otpno.getText().toString();
        if (checkOTPnoValidityInputs()) {
            try {
                verifyCode(input);
            } catch (Exception e) {
                Toast.makeText(this, "Error occured: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /*--------------------------------Phone Authentication Methods-----------------------------------*/
    public void sendVerificationCodeToUser(String phoneNo) {
/*        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNo,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                TaskExecutors.MAIN_THREAD,   // Activity (for callback binding)
                mCallbacks);// OnVerificationStateChangedCallbacks*/

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                        .setPhoneNumber(phoneNo)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }

    private void startTimerResendCode() {
        // Start Countdown timer here for 50 seconds

    }

    public void verifyCode(String codeByUser) {
        // Show a Verifying... dialog
        vfOnPressed = false;
        Log.d(TAG, "verifyCode: VERIFYING . . . ");
        Toast.makeText(this, "VERIFYING . . . ", Toast.LENGTH_SHORT).show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeBySystem, codeByUser);
        signInTheUserByCredentials(credential);

    }

    private void signInTheUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(PhoneAuthActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            autoverifiedStatus = true;
                            if (autoverifiedStatus) {
                                //Start the Welcome Fragments to the user
                                SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_STATS, Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putBoolean(ISLOGIN, true);
                                editor.commit();
                                startWelcomePages();

                            }
                            String f = FirebaseAuth.getInstance().getUid();
                            Log.d(TAG, "onVerificationCompleted: FUID " + f);
                            Log.d(TAG, "onComplete: DONE WITH OTP VERIFICATION ");
                            Toast.makeText(PhoneAuthActivity.this, "Verification Success", Toast.LENGTH_SHORT).show();
                            vfOnPressed = true;


                        } else {
                            Log.d(TAG, "onComplete: FAILED LOG MESSAGE : " + task.getException().getMessage());
                            autoverifiedStatus = false;

                            Toast.makeText(PhoneAuthActivity.this, "Wrong OTP enterred", Toast.LENGTH_SHORT).show();


                        }

                    }
                });
    }


    private void startWelcomePages() {
        Intent i = new Intent(this, WelcomePagesActivity.class);
        startActivity(i);
        finish();
    }

    public boolean checkOTPnoValidityInputs() {
        boolean otpStatus;
        if (TextUtils.isEmpty(otpno.getText().toString())) {
            layoutOTP.setErrorEnabled(true);
            layoutOTP.setError("Invalid OTP");
            otpStatus = false;
        } else {
            layoutOTP.setErrorEnabled(false);
            layoutOTP.setError(null);
            otpStatus = true;
        }
        if (otpStatus == true)
            return true;
        return false;
    }

    public boolean checkMobnoValidityInputs() {

        boolean contactStatus;

        if (TextUtils.isEmpty(mobileno.getText().toString())) {
            layoutmob.setErrorEnabled(true);
            layoutmob.setError("Invalid no.");
            contactStatus = false;
        } else {
            layoutmob.setErrorEnabled(false);
            layoutmob.setError(null);
            contactStatus = true;
        }

        if (contactStatus == true)
            return true;
        return false;


    }

}