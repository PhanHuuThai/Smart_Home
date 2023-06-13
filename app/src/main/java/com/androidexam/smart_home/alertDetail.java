package com.androidexam.smart_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class alertDetail extends AppCompatActivity {

    private RecyclerView rv_img;

    private ArrayList<Integer> contactList;
    private ImageView img_back;
    private customAdapter ctAdapter;
    DatabaseReference data;
    String[] values;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        data = FirebaseDatabase.getInstance().getReference();
        img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(alertDetail.this, MainActivity.class);
                startActivity(e);
            }
        });

        data.child("Pos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                values = snapshot.getValue().toString().split("\\s");
                Log.d("aaa", Arrays.toString(values));
                rv_img = findViewById(R.id.rv_img);

                contactList = new ArrayList<Integer>();
                for(int i = 0; i< values.length; i++){
                    contactList.add(Integer.parseInt(values[i]));
                }
                Log.d("aaa5", contactList.toString());
                rv_img.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                ctAdapter = new customAdapter(contactList);
                rv_img.setAdapter(ctAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


}