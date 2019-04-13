package com.thdoteo.otterairways.Flight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.thdoteo.otterairways.R;

public class FlightConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_confirm);

        setTitle("Confirm your flight");

        int id = getIntent().getIntExtra("FLIGHT_ID", -1);
        Log.d("YESSSSSS", id + "");
    }

}
