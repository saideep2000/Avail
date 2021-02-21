package com.example.avail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity{

    EditText usernamein;
    EditText passwordin;

    Button login;
    TextView register;
    DatabaseReference myRef;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}


        setContentView(R.layout.activity_login);

        SharedPreferences sh = getSharedPreferences("Myprefs", MODE_PRIVATE);
        SharedPreferences ls = getSharedPreferences("dataremeber", MODE_PRIVATE);
        SharedPreferences ps = getSharedPreferences("passcode", MODE_PRIVATE);

        boolean log = sh.getBoolean("login", false);
        String blog = ls.getString("user", "");
        String mlog = ps.getString("pass", "");

        if (log & blog!="") {
            Intent i = new Intent(getApplicationContext(), Afterlogin.class);
            startActivity(i);
        } else {
            usernamein = (EditText) findViewById(R.id.uname);
            passwordin = (EditText) findViewById(R.id.pname);

            login = (Button) findViewById(R.id.login);
            register = (TextView) findViewById(R.id.register);


            // Write a message to the database
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            myRef = database.getReference();

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String u = usernamein.getText().toString().trim();
                    final String p = passwordin.getText().toString().trim();
                    // Read from the database
                    try {
                        myRef = myRef.child("users").child(u).child("password");

                        myRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String value = dataSnapshot.getValue(String.class);
                                Toast.makeText(getApplicationContext(), "Hi..."+u, Toast.LENGTH_LONG).show();
//                                HashFileTest ds = new HashFileTest();
//                                String enc = ds.verifyChecksum(value);

                                if (value.equals(p)) {

                                    SharedPreferences pref = getSharedPreferences("Myprefs", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean("login", true);

                                    SharedPreferences data = getSharedPreferences("dataremeber", MODE_PRIVATE);
                                    editor = data.edit();
                                    editor.putString("user", u);


                                    editor.commit();

                                    Intent i = new Intent(getApplicationContext(), Afterlogin.class);
                                    startActivity(i);
                                } else {
                                    passwordin.setText("");
                                    //Toast.makeText(getApplicationContext(), "hi..."+enc, Toast.LENGTH_LONG).show();
                                    Toast.makeText(getApplicationContext(), "Enter correct password", Toast.LENGTH_LONG).show();
                                }
//                        Log.d(TAG, "Value is: " + value);
                            }

                            @Override
                            public void onCancelled(DatabaseError error) {
                                // Failed to read value


                                Toast.makeText(getApplicationContext(), "Failed try again...sorry", Toast.LENGTH_LONG).show();
                            }
                        });
                    } catch (Exception e) {
                        usernamein.setText("");
                        passwordin.setText("");
                        Toast.makeText(getApplicationContext(), "Wrong username", Toast.LENGTH_LONG).show();
                    }
                }
            });

            register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), Registerform.class);
                    startActivity(i);
                }
            });

        }

    }
}
