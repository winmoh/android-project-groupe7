package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventsListActivity extends AppCompatActivity {

    String username;

    ListView eventItems;

    Button eventItemCancel;
    FirebaseDatabase database;

    DatabaseReference eventAttendeesRef,eventsRef;

    FirebaseStorage storage;
    StorageReference sref;



    Query eventsToAttendQuery;

    ArrayList<EventItem> events_to_attend_array = new ArrayList<>();

    ArrayAdapter<EventItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);

        username=this.getIntent().getStringExtra("username");
        eventItems = findViewById(R.id.eventItems);
        storage=FirebaseStorage.getInstance();

        database = FirebaseDatabase.getInstance();
        eventAttendeesRef = database.getReference("eventAttendee");
        eventsRef = database.getReference("events");



        adapter = new ArrayAdapter<EventItem>(this, android.R.layout.simple_list_item_1, events_to_attend_array){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.event_item, parent, false);
                }
                EventItem currentEvent = events_to_attend_array.get(position);

                TextView titleTextView = convertView.findViewById(R.id.eventItemTitle);
                titleTextView.setText(currentEvent.getTitle());





                TextView dateTextView = convertView.findViewById(R.id.eventItemDate);
                dateTextView.setText(currentEvent.getDate());


                TextView timeTextView = convertView.findViewById(R.id.eventItemTime);
                timeTextView.setText(currentEvent.getTime());


                TextView locationTextView = convertView.findViewById(R.id.eventItemLocation);
                locationTextView.setText(currentEvent.getLocation());
                ImageView eventImage=convertView.findViewById(R.id.eventImage);
                String imageUrl=currentEvent.getEventImage();
                sref=storage.getReferenceFromUrl(imageUrl);

                try{
                    File LocalFile=File.createTempFile("imageHolder",".jpg");
                    sref.getFile(LocalFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                            Bitmap bitmap= BitmapFactory.decodeFile(LocalFile.getAbsolutePath());
                            eventImage.setImageBitmap(bitmap);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EventsListActivity.this,e.getMessage(),Toast.LENGTH_SHORT);
                        }
                    });
                }
                catch(IOException e){
                    e.setStackTrace(e.getStackTrace());
                }




                eventItemCancel = convertView.findViewById(R.id.eventItemCancel);

                eventItemCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String eventId = currentEvent.getId();


                        database.getReference("eventAttendee/" + eventId + "/" + username).removeValue();

                        events_to_attend_array.remove(currentEvent);

                        notifyDataSetChanged();
                    }
                });
                return convertView;
            }
        };


        eventsToAttendQuery = eventAttendeesRef.orderByChild(username).equalTo(true);

        eventsToAttendQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> eventIDs = new ArrayList<>();
                for (DataSnapshot eventSnapshot : snapshot.getChildren()) {
                    String eventId = eventSnapshot.getKey();
                    eventIDs.add(eventId);
                }

                for(String eventID : eventIDs) {
                    DatabaseReference eventRef = eventsRef.child(eventID);
                    eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            EventItem eventItem = snapshot.getValue(EventItem.class);
                            events_to_attend_array.add(eventItem);
                            adapter.notifyDataSetChanged();
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





        eventItems.setAdapter(adapter);


    }
}
