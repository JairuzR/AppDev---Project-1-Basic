package Accounts;

import Bank.*;
import Main.*;

public class AccountLauncher {
    private static Account loggedAccount;
    private static Bank assocBank;

    // is loggedIn
    private static boolean isLoggedIn() {
        return loggedAccount != null;
    }

    public static void accountLogin(){

        destroyLogSession();
        assocBank = null;

        int attempts = 3;
        while (assocBank == null) {
            assocBank = selectBank();
            if (assocBank == null && attempts != 0) {
                System.out.println("Invalid bank selection. Please try again. Attempts: " + attempts);
                attempts--;
            } else if (assocBank == null && attempts == 0) {
                System.out.println("Run out of attempts. Returning to Home.");
                return;
            }
        }

        Main.showMenuHeader("Account Login");
        String accountNum = Main.prompt("Enter account number: ", true);
        String pin = Main.prompt("Enter PIN: ", true);

        loggedAccount = checkCredentials(accountNum, pin);
        if (loggedAccount != null) {
            System.out.println("Login successful.");
            setLogSession(loggedAccount);
            if (loggedAccount.getClass() == SavingsAccount.class) {
                SavingsAccountLauncher.savingsAccountInit();
            }
            else if (loggedAccount.getClass() == CreditAccount.class) {
                CreditAccountLauncher.creditAccountInit();
            }
        }
        else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    // Bank selectBank
    private static Bank selectBank() {
        BankLauncher.showBanksMenu();
        String bankNameInput = Main.prompt("Enter bank name: ", true).trim();
        
        // Print available banks for debugging
        System.out.println("Available banks:");
        for (Bank bank : BankLauncher.banks) {
            System.out.println(" - " + bank.getName());
        }
        
        // Case-insensitive comparison
        for (Bank bank : BankLauncher.banks) {
            if (bank.getName().equalsIgnoreCase(bankNameInput)) {
                System.out.println("Bank selected: " + bank.getName());
                return bank;
            }
        }
        
        System.out.println("Bank not found: " + bankNameInput);
        return null;
    }


    // set log session
    private static void setLogSession(Account account) {
        loggedAccount = account;
        System.out.println("Logged-in account set: " + account.getOwnerFullName());
    }

    // destroy log session
    private static void destroyLogSession() {
        if (isLoggedIn()) {
            loggedAccount = null;
        }
    }

    // check credentials
    public static Account checkCredentials(String accountNum, String pin) {
        Account account = BankLauncher.findAccount(accountNum);
        if (account != null && account.getPin().equals(pin)) {
            return account;
        }
        return null;
    }

    // #get logged account
    protected static Account getLoggedAccount() {
        return loggedAccount;
    }
}