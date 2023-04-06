package com.example.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    Context context;
    ArrayList<ContactModel> arrContacts;
    RecyclerViewAdapter(Context context,ArrayList<ContactModel> arrContacts) {
        this.context = context;
        this.arrContacts = arrContacts;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String mobile_number=arrContacts.get(position).mobileNumber;
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(mobile_number).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DataSnapshot> task)throws NullPointerException {
                    if(task.isSuccessful()){
                        if(task.getResult().exists()){
                            holder.text.setText(arrContacts.get(position).name);
                            holder.inviteTxt.setText("");
                        }
                        else{
                            holder.text.setText(arrContacts.get(position).name);
                            holder.inviteTxt.setText("invite");
                        }
                    }
                }
            });


    }

    @Override
    public int getItemCount() {
        return arrContacts.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView text;
        TextView inviteTxt;
        ImageView img;
        public ViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            text = itemView.findViewById(R.id.text);
            img = itemView.findViewById(R.id.contacts);
            inviteTxt = itemView.findViewById(R.id.inviteText);
            text.setOnClickListener(this);
            img.setOnClickListener(this);

        }

        public void onClick(View view) {
            int position = this.getAdapterPosition();
            ContactModel data = arrContacts.get(position);
            String name = data.name;

            Intent intent = new Intent(context, ChatScreenActivity.class);
            intent.putExtra("name", name);
            context.startActivity(intent);
            ((Activity)context).finish();


        }
    }
    public void filterList(ArrayList<ContactModel> filterdNames) {
        this.arrContacts = filterdNames;
        notifyDataSetChanged();
    }
}
