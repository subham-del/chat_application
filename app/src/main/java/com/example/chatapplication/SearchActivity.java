package com.example.chatapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {


   private RecyclerViewAdapter adapter;
   private ArrayList<ContactModel> data;
    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ImageButton btn = findViewById(R.id.back_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.super.onBackPressed();
            }
        });

        RecyclerView recycleView = findViewById(R.id.recyclerView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));

        data= getContacts(this) ;

      adapter = new RecyclerViewAdapter(this,data);
        recycleView.setAdapter(adapter);
        TextInputEditText Txt= findViewById(R.id.searchText);

        findViewById(R.id.close).setVisibility(View.INVISIBLE);

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Txt.setText("");
            }
        });

        Txt.addTextChangedListener(new TextWatcher() {
            @Override

            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!Txt.getText().toString().isEmpty()){
                    findViewById(R.id.close).setVisibility(View.VISIBLE);
                }
                else{
                    findViewById(R.id.close).setVisibility(View.INVISIBLE);
                }


            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<ContactModel> filterdNames = new ArrayList<>();

        //looping through existing elements
        Iterator itr=data.iterator();
        try{
            int i=0;
            while(itr.hasNext()){

                if(data.get(i).name.toLowerCase().contains(text.toLowerCase()) ||data.get(i).mobileNumber.toLowerCase().contains(text.toLowerCase() )){
                    filterdNames.add(data.get(i));
                }
                i++;
            }
        }catch(Exception e){

        }


        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }
    private ArrayList<ContactModel> getContacts(SearchActivity searchactivity) {

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


}