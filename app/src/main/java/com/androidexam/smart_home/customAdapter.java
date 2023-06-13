package com.androidexam.smart_home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Arrays;

public class customAdapter extends RecyclerView.Adapter<customAdapter.ViewHolder> {
    FirebaseStorage firebaseStorage;
    //FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    DatabaseReference data;
    private ArrayList<Integer> ContactList ;
    private ArrayList<Integer> newArray = new ArrayList<>();
    String[] values;

    public customAdapter(ArrayList<Integer> contactList) {
        this.ContactList = contactList;
    }


    @NonNull
    @Override
    public customAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_detail, parent, false);

        return new ViewHolder(view);

    }

    public void filterList(ArrayList<Integer> filterlist) {
        ContactList = filterlist;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(@NonNull customAdapter.ViewHolder holder, int position)
    {
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        data = FirebaseDatabase.getInstance().getReference();
        String x=Integer.toString(ContactList.get(position));
        int k = position;
        Log.d("positon", Integer.toString(k));
        StorageReference imageRef = storageReference.child("pictures/alert_" + Integer.toString(ContactList.get(position)));


        final long ONE_MEGABYTE = 1024 * 1024;
        imageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                holder.img.setImageBitmap(bitmap);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        holder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference desertRef = storageReference.child("pictures/alert_"+ x);

                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ContactList.remove(k);
                        String s = "";
                        for(int i = 0; i < ContactList.size(); i++){
                            s += Integer.toString(ContactList.get(i)) + " ";
                        }
                        data.child("Pos").setValue(s);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Uh-oh, an error occurred!
                    }
                });
            }
        });



//        holder.btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StorageReference desertRef = storageReference.child("pictures/alert_"+ x +".jpg");
//
//                desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        ContactList.remove(k);
//                        String s = "";
//                        for(int i = 0; i < ContactList.size(); i++){
//                            s += Integer.toString(ContactList.get(i)) + " ";
//                        }
//                        data.child("Pos").setValue(s);
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        // Uh-oh, an error occurred!
//                    }
//                });
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return ContactList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public ImageView img_delete;

        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.img);
            img_delete = (ImageView) view.findViewById(R.id.img_delete);
        }


    }

}

