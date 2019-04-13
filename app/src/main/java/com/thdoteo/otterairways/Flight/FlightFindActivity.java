package com.thdoteo.otterairways.Flight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.R;

import java.util.List;

public class FlightFindActivity extends AppCompatActivity {

    private Spinner departureSpinner;
    private Spinner arrivalSpinner;
    private EditText seatsEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_find);

        setTitle("Find a flight");

        seatsEditText = findViewById(R.id.flight_find_seats);

        departureSpinner = findViewById(R.id.flight_find_departure);
        arrivalSpinner = findViewById(R.id.flight_find_arrival);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        departureSpinner.setAdapter(adapter);
        arrivalSpinner.setAdapter(adapter);
    }

    public void flight_find(View v)
    {
        Intent intent = new Intent(FlightFindActivity.this, FlightChooseActivity.class);
        intent.putExtra("DEPARTURE", departureSpinner.getSelectedItem().toString());
        intent.putExtra("ARRIVAL", arrivalSpinner.getSelectedItem().toString());
        intent.putExtra("SEATS", Integer.parseInt(seatsEditText.getText().toString()));
        startActivity(intent);
    }

}
