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

import com.thdoteo.otterairways.Account.AccountCreateActivity;
import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;

import java.util.List;

public class ReservationCancelActivity extends AppCompatActivity {

    private List<Reservation> filteredReservations;

    private ReservationAdapter reservationAdapter;
    private List<Reservation> reservations;
    private Reservation selectedReservation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_cancel);

        setTitle("Cancel a reservation");

        // Get reservations of account and show them
        String username = getIntent().getStringExtra("ACCOUNT");
        int account_id = AppRoom.getDatabase(this).dao().getAccount(username).getId();

        filteredReservations = AppRoom.getDatabase(this).dao().getReservationsOfAccount(account_id);
        RecyclerView rv = findViewById(R.id.reservation_cancel_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        reservationAdapter = new ReservationAdapter();
        rv.setAdapter(reservationAdapter);
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
