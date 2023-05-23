package com.example.projetandroid;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AttendEventDialog extends AppCompatDialogFragment {

    String username;

    AttendEventDialog(String username){
        this.username=username;
    }
    FirebaseDatabase database;
    DatabaseReference eventsRef,eventAttendeesRef;
    Query query;
    private EditText giveEventCode;
    private Button cancelEventCode,submitEventCode;

    private TextView warningOnCode;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_attend_event_dialog, null);


        submitEventCode = view.findViewById(R.id.submitEventCode);
        cancelEventCode = view.findViewById(R.id.cancelEventCode);
        warningOnCode = view.findViewById(R.id.warningOnCode);
        giveEventCode = view.findViewById(R.id.giveEventCode);

        database = FirebaseDatabase.getInstance();
        eventAttendeesRef = database.getReference("eventAttendee");
        eventsRef = database.getReference("events");


        submitEventCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String given_code=giveEventCode.getText().toString();

                if(given_code.equals("")){
                    warningOnCode.setText("Please provide a code");
                }else{
                    warningOnCode.setText("");
                    query = eventsRef.orderByChild("eventCode").equalTo(given_code);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String eventId = snapshot.getChildren().iterator().next().getKey();
                                eventAttendeesRef.child(eventId).child(username).setValue(true);
                                Toast.makeText(getContext(),"You have been registered to attend the event",Toast.LENGTH_LONG).show();
                                getDialog().dismiss();
                            } else {
                                warningOnCode.setText("Sorry, No event found with code "+given_code);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Handle errors here
                        }
                    });
                }
            }
        });
        cancelEventCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });


        builder.setView(view);


        return builder.create();
    }

}