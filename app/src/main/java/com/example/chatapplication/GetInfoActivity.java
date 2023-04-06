package com.example.chatapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;

public class GetInfoActivity extends AppCompatActivity {
    private final int REQUEST_CODE_SELECT_IMAGE=2;
    private CircularImageView img;
    private Uri selectImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_info);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile info");
       img = findViewById(R.id.profilePic);
        TextInputEditText text = findViewById(R.id.UserName);
        TextInputLayout lay = findViewById(R.id.name);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
            }
        });

        text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lay.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        Button btn = findViewById(R.id.SaveBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.getText().toString().isEmpty()){
                    lay.setError("Please enter your name");
                }
                else{
                    String user_phone_no = "+91"+getIntent().getStringExtra("Phone");
                    DatabaseReference data =  FirebaseDatabase.getInstance().getReference("Users");
                    data.child(user_phone_no).child("name").setValue(text.getText().toString());
                    SharedPreferences pref = getSharedPreferences("User_Phone_no",MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("number",user_phone_no);
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE){
            if(data!=null){
                selectImageUri  = data.getData();
                img.setImageURI(selectImageUri);

            }
        }
    }
}