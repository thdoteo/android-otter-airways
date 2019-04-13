package com.thdoteo.otterairways.Reservation;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thdoteo.otterairways.Account.Account;
import com.thdoteo.otterairways.Account.AccountCreateActivity;
import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.Flight.Flight;
import com.thdoteo.otterairways.Flight.FlightConfirmActivity;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;
import com.thdoteo.otterairways.Transaction.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class ReservationCancelActivity extends AppCompatActivity {

    private List<Reservation> filteredReservations;

    private ReservationAdapter reservationAdapter;
    private List<Reservation> reservations;
    private Reservation selectedReservation;

    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_cancel);

        setTitle("Cancel a reservation");

        // Get reservations of account and show them
        String username = getIntent().getStringExtra("ACCOUNT");
        account = AppRoom.getDatabase(this).dao().getAccount(username);
        filteredReservations = AppRoom.getDatabase(this).dao().getReservationsOfAccount(account.getId());

        if (filteredReservations.size() == 0)
        {
            // Show error dialog and quit
            new AlertDialog.Builder(this)
                    .setTitle("No reservation")
                    .setMessage("There is no reservation made with this account.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(ReservationCancelActivity.this, MainActivity.class));
                        }
                    })
                    .show();
        }

        RecyclerView rv = findViewById(R.id.reservation_cancel_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        reservationAdapter = new ReservationAdapter();
        rv.setAdapter(reservationAdapter);
    }

    public void reservation_quit(View v)
    {
        // Show error dialog and quit
        new AlertDialog.Builder(this)
                .setTitle("The cancellation has failed.")
                .setMessage("The reservation will not be cancelled.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(ReservationCancelActivity.this, MainActivity.class));
                    }
                })
                .show();
    }

    public void reservation_cancel(View v)
    {
        // Show confirm dialog
        new AlertDialog.Builder(this)
                .setTitle("Are you sure?")
                .setMessage("If you cancel you will loose your reservation.")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Get flight of reservation
                        Flight flight = AppRoom.getDatabase(ReservationCancelActivity.this).dao().getFlight(selectedReservation.getFlight_id());

                        // Create transaction
                        JSONObject transactionData = new JSONObject();
                        try {
                            transactionData.put("username", account.getName());
                            transactionData.put("reservation_id", selectedReservation.getId());
                            transactionData.put("departure", flight.getDeparture());
                            transactionData.put("arrival", flight.getArrival());
                            transactionData.put("time", flight.getDeparture_at());
                            transactionData.put("seats", selectedReservation.getSeats());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Transaction transaction = new Transaction("Cancellation", transactionData.toString(), new Date().toString());
                        AppRoom.getDatabase(ReservationCancelActivity.this).dao().addTransaction(transaction);

                        // Delete reservation from database
                        AppRoom.getDatabase(ReservationCancelActivity.this).dao().deleteReservation(selectedReservation);

                        // Go to main menu
                        startActivity(new Intent(ReservationCancelActivity.this, MainActivity.class));
                    }
                })
                .show();
    }



    private class ReservationAdapter extends RecyclerView.Adapter<ItemHolder> {

        private int selected_position = -1;

        public ReservationAdapter(){
            reservations = filteredReservations;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(ReservationCancelActivity.this);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, final int position) {
            Reservation reservation = reservations.get(position);
            String text = "Reservation " + reservation.getId();
            if (selected_position == position)
            {
                selectedReservation = reservation;
                holder.itemView.setSelected(true);
                text = text.toUpperCase();
            }
            holder.bind(text);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (selected_position == position){
                        selected_position = -1;
                        notifyDataSetChanged();
                        return;
                    }

                    selected_position = position;
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return reservations.size();
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder {

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item, parent, false));
        }

        public void bind(String str){
            TextView v = itemView.findViewById(R.id.item_id);
            v.setText(str);
        }

    }

}
