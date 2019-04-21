package com.akarburcin.firebasegyk;

import android.annotation.SuppressLint;
import android.app.Person;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbRef;

    Button veriKaydet;
    EditText editText;
    ListView liste;

    ArrayList<String>notlar=new ArrayList<>();
    ArrayList<String> keysList = new ArrayList<>();
    ArrayAdapter<String>arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance();
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

    /*public Map<String,Object>
    Veri(String not)
    {
        HashMap<String,Object> result=new HashMap<>();
        result.put("not_adı",not);
        return result;
    }*/


}
