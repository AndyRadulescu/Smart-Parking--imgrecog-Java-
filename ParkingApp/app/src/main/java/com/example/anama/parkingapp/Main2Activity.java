package com.example.anama.parkingapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import model.DbHelper;
import model.ParsedParking;
import server.service.DBService;

public class Main2Activity extends AppCompatActivity {

    private Dialog mDialog;
    private Button mDialogyes, mDialogno;
    private TextView mTvParkingPlaces;
    private DbHelper mDbHelper;
    private List<ParsedParking> parkingPLaces = new ArrayList<>();

    //TODO: add the label to show how many available places are in the data packing slot.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        mDbHelper = new DbHelper(this);
        this.mTvParkingPlaces = findViewById(R.id.numberOfPlaces);
        this.parkingPLaces = DBService.getInfoFromDb(mDbHelper, parkingPLaces);
        updateParkingNumber();
    }

    private void updateParkingNumber() {
        int nr = 0;
        for (ParsedParking item : parkingPLaces) {
            if (item.getAvailability() == 1) {
                nr++;
            }
        }
        this.mTvParkingPlaces.setText(String.valueOf(nr));
    }

//    protected void createDialog() {
//        mDialog = new Dialog(this);
//        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        mDialog.setContentView(R.layout.dialog_exit);
//        mDialog.setCanceledOnTouchOutside(true);
//        mDialog.setCancelable(true);
//        mDialogyes = findViewById(R.id.yes);
//        mDialogno = findViewById(R.id.no);
//
//        mDialogyes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent(Intent.ACTION_MAIN);
//                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                in.addCategory(Intent.CATEGORY_HOME);
//                startActivity(in);
//                finish();
//                System.exit(0);
//
//                mDialogno.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mDialog.dismiss();
//                    }
//                });
//            }
//        });
//    }

    public void showParking(View v) {
        Intent nextFrame = new Intent(Main2Activity.this, Main3Activity.class);
        startActivity(nextFrame);
    }
}

