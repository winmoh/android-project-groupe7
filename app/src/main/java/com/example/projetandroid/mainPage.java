package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class mainPage extends AppCompatActivity implements View.OnClickListener {

    private View createEventCard,attendEventCard,eventsToAttendCard,
            manageYourEventsCard,EventsRecommendationCard,profileCard,annualEvents,discoverClubs
            ;

    private TextView logOut, username;

    Intent intent;

    private AttendEventDialog attendEventDialog;

    @Override
    protected void onStart() {
        super.onStart();

        username = findViewById(R.id.username);
        username.setText(this.getIntent().getStringExtra("username"));
    }

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
    }



}