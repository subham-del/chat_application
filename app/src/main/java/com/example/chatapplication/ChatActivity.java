package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;
import com.google.android.material.tabs.TabLayout;

public class ChatActivity extends AppCompatActivity {
    private final int MY_CONTACT_REQUEST_CODE = 10;
    private final int  MY_CAMERA_REQUEST_CODE = 100;
    private final int READ_EXTERNAL_REQ_CODE=3;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ViewPager viewPager=findViewById(R.id.VIewPager);
        TabLayout tabs = findViewById(R.id.tab);


        ViewPagerMessengerAdapter adapter = new ViewPagerMessengerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabs.setupWithViewPager(viewPager);



        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("ChatLive");
        toolbar.setTitleTextColor(Color.WHITE);




    }
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.opt_menu,menu);
//        androidx.appcompat.widget.SearchView SearchView = (androidx.appcompat.widget.SearchView)menu.findItem(R.id.app_bar_search).getActionView();
//        SearchView.setQueryHint("Search");



//        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
//        searchView.setHint("Search");
//        searchView.setBackgroundColor(Color.BLUE);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();

        if(itemId==R.id.app_bar_camera)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},MY_CAMERA_REQUEST_CODE);
            }
            else{
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);

            }



        }
        else if(itemId==R.id.app_bar_search){
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_DENIED){
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},MY_CONTACT_REQUEST_CODE);
            }else{
                Intent intent = new Intent(ChatActivity.this,SearchActivity.class);
                startActivity(intent);
            }


        } else if (itemId == R.id.app_bar_MyAccount) {

                Intent intent = new Intent(ChatActivity.this, MyAccount.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        }
        else if(requestCode == MY_CONTACT_REQUEST_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, SearchActivity.class);
                startActivity(intent);
            }
        }

    }

}
