package com.example.projetandroid;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class CreateEventActivity extends AppCompatActivity implements View.OnClickListener {


    String username;
    FirebaseDatabase database;
    DatabaseReference ref,eventsOwnerRef;
    String imageURL;
    private Calendar selectedTime;
    private Uri uri;
    private String eventId;
    private ImageView giveEventPhoto;

    EditText giveEventTitle, giveEventDate,
            giveEventTime, giveEventLocation;

    TextView titleRequired,dateRequired,timeRequired,locationRequired;


    String code;

    CheckBox makeItPrivate;

    Button createEvent,cancelCreateEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        username = this.getIntent().getStringExtra("username");

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("events");
        eventsOwnerRef = database.getReference("eventsOwner");

        giveEventTitle = findViewById(R.id.giveEventTitle);
        giveEventDate = findViewById(R.id.giveEventDate);
        giveEventDate.setInputType(InputType.TYPE_NULL);
        giveEventPhoto=findViewById(R.id.eventPhoto);
        //event date input conditions
        // Create a DatePickerDialog.OnDateSetListener
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Update the EditText with the selected date
                String selectedDate = (dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                giveEventDate.setText(selectedDate);
            }
        };

// Set an OnClickListener to the EditText to show the DatePickerDialog
        giveEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar currentDate = Calendar.getInstance();
                int year = currentDate.get(Calendar.YEAR);
                int month = currentDate.get(Calendar.MONTH);
                int day = currentDate.get(Calendar.DAY_OF_MONTH);

                // Create the DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateEventActivity.this, dateSetListener, year, month, day);

                // Show the DatePickerDialog
                datePickerDialog.show();
            }
        });


        giveEventTime = findViewById(R.id.giveEventTime);
        giveEventTime.setInputType(InputType.TYPE_NULL);

        // Create a TimePickerDialog.OnTimeSetListener
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                // Create a Calendar object with the selected time
                selectedTime = Calendar.getInstance();
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                selectedTime.set(Calendar.MINUTE, minute);

                // Update the TextView with the selected time
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                String selectedTimeString = timeFormat.format(selectedTime.getTime());
                giveEventTime.setText(selectedTimeString);
            }
        };

        // Create a TimePickerDialog and show it when the input field is clicked
        giveEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current time
                Calendar currentTime = Calendar.getInstance();

                // Create a TimePickerDialog with the current time as the initial selection
                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        CreateEventActivity.this,
                        timeSetListener,
                        currentTime.get(Calendar.HOUR_OF_DAY),
                        currentTime.get(Calendar.MINUTE),
                        false
                );

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });


        giveEventLocation = findViewById(R.id.giveEventLocation);

        titleRequired = findViewById(R.id.titleRequired);
        dateRequired = findViewById(R.id.dateRequired);
        timeRequired = findViewById(R.id.timeRequired);
        locationRequired = findViewById(R.id.locationRequired);

        makeItPrivate = findViewById(R.id.makeItPrivate);
        createEvent = findViewById(R.id.createEvent);
        cancelCreateEvent = findViewById(R.id.cancelCreateEvent);


        createEvent.setOnClickListener(this);
        cancelCreateEvent.setOnClickListener(this);

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            uri = data.getData();
                            giveEventPhoto.setImageURI(uri);
                        } else {
                            Toast.makeText(CreateEventActivity.this, "No Image Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        giveEventPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        // Set click listener for the save button


    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.createEvent :

                if(giveEventTitle.getText().toString().equals("")){
                    titleRequired.setText("*Title Required");
                    break;
                }
                if(giveEventDate.getText().toString().equals("")){
                    dateRequired.setText("*Date Required");
                    break;
                }
                if(giveEventTime.getText().toString().equals("")){
                    timeRequired.setText("*Time Required");
                    break;
                }
                if(giveEventLocation.getText().toString().equals("")){
                    locationRequired.setText("*Location Required");
                    break;
                }
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("Android images")
                        .child(uri.getLastPathSegment());

                storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete()) ;
                        Uri urlImage = uriTask.getResult();
                        imageURL = urlImage.toString();
                        String club="CLUB";
                        eventId=ref.push().getKey();
                        eventsOwnerRef.child(username).child(eventId).setValue("true");
                        ref.child(eventId).child("id").setValue(eventId);
                        ref.child(eventId).child("club").setValue(club);
                        ref.child(eventId).child("location").setValue(giveEventLocation.getText().toString());
                        ref.child(eventId).child("date").setValue(giveEventDate.getText().toString());
                        ref.child(eventId).child("time").setValue(giveEventTime.getText().toString());
                        ref.child(eventId).child("title").setValue(giveEventTitle.getText().toString());
                        ref.child(eventId).child("eventImage").setValue(imageURL);
                        if(makeItPrivate.isChecked()){
                            code = new CodeGenerator().getCode();
                            ref.child(eventId).child("is_private").setValue("true");
                            ref.child(eventId).child("eventCode").setValue(code);
                            displayCodeDialog();
                        }else {
                            ref.child(eventId).child("is_private").setValue("false");
                            ref.child(eventId).child("eventCode").setValue("");
                            finish();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(CreateEventActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            case R.id.cancelCreateEvent:
                finish();
                break;
        }


    }


    private void displayCodeDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage(code+" (coppied to clip board)");
        dialog.setTitle("The event code :");
        dialog.setNegativeButton("Close", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}