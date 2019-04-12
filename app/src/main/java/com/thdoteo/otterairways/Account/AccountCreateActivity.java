package com.thdoteo.otterairways.Account;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.thdoteo.otterairways.AppRoom;
import com.thdoteo.otterairways.MainActivity;
import com.thdoteo.otterairways.R;
import com.thdoteo.otterairways.Transaction.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class AccountCreateActivity extends AppCompatActivity {

    private boolean hasAlreadyFailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_create);

        setTitle("Create an account");
    }

    private boolean validateData(String input)
    {
        if (input.equals("!admiM2"))
        {
            return false;
        }

        boolean rule1 = false, rule2 = false, rule3 = false, rule4 = false;

        for (int i = 0; i < input.length(); i++)
        {
            char c = input.charAt(i);
            if (c == '!' || c == '@' || c == '#' || c == '$')
            {
                rule1 = true;
            }
            else if (Character.isDigit(c))
            {
                rule2 = true;
            }
            else if (Character.isUpperCase(c))
            {
                rule3 = true;
            }
            else if (Character.isLowerCase(c))
            {
                rule4 = true;
            }
        }

        return rule1 && rule2 && rule3 && rule4;
    }

    private boolean isUsernameDuplicate(String name)
    {
        Account account = AppRoom.getDatabase(this).dao().getAccount(name);
        if (account == null)
        {
            return true;
        }
        return false;
    }

    public void account_create(View v) throws JSONException {
        // Get user inputs
        TextView nameTV = findViewById(R.id.account_create_name);
        String name = nameTV.getText().toString();
        TextView passwordTV = findViewById(R.id.account_create_password);
        String password = passwordTV.getText().toString();

        boolean test1 = validateData(name) && validateData(password);
        boolean test2 = isUsernameDuplicate(name);

        if (test1 && test2)
        {
            // Create account
            Account account = new Account(name, password);
            AppRoom.getDatabase(AccountCreateActivity.this).dao().addAccount(account);

            // Create transaction
            JSONObject transactionData = new JSONObject();
            transactionData.put("username", name);
            Transaction transaction = new Transaction("New account", transactionData.toString(), new Date().toString());
            AppRoom.getDatabase(AccountCreateActivity.this).dao().addTransaction(transaction);

            // Show confirmation dialog
            new AlertDialog.Builder(this)
                    .setTitle("Account created")
                    .setMessage("Your account has been successfully created!")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(AccountCreateActivity.this, MainActivity.class));
                        }
                    })
                    .show();
        }
        else
        {
            if (hasAlreadyFailed)
            {
                startActivity(new Intent(AccountCreateActivity.this, MainActivity.class));
            }
            else
            {
                hasAlreadyFailed = true;
                String message = "";
                if (!test1)
                {
                    message = "The username and/or password are not in the correct format.";
                }
                else
                {
                    message = "The username is already taken.";
                }
                new AlertDialog.Builder(this)
                        .setTitle("Invalid data")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.yes, null)
                        .show();
            }
        }

    }

}
