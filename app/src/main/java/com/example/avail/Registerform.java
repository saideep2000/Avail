package com.example.avail;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.NoSuchAlgorithmException;

public class Registerform extends AppCompatActivity {

    EditText usernamein;
    EditText mailin;
    EditText numberin;
    EditText passwordin;
    EditText conpasswordin;


    Button register;
    DatabaseReference myRef;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        usernamein = (EditText) findViewById(R.id.editText);
        passwordin = (EditText) findViewById(R.id.editText2);
        conpasswordin = (EditText) findViewById(R.id.editText3);

        register = (Button)findViewById(R.id.button);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

//        myRef.setValue("Hello, World!");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String u = usernamein.getText().toString().trim();
                String p = passwordin.getText().toString().trim();
                String c = conpasswordin.getText().toString().trim();

                if(p.equals(c)) {
                    try {
//                        myRef.child("users").child(u).setValue(u);
                        myRef.child("users").child(u).child("password").setValue(p);
                        String id=myRef.push().getKey();

//                        myRef.child("userhobbies").child(u).child(id).setValue("hitch");

                        Intent i=new Intent(getApplicationContext(),Login.class);
                        startActivity(i);
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(),"fgf", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    passwordin.setText("");
                    conpasswordin.setText("");
                    Toast.makeText(getApplicationContext(),"Not matched try again..!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}