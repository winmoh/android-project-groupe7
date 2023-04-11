package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class mainPage extends AppCompatActivity implements View.OnClickListener {

    private View createEventCard,attendEventCard,eventsToAttendCard,
            manageYourEventsCard,EventsRecommendationCard,profileCard;
    private TextView logOut;

    private AttendEventDialog attendEventDialog;

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.logOut :
                break;
            case R.id.createEventCard :
                Intent intent = new Intent(this,CreateEventActivity.class);
                startActivity(intent);
                break;
            case R.id.attendEventCard :
                attendEventDialog = new AttendEventDialog();
                attendEventDialog.show(getSupportFragmentManager(),"attend event dialog");

                break;
            case R.id.eventsToAttendCard :
                break;
            case R.id.manageYourEventsCard :
                break;
            case R.id.EventsRecommendationCard :
                break;
            case R.id.profileCard :
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_page);



        createEventCard=findViewById(R.id.createEventCard);
        createEventCard.setOnClickListener(this);

        attendEventCard=findViewById(R.id.attendEventCard);
        attendEventCard.setOnClickListener(this);

        eventsToAttendCard=findViewById(R.id.eventsToAttendCard);
        eventsToAttendCard.setOnClickListener(this);

        manageYourEventsCard=findViewById(R.id.manageYourEventsCard);
        manageYourEventsCard.setOnClickListener(this);

        EventsRecommendationCard=findViewById(R.id.EventsRecommendationCard);
        EventsRecommendationCard.setOnClickListener(this);

        profileCard=findViewById(R.id.profileCard);
        profileCard.setOnClickListener(this);
    }



}