package com.akarburcin.firebasegyk;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MainFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference dbRef;

    Button veriKaydet;
    EditText editText;
    ListView liste;

    ArrayList<String> notlar=new ArrayList<>();
    ArrayList<String> keysList = new ArrayList<>();
    ArrayAdapter<String>arrayAdapter;

/*
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);


    }*/

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;

       database= FirebaseDatabase.getInstance();
       dbRef=database.getReference("NotListesi");

       liste=(ListView)findViewById(R.id.listview);
       arrayAdapter=new ArrayAdapter<String>
               (this,android.R.layout.simple_list_item_1,notlar);
       liste.setAdapter(arrayAdapter);

       editText=(EditText)findViewById(R.id.editText);
       veriKaydet=(Button)findViewById(R.id.BtnEkle);

       dbRef.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               String not=dataSnapshot.getValue().toString();
               notlar.add(not);
               arrayAdapter.notifyDataSetChanged();
               editText.setText("");
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
           }

           @Override
           public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

               /* String silinenNot=dataSnapshot.getValue().toString();
                notlar.remove(notlar.indexOf(silinenNot));
                arrayAdapter.notifyDataSetChanged();
                editText.setText("");*/

               String string = dataSnapshot.getValue(String.class);
               notlar.remove(string);
               keysList.remove(dataSnapshot.getKey());
               arrayAdapter.notifyDataSetChanged();
               editText.setText("");

           }

           @Override
           public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
           }
           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {
           }
       });

       veriKaydet.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if(editText.getText().toString().trim().length()!=0){

                   dbRef.push().setValue(editText.getText().toString());
               }
           }
       });

   }


}
