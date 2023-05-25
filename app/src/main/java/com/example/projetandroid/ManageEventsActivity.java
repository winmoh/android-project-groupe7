package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import java.util.List;

public class ManageEventsActivity extends AppCompatActivity {

    String username;

    ListView eventsToManage;

    FirebaseDatabase database;

    DatabaseReference eventsOwnerRef,eventsRef;

    ArrayList<EventItem> your_events = new ArrayList<>();

    ArrayAdapter<EventItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_events);


        username = this.getIntent().getStringExtra("username");

        eventsToManage = findViewById(R.id.eventsToManage);

        database = FirebaseDatabase.getInstance();
        eventsOwnerRef = database.getReference("eventsOwner").child(username);
        eventsRef = database.getReference("events");


        adapter = new ArrayAdapter<EventItem>(this, android.R.layout.simple_list_item_1, your_events){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.manage_event_item, parent, false);
                }
                EventItem currentEvent = your_events.get(position);

                TextView titleTextView = convertView.findViewById(R.id.manageEventTitle);
                titleTextView.setText(currentEvent.getTitle());


                TextView dateTextView = convertView.findViewById(R.id.manageEventDate);
                dateTextView.setText(currentEvent.getDate());


                TextView timeTextView = convertView.findViewById(R.id.manageEventTime);
                timeTextView.setText(currentEvent.getTime());


                TextView locationTextView = convertView.findViewById(R.id.manageEventLocation);
                locationTextView.setText(currentEvent.getLocation());

                TextView isPrivateTextView = convertView.findViewById(R.id.manageEventPrivate);
                if(currentEvent.getIs_private().equals("true")){
                    isPrivateTextView.setText("Private");
                    isPrivateTextView.setBackgroundColor(Color.RED);
                }else{
                    isPrivateTextView.setText("Public");
                    isPrivateTextView.setBackgroundColor(Color.GREEN);
                }

                LinearLayout manageEventItem = convertView.findViewById(R.id.manageEventItem);
                manageEventItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                    }
                });

                return convertView;
            }
        };

        eventsOwnerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> eventsArray= new ArrayList<>();
                for(DataSnapshot data : snapshot.getChildren()){
                    eventsArray.add(data.getKey());
                }

                for(String eventID : eventsArray) {
                    DatabaseReference eventRef = eventsRef.child(eventID);
                    eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EventItem eventItem = snapshot.getValue(EventItem.class);
                            if(!(eventItem==null)){
                                your_events.add(eventItem);
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

        eventsToManage.setAdapter(adapter);


    }
}