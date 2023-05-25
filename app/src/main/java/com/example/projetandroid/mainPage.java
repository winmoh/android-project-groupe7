package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.GridLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class mainPage extends AppCompatActivity implements View.OnClickListener {


    private View createEventCard,attendEventCard,eventsToAttendCard,
            manageYourEventsCard,EventsRecommendationCard,profileCard,annualEvents,discoverClubs
            ;

    private TextView logOut, username;

    Intent intent;

    private AttendEventDialog attendEventDialog;

    FirebaseDatabase db;
    DatabaseReference adminsRef;



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logOut :
                intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.createEventCard :
                intent = new Intent(this,CreateEventActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
                break;
            case R.id.attendEventCard :
                attendEventDialog = new AttendEventDialog(username.getText().toString());
                attendEventDialog.show(getSupportFragmentManager(),"attend event dialog");
                break;
            case R.id.eventsToAttendCard :
                intent = new Intent(this, EventsListActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
                break;
            case R.id.manageYourEventsCard :
                intent = new Intent(this, ManageEventsActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
                break;
            case R.id.EventsRecommendationCard :
                intent = new Intent(this, PublicEventsActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
                break;
            case R.id.profileCard :
                intent = new Intent(this,ProfileActivity.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
                break;
            case R.id.annualEvents:
                intent=new Intent(this,localEvents.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
                break;
            case R.id.discoverClubs:
                intent=new Intent(this,clubs.class);
                intent.putExtra("username",username.getText().toString());
                startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);

        logOut = findViewById(R.id.logOut);
        logOut.setOnClickListener(this);


        createEventCard = findViewById(R.id.createEventCard);
        createEventCard.setOnClickListener(this);

        attendEventCard = findViewById(R.id.attendEventCard);
        attendEventCard.setOnClickListener(this);

        eventsToAttendCard = findViewById(R.id.eventsToAttendCard);
        eventsToAttendCard.setOnClickListener(this);

        manageYourEventsCard = findViewById(R.id.manageYourEventsCard);
        manageYourEventsCard.setOnClickListener(this);

        EventsRecommendationCard = findViewById(R.id.EventsRecommendationCard);
        EventsRecommendationCard.setOnClickListener(this);

        profileCard = findViewById(R.id.profileCard);
        profileCard.setOnClickListener(this);

        annualEvents = findViewById(R.id.annualEvents);
        annualEvents.setOnClickListener(this);

        discoverClubs=findViewById(R.id.discoverClubs);
        discoverClubs.setOnClickListener(this);

        username = findViewById(R.id.username);
        username.setText(this.getIntent().getStringExtra("username"));
        db = FirebaseDatabase.getInstance();
        adminsRef = db.getReference("admins");
        adminsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.hasChild(username.getText().toString())){
                    ((GridLayout)findViewById(R.id.gridLayout)).removeViewAt(0);
                    ((GridLayout)findViewById(R.id.gridLayout)).removeViewAt(2);

                    GridLayout.LayoutParams params = (GridLayout.LayoutParams) findViewById(R.id.eventsToAttendCard).getLayoutParams();
                    params.rowSpec = GridLayout.spec(0, GridLayout.FILL, 1f);
                    params.columnSpec = GridLayout.spec(0, GridLayout.FILL, 1f);
                    ((CardView)findViewById(R.id.eventsToAttendCard)).setLayoutParams(params);

                    params = (GridLayout.LayoutParams) findViewById(R.id.profileCard).getLayoutParams();
                    params.rowSpec = GridLayout.spec(1, GridLayout.FILL, 1f);
                    params.columnSpec = GridLayout.spec(1, GridLayout.FILL, 1f);
                    ((CardView)findViewById(R.id.profileCard)).setLayoutParams(params);

                    params = (GridLayout.LayoutParams) findViewById(R.id.EventsRecommendationCard).getLayoutParams();
                    params.rowSpec = GridLayout.spec(1, GridLayout.FILL, 1f);
                    params.columnSpec = GridLayout.spec(0, GridLayout.FILL, 1f);
                    ((CardView)findViewById(R.id.EventsRecommendationCard)).setLayoutParams(params);

                    params = (GridLayout.LayoutParams) findViewById(R.id.discoverClubs).getLayoutParams();
                    params.rowSpec = GridLayout.spec(2, GridLayout.FILL, 1f);
                    params.columnSpec = GridLayout.spec(1, GridLayout.FILL, 1f);
                    ((CardView)findViewById(R.id.discoverClubs)).setLayoutParams(params);

                    params = (GridLayout.LayoutParams) findViewById(R.id.annualEvents).getLayoutParams();
                    params.rowSpec = GridLayout.spec(2, GridLayout.FILL, 1f);
                    params.columnSpec = GridLayout.spec(0, GridLayout.FILL, 1f);
                    ((CardView)findViewById(R.id.annualEvents)).setLayoutParams(params);

                    ((GridLayout)findViewById(R.id.gridLayout)).setRowCount(3);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}