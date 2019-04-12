package com.thdoteo.otterairways;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.thdoteo.otterairways.Account.Account;
import com.thdoteo.otterairways.Flight.Flight;
import com.thdoteo.otterairways.Transaction.Transaction;

import java.util.List;

@Dao
public interface AppDao {

    @Query("select * from Account")
    List<Account> getAccounts();

    @Query("select * from Account where name=:account_name")
    Account getAccount(String account_name);

    @Insert
    void addAccount(Account account);

    @Query("select * from `Transaction`")
    List<Transaction> getTransactions();

    @Insert
    void addTransaction(Transaction transaction);

    @Query("select * from Flight")
    List<Flight> getFlights();

    @Insert
    void addFlight(Flight flight);

}
