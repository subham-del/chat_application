package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class OtpActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_otp);
        EditText Text1 = findViewById(R.id.Txt1);
        EditText Text2 = findViewById(R.id.Txt2);
        EditText Text3 = findViewById(R.id.Txt3);
        EditText Text4 = findViewById(R.id.Txt4);
        EditText Text5 = findViewById(R.id.Txt5);
        EditText Text6 = findViewById(R.id.Txt6);
        TextView timer = findViewById(R.id.Timer);
        Toolbar tool = findViewById(R.id.ToolBar);
        TextView txt=findViewById(R.id.setTxt);
        TextView errTxt=findViewById(R.id.errorMsg);
        Button button = findViewById(R.id.Button);
        ProgressBar bar = findViewById(R.id.progressbar);
        String backendOtp=getIntent().getStringExtra("OTP");

        txt.setText(String.format("You on +91- %s",getIntent().getStringExtra("number")));

        Text1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Text1.getText().toString().length()==1) {
                    Text2.requestFocus();
                    errTxt.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Text2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Text2.getText().toString().length()==1){
                    Text3.requestFocus();
                    errTxt.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Text3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Text3.getText().toString().length()==1) {
                    Text4.requestFocus();
                    errTxt.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Text4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Text4.getText().toString().length()==1) {
                    Text5.requestFocus();
                    errTxt.setText("");
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Text5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Text5.getText().toString().length()==1) {
                    Text6.requestFocus();
                    errTxt.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Text6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(Text6.getText().toString().length()==1) {
                    errTxt.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        Text6.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL && Text6.getText().toString().isEmpty()){
                    Text5.requestFocus();
                }
                return false;
            }
        });
        Text5.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL && Text5.getText().toString().isEmpty()){
                    Text4.requestFocus();
                }
                return false;
            }
        });
        Text4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL && Text4.getText().toString().isEmpty()){
                    Text3.requestFocus();
                }
                return false;
            }
        });
        Text3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL && Text3.getText().toString().isEmpty()){
                    Text2.requestFocus();
                }
                return false;
            }
        });
        Text2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i == KeyEvent.KEYCODE_DEL && Text2.getText().toString().isEmpty()){
                    Text1.requestFocus();
                }
                return false;
            }
        });

        setSupportActionBar(tool);
        getSupportActionBar().setTitle("Verification");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long hour = (millisUntilFinished / 3600000) % 24;
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                timer.setText(f.format(min) + ":" + f.format(sec));
            }
            // When the task is over it will print 00:00 there
            public void onFinish() {
                timer.setText("00:00");
            }
        }.start();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(timer.getText().toString().equals("00:00")){
                    bar.setVisibility(View.INVISIBLE);
                    Toast.makeText(OtpActivity.this, "Timeout please request for another OTP", Toast.LENGTH_LONG).show();

                }
                else if(!Text1.getText().toString().isEmpty() && !Text2.getText().toString().isEmpty()  && !Text3.getText().toString().isEmpty() && !Text4.getText().toString().isEmpty() && !Text5.getText().toString().isEmpty() && !Text6.getText().toString().isEmpty()){
                    bar.setVisibility(View.VISIBLE);
                    String UserOtp = Text1.getText().toString()+Text2.getText().toString()+Text3.getText().toString()+Text4.getText().toString()+Text5.getText().toString()+Text6.getText().toString();
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(backendOtp,UserOtp);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                SharedPreferences pref = getSharedPreferences("login",MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();
                                editor.putBoolean("flag",true);
                                editor.apply();
                                Intent Next_page = new Intent(OtpActivity.this,GetInfoActivity.class);
                                Next_page.putExtra("Phone",getIntent().getStringExtra("number"));
                                Next_page.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(Next_page);
                                bar.setVisibility(View.INVISIBLE);

                            }
                            else{
                                bar.setVisibility(View.INVISIBLE);
                                errTxt.setText("invalid otp");
                                errTxt.setTextColor(Color.rgb(183,28,28));
                            }
                        }
                    });
                }
                else{
                    errTxt.setText("please enter the otp");
                    errTxt.setTextColor(Color.rgb(183,28,28));
                }
            }
        });



    }
}
