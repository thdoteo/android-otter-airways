package com.thdoteo.otterairways.Account;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.thdoteo.otterairways.Admin.AdminLogsActivity;
import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;

public class AccountLoginActivity extends AppCompatActivity {

    private String adminUsername = "!admiM2";
    private String adminPassword = "!admiM2";

    private boolean hasAlreadyFailed;

    private Class nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_login);

        Bundle extras = getIntent().getExtras();
        nextActivity = (Class<Activity>)extras.getSerializable("NEXT_ACTIVITY");

        setTitle(getActivityTitle(nextActivity.getSimpleName()));
    }

    private String getActivityTitle(String name)
    {
        String result = name.replace("Activity", "").replaceAll("([^_])([A-Z])", "$1 $2");
        return "Login to " + result;
    }

    private boolean isValidLogin(String name, String password)
    {
        Account account = AppRoom.getDatabase(this).dao().getAccount(name);
        if (account == null)
        {
            return false;
        }
        if (account.getPassword().equals(password))
        {
            return true;
        }
        return false;
    }

    public void account_login(View v)
    {
        // Get user inputs
        TextView nameTV = findViewById(R.id.account_login_name);
        final String name = nameTV.getText().toString();
        TextView passwordTV = findViewById(R.id.account_login_password);
        String password = passwordTV.getText().toString();

        // Check if user is authorized
        boolean test = isValidLogin(name, password);
        if (nextActivity == AdminLogsActivity.class)
        {
            test = name.equals(adminUsername) && password.equals(adminPassword);
        }

        // Redirect to next activity
        if (test)
        {
            new AlertDialog.Builder(this)
                    .setTitle("Logged in")
                    .setMessage("You were successfully logged in!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(AccountLoginActivity.this, nextActivity);
                            intent.putExtras(getIntent().getExtras());
                            intent.putExtra("ACCOUNT", name);
                            startActivity(intent);
                        }
                    })
                    .show();
        }
        else
        {
            if (hasAlreadyFailed)
            {
                startActivity(new Intent(AccountLoginActivity.this, MainActivity.class));
            }
            else
            {
                hasAlreadyFailed = true;
                new AlertDialog.Builder(this)
                        .setTitle("Invalid data")
                        .setMessage("The username and/or password you entered do not match an account.")
                        .setPositiveButton(android.R.string.yes, null)
                        .show();
            }
        }
    }

}
