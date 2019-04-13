package com.thdoteo.otterairways;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.thdoteo.otterairways.Account.Account;
import com.thdoteo.otterairways.Account.AccountCreateActivity;
import com.thdoteo.otterairways.Account.AccountLoginActivity;
import com.thdoteo.otterairways.Admin.AdminLogsActivity;
import com.thdoteo.otterairways.Flight.Flight;
import com.thdoteo.otterairways.Flight.FlightFindActivity;
import com.thdoteo.otterairways.Reservation.ReservationCancelActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Feed database with accounts
        List<Account> accounts = AppRoom.getDatabase(MainActivity.this).dao().getAccounts();
        if (accounts.size() == 0){
            AppRoom.getDatabase(this).loadAccounts(this);
        }
        Log.d("ACCOUNTS NB", "" + accounts.size());

        // Feed database with flights
        List<Flight> flights = AppRoom.getDatabase(MainActivity.this).dao().getFlights();
        if (flights.size() == 0){
            AppRoom.getDatabase(this).loadFlights(this);
        }
        Log.d("FLIGHTS NB", "" + flights.size());
    }

    public void goto_account_create(View v)
    {
        startActivity(new Intent(MainActivity.this, AccountCreateActivity.class));
    }

    public void goto_flight_find(View v)
    {
        startActivity(new Intent(MainActivity.this, FlightFindActivity.class));
    }

    public void goto_reservations_cancel(View v)
    {
        Intent intent = new Intent(MainActivity.this, AccountLoginActivity.class);
        intent.putExtra("NEXT_ACTIVITY", ReservationCancelActivity.class);
        startActivity(intent);
    }

    public void goto_admin_logs(View v)
    {
        Intent intent = new Intent(MainActivity.this, AccountLoginActivity.class);
        intent.putExtra("NEXT_ACTIVITY", AdminLogsActivity.class);
        startActivity(intent);
    }
}
