package com.thdoteo.otterairways.Flight;

import android.app.AlertDialog;
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

import com.thdoteo.otterairways.Account.AccountLoginActivity;
import com.thdoteo.otterairways.Admin.AdminAddFlightActivity;
import com.thdoteo.otterairways.Admin.AdminLogsActivity;
import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;

import java.util.List;

public class FlightChooseActivity extends AppCompatActivity {

    private List<Flight> filteredFlights;

    private Flight selectedFlight;

    private FlightAdapter flightAdapter;
    private List<Flight> flights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_choose);

        setTitle("Choose your flight");

        // Get filtered flights
        String d = getIntent().getStringExtra("DEPARTURE");
        String a = getIntent().getStringExtra("ARRIVAL");
        int s = getIntent().getIntExtra("SEATS", 1);
        filteredFlights = AppRoom.getDatabase(this).dao().findFlights(d, a, s);

        if (filteredFlights.size() == 0)
        {
            // Show error dialog and go to main menu
            new AlertDialog.Builder(this)
                    .setTitle("No flights")
                    .setMessage("There is no flights matching your request.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(FlightChooseActivity.this, MainActivity.class));
                        }
                    })
                    .show();
        }

        RecyclerView rv = findViewById(R.id.flight_choose_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        flightAdapter = new FlightAdapter();
        rv.setAdapter(flightAdapter);
    }

    public void goto_flight_confirm(View v)
    {
        Intent intent = new Intent(FlightChooseActivity.this, AccountLoginActivity.class);
        intent.putExtra("NEXT_ACTIVITY", FlightConfirmActivity.class);
        intent.putExtra("FLIGHT_ID", selectedFlight.getId());
        intent.putExtra("SEATS", getIntent().getIntExtra("SEATS", 1));
        startActivity(intent);
    }





    private class FlightAdapter extends RecyclerView.Adapter<ItemHolder> {

        private int selected_position = -1;

        public FlightAdapter(){
            flights = filteredFlights;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FlightChooseActivity.this);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, final int position) {
            Flight flight = flights.get(position);

            String text = flight.getNumber() + " - " + flight.getDeparture() + " -> " + flight.getArrival() + " at " + flight.getDeparture_at() + " (" + flight.getCapacity() + " seats)";
            if (selected_position == position)
            {
                selectedFlight = flight;
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
            return flights.size();
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
