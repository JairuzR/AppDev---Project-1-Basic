package Accounts;

import Bank.*;
import java.util.ArrayList;

public abstract class Account {

    private Bank bank;
    private String AccountNumber;
    private String OwnerFName, OwnerLName, OwnerEmail;
    private String pin;
    private ArrayList<Transaction> Transactions;

    public ArrayList<Transaction> getTransactions() {
        return Transactions;
    }

    public Bank getBank() {
        return bank;
    }

    public String getAccountNumber() {
        return AccountNumber;
    }

    public String getOwnerFName() {
        return OwnerFName;
    }

    public String getOwnerLName() {
        return OwnerLName;
    }

    public String getOwnerEmail() {
        return OwnerEmail;
    }

    public String getPin() {
        return pin;
    }

    /**
     *
     * @param bank
     * @param AccountNumber
     * @param OwnerFName
     * @param OwnerLName
     * @param OwnerEmail
     * @param pin
     */
    public Account(Bank bank, String AccountNumber,String OwnerFName, String OwnerLName, String OwnerEmail, String pin) {
        this.bank = bank;
        this.AccountNumber = AccountNumber;
        this.OwnerFName = OwnerFName;
        this.OwnerLName = OwnerLName;
        this.OwnerEmail = OwnerEmail;
        this.pin = pin;
        this.Transactions = new ArrayList<>();
    }

    /**
     * Gets the full name of the account owner...
     *
     * @return Full name in "First Last" format.
     */
    public String getOwnerFullName() {
        return OwnerFName + " " + OwnerLName;
    }

    public void addNewTransaction(String accountNum, Transaction.Transactions type, String description) {
        Transactions.add(new Transaction(accountNum, type, description));
    }

    public String getTransactionInfo () {
        String result = "";
        for (Transaction i : Transactions) {
            result += i.toString() + "\n";
        }
        return result;
    }
    
    @Override
    public String toString() { return String.format("Account number: %s, Owner: %s", this.AccountNumber, getOwnerFullName()); }

}
