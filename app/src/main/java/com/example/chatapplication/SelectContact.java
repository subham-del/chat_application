package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class SelectContact extends AppCompatActivity {

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);

        setSupportActionBar(findViewById(R.id.toolbar));
        getSupportActionBar().setTitle("Select contact");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RecyclerView recycleView = findViewById(R.id.recyclerView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));


        ArrayList<ContactModel> data= getContacts(this) ;

       RecyclerViewAdapter adapter = new RecyclerViewAdapter(this,data);
       recycleView.setAdapter(adapter);


    }

    private ArrayList<ContactModel> getContacts(SelectContact selectContact) {

        ArrayList<ContactModel> contactList = new ArrayList<>();
            ContentResolver cr = getContentResolver();

            Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            if (cursor != null) {
                HashSet<String> mobileNoSet = new HashSet<String>();
                try {
                    final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                    String name, number;
                    while (cursor.moveToNext()) {
                        name = cursor.getString(nameIndex);
                        number = cursor.getString(numberIndex);
                        number = number.replace(" ", "");
                        if (!mobileNoSet.contains(number) && number.toString().length()>=10) {
                            contactList.add(new ContactModel(name, number));
                            mobileNoSet.add(number);
                        }
                    }
                } finally {
                    cursor.close();
                }
            }
        return contactList;

    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        return super.onCreateOptionsMenu(menu);

    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId=item.getItemId();

        if(itemId==R.id.search)
        {
            Intent intent = new Intent(SelectContact.this,SearchActivity.class);
            startActivity(intent);
            finish();
        }
        else{
            super.onBackPressed();
        }


        return super.onOptionsItemSelected(item);
    }

}