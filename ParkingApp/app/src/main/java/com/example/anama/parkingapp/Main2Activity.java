package com.example.anama.parkingapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    private Dialog mDialog;
    private Button mDialogyes, mDialogno;

    //TODO: add the label to show how many available places are in the data packing slot.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    protected void createDialog() {
        mDialog = new Dialog(this);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_exit);
        mDialog.setCanceledOnTouchOutside(true);
        mDialog.setCancelable(true);
        mDialogyes = (Button) findViewById(R.id.yes);
        mDialogno = (Button) findViewById(R.id.no);

        mDialogyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_MAIN);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in.addCategory(Intent.CATEGORY_HOME);
                startActivity(in);
                finish();
                System.exit(0);

                mDialogno.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mDialog.dismiss();
                    }
                });
            }
        });
    }

    public void Action(View v) {
        Intent nextFrame = new Intent(Main2Activity.this, Main3Activity.class);
        startActivity(nextFrame);
    }
}

