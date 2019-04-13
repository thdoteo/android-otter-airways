package com.thdoteo.otterairways;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.thdoteo.otterairways.Account.Account;
import com.thdoteo.otterairways.Flight.Flight;
import com.thdoteo.otterairways.Reservation.Reservation;
import com.thdoteo.otterairways.Transaction.Transaction;

@Database(entities = {Account.class, Transaction.class, Flight.class, Reservation.class}, version = 1)
public abstract class AppRoom extends RoomDatabase {

    private static AppRoom instance;
    public abstract AppDao dao();

    public static AppRoom getDatabase(final Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppRoom.class, "AppDB")
                    .allowMainThreadQueries().build();
        }
        return instance;
    }

    public void loadAccounts(Context context) {
        AppDao dao = getDatabase(context).dao();
        Account a1 = new Account("A@lice5", "@cSit100");
        Account a2 = new Account("$BriAn7", "123aBc##");
        Account a3 = new Account("!chriS12!", "CHrIS12!!");
        dao.addAccount(a1);
        dao.addAccount(a2);
        dao.addAccount(a3);
    }

    public void loadFlights(Context context) {
        AppDao dao = getDatabase(context).dao();
        Flight f1 = new Flight("Otter101", "Monterey", "Los Angeles", "10:30(AM)", 10, 150.00);
        Flight f2 = new Flight("Otter102", "Los Angeles", "Monterey","1:00(PM)", 10, 150.00);
        Flight f3 = new Flight("Otter201", "Monterey", "Seattle", "11:00(AM)", 5, 200.50);
        Flight f4 = new Flight("Otter205", "Monterey", "Seattle", "3:45(PM)", 15, 150.00);
        Flight f5 = new Flight("Otter202", "Seattle", "Monterey", "2:10(PM)", 5, 200.50);
        dao.addFlight(f1);
        dao.addFlight(f2);
        dao.addFlight(f3);
        dao.addFlight(f4);
        dao.addFlight(f5);
    }

}
