package com.example.chatapplication;

import android.net.Uri;

public class ContactModel {

    public String id;
    public String name;
    public String mobileNumber;
    ContactModel(String name,String number){
        this.name=name;
        this.mobileNumber=number;
    }


}
