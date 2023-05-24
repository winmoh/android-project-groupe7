package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClubsInfo extends AppCompatActivity {

    ImageView clubInfoImage;

    TextView clubName,clubPresident,clubCreationDate,clubDescription;

    Button closeClubInfo;
    String username,club;

    FirebaseDatabase db;

    DatabaseReference clubRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs_info);

        username = this.getIntent().getStringExtra("username");
        club = this.getIntent().getStringExtra("club");

        db = FirebaseDatabase.getInstance();
        clubRef = db.getReference("clubs").child(club);

        clubInfoImage = findViewById(R.id.clubInfoImage);
        setImage();

        clubName = findViewById(R.id.clubName);
        clubPresident = findViewById(R.id.clubPresident);
        clubCreationDate = findViewById(R.id.clubCreationDate);
        clubDescription = findViewById(R.id.clubDescription);

        closeClubInfo = findViewById(R.id.closeClubInfo);
        closeClubInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        clubRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clubCreationDate.setText(snapshot.child("creation_date").getValue().toString());
                clubPresident.setText(snapshot.child("president").getValue().toString());
                clubDescription.setText(snapshot.child("description").getValue().toString());
                clubName.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void setImage(){
        switch(club){
            case "quran":
                clubInfoImage.setImageResource(R.drawable.quran);
                break;
            case "cindh" :
                clubInfoImage.setImageResource(R.drawable.cindh);
                break;
            case "insec" :
                clubInfoImage.setImageResource(R.drawable.insec);
                break;
            case "it" :
                clubInfoImage.setImageResource(R.drawable.it);
                break;
            case "ieee" :
                clubInfoImage.setImageResource(R.drawable.ieee);
                break;
            case "fintech" :
                clubInfoImage.setImageResource(R.drawable.fintech);
                break;
            case "enactus" :
                clubInfoImage.setImageResource(R.drawable.enactus1);
                break;
            case "robotic" :
                clubInfoImage.setImageResource(R.drawable.robotic);
                break;
            case "bridge" :
                clubInfoImage.setImageResource(R.drawable.bridge);
                break;
            case "ia" :
                clubInfoImage.setImageResource(R.drawable.ia);
                break;
            case "cje" :
                clubInfoImage.setImageResource(R.drawable.cje);
                break;
            case "olympiades" :
                clubInfoImage.setImageResource(R.drawable.olympiades);
                break;
            case "sportif" :
                clubInfoImage.setImageResource(R.drawable.sportif);
                break;
            case "ultras" :
                clubInfoImage.setImageResource(R.drawable.ultras);
                break;
            case "houseofart" :
                clubInfoImage.setImageResource(R.drawable.houseofart);
                break;
            case "tizi" :
                clubInfoImage.setImageResource(R.drawable.tizi);
                break;
            case "hultprize" :
                clubInfoImage.setImageResource(R.drawable.hultprize);
                break;
            case "ensiastv" :
                clubInfoImage.setImageResource(R.drawable.ensiastv);
                break;
            case "forum" :
                clubInfoImage.setImageResource(R.drawable.forum);
                break;
            case "tedx" :
                clubInfoImage.setImageResource(R.drawable.tedx);
                break;
            case "adei" :
                clubInfoImage.setImageResource(R.drawable.adei);
                break;
        }
    }
}