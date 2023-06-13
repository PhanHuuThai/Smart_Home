package com.androidexam.smart_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.androidexam.smart_home.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DatabaseReference data;
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    String value = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //moveTaskToBack(true);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        data = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();

        data.child("dht").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String[] dht = snapshot.getValue().toString().split("\\s");
                binding.tvNhietdo.setText(dht[0] + "℃");
                binding.tvDoam.setText(dht[1] + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        data.child("DEN1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                value = snapshot.getValue().toString();
                if(value.charAt(0)=='1'){
                    binding.sw1.setChecked(true);
                    binding.img1.setImageResource(R.mipmap.ledon_foreground);
                }
                if(value.charAt(0)=='0'){
                    binding.sw1.setChecked(false);
                    binding.img1.setImageResource(R.mipmap.ledoff_foreground);
                }
                if(value.charAt(1)=='1'){
                    binding.sw2.setChecked(true);
                    binding.img2.setImageResource(R.mipmap.ledon_foreground);
                }
                if(value.charAt(1)=='0'){
                    binding.sw2.setChecked(false);
                    binding.img2.setImageResource(R.mipmap.ledoff_foreground);
                }
                if(value.charAt(2)=='1'){
                    binding.sw3.setChecked(true);
                    binding.img3.setImageResource(R.mipmap.ledon_foreground);
                }
                if(value.charAt(2)=='0'){
                    binding.sw3.setChecked(false);
                    binding.img3.setImageResource(R.mipmap.ledoff_foreground);
                }
                if(value.charAt(3)=='1'){
                    binding.sw4.setChecked(true);
                    binding.img4.setImageResource(R.mipmap.ledon_foreground);
                }
                if(value.charAt(3)=='0'){
                    binding.sw4.setChecked(false);
                    binding.img4.setImageResource(R.mipmap.ledoff_foreground);
                }
                if(value.charAt(5)=='1'){
                    //openNoti();
                    startService(new Intent(MainActivity.this, MyService.class));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    value = "1" + value.substring(1);
                    data.child("DEN1").setValue(value);
                    binding.img1.setImageResource(R.mipmap.ledon_foreground);
                } else {
                    value = "0" + value.substring(1);
                    binding.img1.setImageResource(R.mipmap.ledoff_foreground);
                    data.child("DEN1").setValue(value);
                }
            }
        });

        binding.sw2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    binding.img2.setImageResource(R.mipmap.ledon_foreground);
                    value = value.substring(0,1) + "1" + value.substring(2);
                    data.child("DEN1").setValue(value);
                } else {
                    binding.img2.setImageResource(R.mipmap.ledoff_foreground);
                    value = value.substring(0,1) + "0" + value.substring(2);
                    data.child("DEN1").setValue(value);
                }
            }
        });

        binding.sw3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    binding.img3.setImageResource(R.mipmap.ledon_foreground);
                    value = value.substring(0,2) + "1" + value.substring(3);
                    data.child("DEN1").setValue(value);
                } else {
                    binding.img3.setImageResource(R.mipmap.ledoff_foreground);
                    value = value.substring(0,2) + "0" + value.substring(3);
                    data.child("DEN1").setValue(value);
                }
            }
        });

        binding.sw4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    binding.img4.setImageResource(R.mipmap.ledon_foreground);
                    value = value.substring(0,3) + "1" + value.substring(4);
                    data.child("DEN1").setValue(value);
                } else {
                    binding.img4.setImageResource(R.mipmap.ledoff_foreground);
                    value = value.substring(0,3) + "0" + value.substring(4);
                    data.child("DEN1").setValue(value);
                }
            }
        });




        binding.butcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(MainActivity.this, alertDetail.class);
                startActivity(e);
            }
        });

        //finish();
    }


    private void openNoti(){
        Notification notification = new NotificationCompat.Builder(this, MyNotification.CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("CẢNH BÁO")
                .setContentText("Có Lửa Lớn Trong Nhà")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.iconfire))
                .build();

        NotificationManagerCompat.from(this).notify(new Random().nextInt(), notification);
    }

}