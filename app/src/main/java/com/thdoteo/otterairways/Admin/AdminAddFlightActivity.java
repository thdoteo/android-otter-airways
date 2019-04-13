package com.thdoteo.otterairways.Admin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.thdoteo.otterairways.Account.AccountCreateActivity;
import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.Flight.Flight;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;

public class AdminAddFlightActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_flight_add);

        setTitle("Add a flight");
    }

    private void invalidData()
    {
        new AlertDialog.Builder(this)
                .setTitle("Invalid data")
                .setMessage("The entered flight data are invalid.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AdminAddFlightActivity.this, MainActivity.class));
                    }
                })
                .show();
    }

    public void flight_add(View v)
    {
        // Get user input
        EditText numberET = findViewById(R.id.admin_flight_add_number);
        EditText departureET = findViewById(R.id.admin_flight_add_departure);
        EditText arrivalET = findViewById(R.id.admin_flight_add_arrival);
        EditText timeET = findViewById(R.id.admin_flight_add_time);
        EditText priceET = findViewById(R.id.admin_flight_add_price);
        EditText capacityET = findViewById(R.id.admin_flight_add_capacity);
        String number = numberET.getText().toString();
        String departure = departureET.getText().toString();
        String arrival = arrivalET.getText().toString();
        String time = timeET.getText().toString();
        Double price = -1.0;
        int capacity = -1;

        // Test data
        try {
            price = Double.parseDouble(priceET.getText().toString());
            capacity = Integer.parseInt(capacityET.getText().toString());
        }
        catch (NumberFormatException e)
        {
            invalidData();
        }
        Flight preExistingFlight = AppRoom.getDatabase(this).dao().getFlightByNumber(number);
        if ((time.isEmpty() || departure.isEmpty() || arrival.isEmpty() || number.isEmpty()) || preExistingFlight != null)
        {
            invalidData();
        }


        final Flight flight = new Flight(number, departure, arrival, time, capacity, price);

        // Show confirm dialog
        new AlertDialog.Builder(this)
                .setTitle("Is this correct?")
                .setMessage("If you click yes, the flight will be added.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Add flight to database
                        AppRoom.getDatabase(AdminAddFlightActivity.this).dao().addFlight(flight);

                        // Go back to main menu
                        startActivity(new Intent(AdminAddFlightActivity.this, MainActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

}
