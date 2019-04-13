package com.thdoteo.otterairways.Flight;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.thdoteo.otterairways.Account.Account;
import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;
import com.thdoteo.otterairways.Reservation.Reservation;

public class FlightConfirmActivity extends AppCompatActivity {

    private Account account;
    private Flight flight;
    private int seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_confirm);

        setTitle("Confirm your flight");

        // Get information
        String username = getIntent().getStringExtra("ACCOUNT");
        account = AppRoom.getDatabase(this).dao().getAccount(username);

        int flight_id = getIntent().getIntExtra("FLIGHT_ID", -1);
        flight = AppRoom.getDatabase(this).dao().getFlight(flight_id);

        seats = getIntent().getIntExtra("SEATS", 1);

        // Show information on UI
        TextView usernameTV = findViewById(R.id.flight_confirm_username);
        usernameTV.setText("Name: " + username);

        TextView flightTV = findViewById(R.id.flight_confirm_flightnumber);
        flightTV.setText("Flight: " + flight.getNumber());

        TextView tripTV = findViewById(R.id.flight_confirm_trip);
        tripTV.setText("From: " + flight.getDeparture() + "   To: " + flight.getArrival());

        TextView timeTV = findViewById(R.id.flight_confirm_time);
        timeTV.setText("Time: " + flight.getDeparture_at());

        TextView seatsTV = findViewById(R.id.flight_confirm_seats);
        seatsTV.setText("Seats: " + seats);

        TextView priceTV = findViewById(R.id.flight_confirm_price);
        priceTV.setText("Price: " + seats * flight.getPrice());
    }

    public void flight_confirm(View v)
    {
        // Create the reservation
        Reservation reservation = new Reservation(account.getId(), flight.getId(), seats, seats * flight.getPrice());
        AppRoom.getDatabase(this).dao().addReservation(reservation);

        // Go to main menu
        startActivity(new Intent(FlightConfirmActivity.this, MainActivity.class));
    }

}