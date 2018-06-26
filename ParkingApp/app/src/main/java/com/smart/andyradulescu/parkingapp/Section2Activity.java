package com.smart.andyradulescu.parkingapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class Section2Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_section2);
    }

    public void setParking1(View view) {
        LinearLayout first = findViewById(R.id.firstPlace);
        first.setBackgroundResource(R.drawable.selected_border);
    }

    public void setParking2(View view) {
        LinearLayout first = findViewById(R.id.secondPlace);
        first.setBackgroundResource(R.drawable.selected_border);
    }

    public void setParking3(View view) {
        LinearLayout first = findViewById(R.id.thirdPlace);
        first.setBackgroundResource(R.drawable.selected_border);
    }

    public void setParking4(View view) {
        LinearLayout first = findViewById(R.id.fourthPlace);
        first.setBackgroundResource(R.drawable.selected_border);
    }

    public void setParking5(View view) {
        LinearLayout first = findViewById(R.id.fifthPlace);
        first.setBackgroundResource(R.drawable.selected_border);
    }

    public void setParking6(View view) {
        LinearLayout first = findViewById(R.id.sixthPlace);
        first.setBackgroundResource(R.drawable.selected_border);
    }
}
