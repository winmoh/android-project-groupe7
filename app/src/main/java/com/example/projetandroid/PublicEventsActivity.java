package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PublicEventsActivity extends AppCompatActivity {

    String username;

    ListView publicEvents;

    FirebaseDatabase database;

    DatabaseReference eventsRef,eventAttendeeRef;


    ArrayList<EventItem> public_events = new ArrayList<>();

    ArrayAdapter<EventItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_events);

        username = this.getIntent().getStringExtra("username");

        publicEvents = findViewById(R.id.publicEvents);

        database = FirebaseDatabase.getInstance();

        eventsRef = database.getReference("events");
        eventAttendeeRef = database.getReference("eventAttendee");


        adapter = new ArrayAdapter<EventItem>(this, android.R.layout.simple_list_item_1, public_events){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.public_event_item, parent, false);
                }
                EventItem currentEvent = public_events.get(position);

                TextView clubName_p = convertView.findViewById(R.id.clubName_p);
                clubName_p.setText(currentEvent.getClub());


                TextView eventItemTitle_p = convertView.findViewById(R.id.eventItemTitle_p);
                eventItemTitle_p.setText(currentEvent.getTitle());


                TextView eventItemLocation_p = convertView.findViewById(R.id.eventItemLocation_p);
                eventItemLocation_p.setText(currentEvent.getLocation());


                TextView eventItemDate_p = convertView.findViewById(R.id.eventItemDate_p);
                eventItemDate_p.setText(currentEvent.getDate());


                TextView eventItemTime_p = convertView.findViewById(R.id.eventItemTime_p);
                eventItemTime_p.setText(currentEvent.getTime());

                convertView.findViewById(R.id.eventItemAttend_p).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        eventAttendeeRef.child(currentEvent.getId()).child(username).setValue(true);
                        public_events.remove(currentEvent);
                        notifyDataSetChanged();
                    }
                });


                return convertView;
            }
        };

        eventsRef.orderByChild("is_private").equalTo("false").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){

                    eventAttendeeRef.child(data.getKey()).child(username).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()){
                                public_events.add((EventItem) data.getValue(EventItem.class));

                                Log.i("event",data.getKey());
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        publicEvents.setAdapter(adapter);
    }
}