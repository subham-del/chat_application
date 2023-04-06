package com.example.chatapplication;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;


public class MyAccount extends AppCompatActivity {

   private Uri profile_Uri;

    private final int REQUEST_CODE_SELECT_IMAGE=2;
    private final int STORAGE_REQ_CODE=100;
    private ImageView img;
    private ImageButton camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        TextInputEditText name = findViewById(R.id.Name);
        TextInputEditText number = findViewById(R.id.number);

        SharedPreferences pref = getSharedPreferences("User_Phone_no",MODE_PRIVATE);
        String User_no = pref.getString("number","false");
        if(!User_no.equals("false")){
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(User_no).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task) {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            DataSnapshot dataSnapshot = task.getResult();
                            name.setText(String.valueOf(dataSnapshot.child("name").getValue()));
                            number.setText(User_no);
                        }
                    }
                }
            });
        }

        camera = findViewById(R.id.camera);
        img = findViewById(R.id.profilePic);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE){
            if(data!=null){
                Uri selectImageUri = data.getData();
                img.setImageURI(selectImageUri);

            }
        }
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();


        if(itemId==android.R.id.home){
            super.onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

}