package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.projetandroid.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.util.Patterns;

public class signup extends AppCompatActivity {
    ActivitySignupBinding binding;
    String userName, Phone, Email, Password;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("app users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Button back =binding.backButton;
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nt=new Intent(signup.this,MainActivity.class);
                startActivity(nt);
            }
        });
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userName = binding.username.getText().toString();
                Phone = binding.phone.getText().toString();
                Email = binding.email.getText().toString();
                Password = binding.password.getText().toString();
                String Password2 = binding.confirmpass.getText().toString();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if (userName.length() < 6 || userName.length() > 25 || userName.contains(" ") || !userName.trim().matches("^[a-zA-Z0-9]+$")) {
                    Toast.makeText(signup.this, "the username should be 6 to 25 alpha numeric character", Toast.LENGTH_SHORT).show();
                }
                if (!Phone.matches("^\\+212[\\d]{9}$")) {
                    Toast.makeText(signup.this, "invalid phone format", Toast.LENGTH_SHORT).show();
                }
                if (!Email.matches(emailPattern)) {
                    Toast.makeText(signup.this, "invalid email format! enter a valid email", Toast.LENGTH_SHORT).show();
                }
                if (Password.length() < 8) {
                    Toast.makeText(signup.this, "a password is 8 character or more", Toast.LENGTH_SHORT).show();
                }
                if (!Password.equals(Password2)) {
                    Toast.makeText(signup.this, "passwords don't match", Toast.LENGTH_SHORT).show();
                }
                if (userName.length() >= 6 && userName.length() <= 25 && !userName.contains(" ") && userName.trim().matches("^[a-zA-Z0-9]+$") && Phone.matches("^\\+212[\\d]{9}$") && Email.matches(emailPattern) && Password.length() >= 8 && Password.equals(Password2)) {
                    Users user = new Users(userName, Email, Phone, Password);


                    reference.child(userName).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    binding.username.setText("");
                                    binding.email.setText("");
                                    binding.phone.setText("");
                                    binding.password.setText("");
                                    binding.confirmpass.setText("");

                                    Intent main = new Intent(signup.this, mainPage.class);
                                    main.putExtra("username",userName);
                                    startActivity(main);
                                }
                            });
                    Toast.makeText(signup.this, "registred succefuly", Toast.LENGTH_SHORT);


                }

            }
        });

    }
}

