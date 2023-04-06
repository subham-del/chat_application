package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.widget.ContentLoadingProgressBar;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {


    private Retrofit retrofit;
    private int MY_STORAGE_REQUEST_CODE = 100;
    private RetrofitAPI retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        ProgressBar progressBar = findViewById(R.id.progressbar);
        TextInputEditText txt=findViewById(R.id.textInputEditText);
        TextInputLayout txtLayout=findViewById(R.id.textInputLayout);
        Button button = findViewById(R.id.Button);
        Toolbar tool=findViewById(R.id.ToolBar);
        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Sign up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        Typeface typeface = Typeface.createFromAsset(getAssets(),"Roboto-Light.ttf");
//        button.setTypeface(typeface);







        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(txt.getText().toString().length()>0){
                    txtLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!txt.getText().toString().trim().isEmpty()){
                    if(txt.getText().toString().length()==10){
                        progressBar.setVisibility(View.VISIBLE);
                        retrofit = new Retrofit.Builder().baseUrl("http://localhost:80").build();
                        retrofitInterface = retrofit.create(RetrofitAPI.class);
                        Call<Void> call = retrofitInterface.PostData(txt.getText().toString());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                Toast.makeText(getApplicationContext(),"Data added successfully to api", Toast.LENGTH_LONG).show();

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {


                            }
                        });                        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91"+txt.getText().toString(),60, TimeUnit.SECONDS,LoginActivity.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            public void onCodeSent(String s,PhoneAuthProvider.ForceResendingToken forceResendingToken){
                             Intent nxtPage = new Intent(LoginActivity.this,OtpActivity.class);
                             nxtPage.putExtra("number",txt.getText().toString());
                             nxtPage.putExtra("OTP",s);
                             startActivity(nxtPage);
                             progressBar.setVisibility(View.INVISIBLE);
//
                            }
                        });

                    }
                    else{
                       txtLayout.setError("please enter a ten digit number");
                    }
                }
                else{
                    txtLayout.setError("please enter the phone number");
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
        Boolean check = pref.getBoolean("flag",false);
        if(check){
            Intent intent = new Intent(this,ChatActivity.class);
            startActivity(intent);
        }
        else{
        }

    }
}