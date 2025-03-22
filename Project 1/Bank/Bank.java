package Bank;

import Accounts.*;
import Main.Field;

import java.sql.Struct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 *Bank class
 */
public class Bank {

    /***
     * ID for bank account I guess, not really sure for now
     */
    private int ID;

    /**
     * Name and password of the user account
     */
    private String name, passcode;

    /**
     * The most amount the user can deposit
     */
    private double depositLimit;

    /**
     * The most amount the user can withdraw
     */
    private double withdrawLimit;

    /**
     * Limit amount of all Credit Account can loan
     */
    private double creditLimit;

    /**
     * Fee added when there is other bank transaction
     */
    private double processingFee;

    /**
     * Stores all the account in this bank
     */
    private ArrayList<Account> bankAccounts;
    
    /**
     * Single Scanner instance for all input operations
     */
    private Scanner scanner;

    public Bank(int ID, String name, String passcode) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = 50000.00d;
        this.withdrawLimit = 50000.00d;
        this.creditLimit = 100000.00d;
        this.processingFee = 10.00d;
        bankAccounts = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public Bank(int ID, String name, String passcode, double depositLimit, double withdrawLimit, double creditLimit, double processingFee) {
        this.ID = ID;
        this.name = name;
        this.passcode = passcode;
        this.depositLimit = depositLimit;
        this.withdrawLimit = withdrawLimit;
        this.creditLimit = creditLimit;
        this.processingFee = processingFee;
        bankAccounts = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Retrieves the bank ID
     * @return  the bank ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Retrieves the bank name
     * @return  Bank Name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the passcode for the bank.
     * @return the passcode of the bank
     */
    public String getPasscode() {
        return passcode;
    }

    /**
     * Retrieves the deposit limit
     * @return the deposit limit of the bank
     */
    public double getDepositLimit() {
        return depositLimit;
    }

    /**
     * Retrieves the withdraw limit of the bank.
     * @return the withdraw limit of the bank
     */
    public double getWithdrawLimit() {
        return withdrawLimit;
    }

    /**
     * Retrieves the credit limit of the bank.
     * @return the credit limit of the bank
     */
    public double getCreditLimit() {
        return creditLimit;
    }

    /**
     * Retrieves the processing fee associated with the bank.
     *
     * @return the processing fee of the bank as a double.
     */
    public double getProcessingFee() {
        return processingFee;
    }

    /**
     * Shows account based on its type
     *
     * @param accountType
     */
    public void showAccounts(Class accountType) {
//        System.out.printf("Showing %ss: \n", accountType.getSimpleName());

        for (int i = 0; i < bankAccounts.size(); i++) {
            if (accountType.isInstance(bankAccounts.get(i))) {
                System.out.printf("[%d]. %s\n", i+1, bankAccounts.get(i));
            }
        }
    }

    /**
     * Search for a specific account in a specific bank
     *
     * @param bank
     * @param accountNumber
     * @return Account if it had similar bank and account number
     */
    public Account getBankAccount(Bank bank, String accountNumber) {
        for (Account account : bankAccounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;
    }

    /**
     * Collects the basic information needed to create a new account.
     * 
     * @return ArrayList of Strings containing account information in the order:
     *         account number, PIN, first name, last name, email
     */
    public ArrayList<String> createNewAccount() {
        ArrayList<String> accountInfo = new ArrayList<>();
        
        // Collect account number
        System.out.print("Enter account number: ");
        String accountNumber = scanner.nextLine();
        accountInfo.add(accountNumber);
        
        // Collect PIN
        System.out.print("Enter PIN: ");
        String pin = scanner.nextLine();
        accountInfo.add(pin);
        
        // Collect first name
        System.out.print("Enter owner firstname: ");
        String firstName = scanner.nextLine();
        accountInfo.add(firstName);
        
        // Collect last name
        System.out.print("Enter owner lastname: ");
        String lastName = scanner.nextLine();
        accountInfo.add(lastName);
        
        // Collect email
        String email;
        boolean validEmail = false;
        int attempts = 0;
        do {
            System.out.print("Enter owner email: ");
            email = scanner.nextLine();
            
            // Check if email contains '@' symbol
            if (email.contains("@") && email.indexOf("@") < email.length() - 1) {
                validEmail = true;
                accountInfo.add(email);
            } else {
                attempts++;
                System.out.println("Invalid! Email must contain an '@' symbol followed by a domain.");
                if (attempts >= 3) {
                    System.out.println("Too many invalid attempts. Using default email: user@example.com");
                    email = "user@example.com";
                    accountInfo.add(email);
                    validEmail = true;
                }
            }
        } while (!validEmail);
        
        return accountInfo;
    }

    /**
     * Creates a new credit account for the bank by gathering required information,
     * initializing a CreditAccount instance with the provided details, and adding
     * it to the bank's account list.
     * 
     * @return the newly created CreditAccount instance or null if creation fails
     */
    public CreditAccount createNewCreditAccount() {
        ArrayList<String> accountInfo = this.createNewAccount();
        String accountNumber = accountInfo.get(0);
        String pin = accountInfo.get(1);
        String firstName = accountInfo.get(2);
        String lastName = accountInfo.get(3);
        String email = accountInfo.get(4);

        // Checks if an account with the same account number already exists
        if (accountExists(accountNumber)) {
            System.out.println("Account already exists!");
            return null;
        }

        CreditAccount creditAccount = new CreditAccount(this, accountNumber, firstName, lastName, email, pin);
        this.addNewAccount(creditAccount);
        System.out.println("Credit account created successfully!");
        return creditAccount;
    }

    /**
     * Creates a new savings account for the bank by gathering required information,
     * initializing a SavingsAccount instance with the provided details,
     * and adding it to the bank's account list.
     * 
     * @return the newly created SavingsAccount instance or null if creation fails
     */
    public SavingsAccount createNewSavingsAccount() {
        ArrayList<String> accountInfo = this.createNewAccount();
        String accountNumber = accountInfo.get(0);
        String pin = accountInfo.get(1);
        String firstName = accountInfo.get(2);
        String lastName = accountInfo.get(3);
        String email = accountInfo.get(4);
        
        // Get initial balance
        System.out.print("Enter initial account balance: ");
        double balance = 0.0;
        try {
            balance = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid balance input. Using default balance of 0.0");
        }

        // Checks if an account with the same account number already exists
        if (accountExists(accountNumber)) {
            System.out.println("Account already exists!");
            return null;
        }

        SavingsAccount savingsAccount = new SavingsAccount(this, accountNumber, firstName, lastName, email, pin, balance);
        this.addNewAccount(savingsAccount);
        System.out.println("Savings account created successfully!");
        return savingsAccount;
    }
    
    /**
     * Adding accounts to a bank
     * @param account
     */
    public void addNewAccount(Account account) {
        if (!accountExists(account.getAccountNumber())) {
            bankAccounts.add(account);
        }
    }

    /**
     * Checks if an account exists in this bank
     * @param accountNumber the account number to check
     * @return true if the account exists, false otherwise
     */
    public boolean accountExists(String accountNumber) {
        Account account = getBankAccount(this, accountNumber);
        return account != null;
    }
    
    /**
     * Closes the scanner when done with bank operations
     */
    public void closeScanner() {
        if (this.scanner != null) {
            this.scanner.close();
        }
    }

    /**
     * String representation of a bank
     * @return the bank's string representation
     */
    public String toString() {
        return String.format("Name: %s\n", name);
    }
}