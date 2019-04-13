package com.thdoteo.otterairways.Admin;

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

import com.thdoteo.otterairways.Account.AccountCreateActivity;
import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;
import com.thdoteo.otterairways.Reservation.ReservationCancelActivity;
import com.thdoteo.otterairways.Transaction.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AdminLogsActivity extends AppCompatActivity {

    private LogsAdapter logsAdapter;
    private List<Transaction> logs;
    private List<Transaction> filteredLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_logs);

        setTitle("Logs");

        // Get logs
        filteredLogs = AppRoom.getDatabase(AdminLogsActivity.this).dao().getTransactions();
        if (filteredLogs.size() == 0)
        {
            // Show error dialog and go to next admin activity
            new AlertDialog.Builder(this)
                    .setTitle("No logs")
                    .setMessage("There is no logs at the moment.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(AdminLogsActivity.this, AdminAddFlightActivity.class));
                        }
                    })
                    .show();
        }

        // Show logs in list
        RecyclerView rv = findViewById(R.id.admin_logs_list);
        rv.setLayoutManager(new LinearLayoutManager(this));
        logsAdapter = new LogsAdapter();
        rv.setAdapter(logsAdapter);
    }

    public void goto_admin_flight(View v)
    {
        new AlertDialog.Builder(this)
                .setTitle("Do you want to add a flight?")
                .setMessage("If you click yes you will be able to add a new flight. Clicking no will redirect you to the main menu.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AdminLogsActivity.this, AdminAddFlightActivity.class));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(AdminLogsActivity.this, MainActivity.class));
                    }
                })
                .show();
    }





    private class LogsAdapter extends RecyclerView.Adapter<ItemHolder> {

        public LogsAdapter(){
            logs = filteredLogs;
        }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(AdminLogsActivity.this);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            Transaction transaction = logs.get(position);
            if (transaction.getType().equals("New account"))
            {
                holder.bind(transaction.getType() + ": " + transaction.getData() + " (" + transaction.getDatetime() + ")");
            }
            else
            {
                holder.bind( "Transaction: " + transaction.getType() + " (" + transaction.getDatetime() + ")");
            }
        }

        @Override
        public int getItemCount() {
            return logs.size();
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
