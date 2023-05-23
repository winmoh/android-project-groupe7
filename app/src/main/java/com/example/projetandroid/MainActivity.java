package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.renderscript.ScriptGroup;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetandroid.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    DatabaseReference ref=FirebaseDatabase.getInstance().getReference("app users");


    ActivityMainBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bind.getRoot());

        TextView text=bind.sig;
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent inten=new Intent(MainActivity.this,signup.class);
                startActivity(inten);
            }
        });

        Button login=(Button)bind.connect;

       login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=bind.champUser.getText().toString();
                String password=bind.champPassword.getText().toString();

                if(username.isEmpty() || password.isEmpty()){
                    Toast.makeText(MainActivity.this,"enter your username and password",Toast.LENGTH_LONG).show();
                }
                else{
                    ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild(username)){
                                String code=snapshot.child(username).child("password").getValue(String.class);

                                if(code.equals(password)){

                                    Intent verMain= new Intent(MainActivity.this,mainPage.class);
                                    verMain.putExtra("username",username);
                                    startActivity(verMain);

                                }
                                else{
                                    Toast.makeText(MainActivity.this,"incorrect password",Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                Toast.makeText(MainActivity.this,"invalid username",Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this,"error error error",Toast.LENGTH_LONG).show();


                        }
                    });
                }

            }
        });
    }
}