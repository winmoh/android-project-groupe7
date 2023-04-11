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

public class AttendEventDialog extends AppCompatDialogFragment {


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


        submitEventCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(giveEventCode.getText().toString().equals("")){
                    warningOnCode.setText("Please provide a code");
                }else{
                    warningOnCode.setText("");
                    getDialog().dismiss();
                }
                //complete attend event code here+check if the event exist(valid code)
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
