package com.example.projetandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class clubs extends AppCompatActivity implements View.OnClickListener {

    CardView quran,cindh,insec,it,ieee,fintech,
            enactus,robotic,bridge,ia,cje,
            olympiades,sportif,ultras,houseofart,
            tizi,hultprize,ensiastv,forum,tedx,adei;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubs);

        quran = findViewById(R.id.quran);
        quran.setOnClickListener(this);

        cindh = findViewById(R.id.cindh);
        cindh.setOnClickListener(this);

        insec = findViewById(R.id.insec);
        insec.setOnClickListener(this);

        it = findViewById(R.id.it);
        it.setOnClickListener(this);

        ieee = findViewById(R.id.ieee);
        ieee.setOnClickListener(this);

        fintech = findViewById(R.id.fintech);
        fintech.setOnClickListener(this);

        enactus = findViewById(R.id.enactus);
        enactus.setOnClickListener(this);

        robotic = findViewById(R.id.robotic);
        robotic.setOnClickListener(this);

        bridge = findViewById(R.id.bridge);
        bridge.setOnClickListener(this);

        ia = findViewById(R.id.ia);
        ia.setOnClickListener(this);

        cje = findViewById(R.id.cje);
        cje.setOnClickListener(this);

        olympiades = findViewById(R.id.olympiades);
        olympiades.setOnClickListener(this);

        sportif = findViewById(R.id.sportif);
        sportif.setOnClickListener(this);

        ultras = findViewById(R.id.ultras);
        ultras.setOnClickListener(this);

        houseofart = findViewById(R.id.houseofart);
        houseofart.setOnClickListener(this);

        tizi = findViewById(R.id.tizi);
        tizi.setOnClickListener(this);

        hultprize = findViewById(R.id.hultprize);
        hultprize.setOnClickListener(this);

        ensiastv = findViewById(R.id.ensiastv);
        ensiastv.setOnClickListener(this);

        forum = findViewById(R.id.forum);
        forum.setOnClickListener(this);

        tedx = findViewById(R.id.tedx);
        tedx.setOnClickListener(this);

        adei = findViewById(R.id.adei);
        adei.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this,ClubsInfo.class);
        intent.putExtra("username",this.getIntent().getStringExtra("username"));
        switch (view.getId()){
            case R.id.quran :
                intent.putExtra("club","quran");
                break;
            case R.id.cindh :
                intent.putExtra("club","cindh");
                break;
            case R.id.insec :
                intent.putExtra("club","insec");
                break;
            case R.id.it :
                intent.putExtra("club","it");
                break;
            case R.id.ieee :
                intent.putExtra("club","ieee");
                break;
            case R.id.fintech :
                intent.putExtra("club","fintech");
                break;
            case R.id.enactus :
                intent.putExtra("club","enactus");
                break;
            case R.id.robotic :
                intent.putExtra("club","robotic");
                break;
            case R.id.bridge :
                intent.putExtra("club","bridge");
                break;
            case R.id.ia :
                intent.putExtra("club","ia");
                break;
            case R.id.cje :
                intent.putExtra("club","cje");
                break;
            case R.id.olympiades :
                intent.putExtra("club","olympiades");
                break;
            case R.id.sportif :
                intent.putExtra("club","sportif");
                break;
            case R.id.ultras :
                intent.putExtra("club","ultras");
                break;
            case R.id.houseofart :
                intent.putExtra("club","houseofart");
                break;
            case R.id.tizi :
                intent.putExtra("club","tizi");
                break;
            case R.id.hultprize :
                intent.putExtra("club","hultprize");
                break;
            case R.id.ensiastv :
                intent.putExtra("club","ensiastv");
                break;
            case R.id.forum :
                intent.putExtra("club","forum");
                break;
            case R.id.tedx :
                intent.putExtra("club","tedx");
                break;
            case R.id.adei :
                intent.putExtra("club","adei");
                break;
        }
        startActivity(intent);
    }
}