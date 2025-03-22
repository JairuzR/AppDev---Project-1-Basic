package Bank;

import Main.*;
import Accounts.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Launcher class for bank management system.
 * Handles bank creation, login, and account management operations.
 */
public class BankLauncher {
    /** List of all registered banks */
    public static ArrayList<Bank> banks = new ArrayList<>();
    
    /** Currently logged in bank */
    private static Bank loggedBank;
    
    /** Scanner for user input */
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Checks if a bank is currently logged in
     * @return true if a bank is logged in, false otherwise
     */
    public static boolean isLogged() {
        return loggedBank != null;
    }

    /**
     * Main bank interface menu, runs while a bank is logged in
     */
    public static void bankInit() {
        while(isLogged()) {
            Main.showMenuHeader("Bank Menu");
            Main.showMenu(31);
            Main.setOption();

            switch(Main.getOption()) {
                case 1:
                    showAccounts();
                    break;
                case 2:
                    newAccounts();
                    break;
                case 3:
                    logout();
                    break;
                default:
                    System.out.println("Invalid Option. Please try again.");
            }
        }
    }

    /**
     * Displays accounts based on user selection
     */
    private static void showAccounts() {
        if(isLogged()) {
            Main.showMenu(32);
            Main.setOption();
            switch(Main.getOption()) {
                case 1:
                    loggedBank.showAccounts(CreditAccount.class);
                    break;
                case 2:
                    loggedBank.showAccounts(SavingsAccount.class);
                    break;
                case 3:
                    loggedBank.showAccounts(Account.class);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid input. Please try again.");
            }
        }
    }

    /**
     * Creates new accounts based on user selection
     */
    private static void newAccounts() {
        Main.showMenu(33);
        Main.setOption();

        switch(Main.getOption()) {
            case 1:
                // New credit account
                CreditAccount newCreditAccount = loggedBank.createNewCreditAccount();
                if (newCreditAccount != null) {
                    System.out.println("Credit account with account number " + newCreditAccount.getAccountNumber() + " is created successfully!");
                }
                break;
            case 2:
                // New savings account
                SavingsAccount newSavingsAccount = loggedBank.createNewSavingsAccount();
                if (newSavingsAccount != null) {
                    System.out.println("Savings account with account number " + newSavingsAccount.getAccountNumber() + " is created successfully!");
                }
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid input. Please try again.");
        }
    }

    /**
     * Handles bank login process
     */
    public static void bankLogin() {
        System.out.print("Enter bank name: ");
        String bankName = scanner.nextLine();

        System.out.print("Enter bank passcode: ");
        String passcode = scanner.nextLine();

        Bank matchedBank = null;

        for (Bank bank : banks) {
            if (bank.getName().equals(bankName) && bank.getPasscode().equals(passcode)) {
                matchedBank = bank;
                break;
            }
        }

        if (matchedBank != null) {
            setLogSession(matchedBank);
            System.out.println("Successfully logged into " + matchedBank.getName());
            bankInit();
        } else {
            System.out.println("Bank not found. Either invalid bank name or passcode. Please try again.");
        }
    }

    /**
     * Sets the current logged in bank
     * @param b The bank to set as logged in
     */
    private static void setLogSession(Bank b) {
        loggedBank = b;
    }

    /**
     * Logs out the current bank session
     */
    private static void logout() {
        System.out.println("Logged out successfully from " + loggedBank.getName());
        loggedBank = null;
    }

    /**
     * Creates a new bank and adds it to the system
     */
    public static void createNewBank() {
        try {
            System.out.print("Bank Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Bank Passcode: ");
            String passcode = scanner.nextLine();
            
            // Validate passcode length
            if (passcode.length() < 5) {
                System.out.println("Passcode must be at least 5 characters long.");
                return;
            }
            
            double depositLimit = 0.0;
            double withdrawLimit = 0.0;
            double creditLimit = 0.0;
            double processingFee = 0.0;
            
            System.out.print("Set Deposit Limit (Enter 0 for default): ");
            depositLimit = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Set Withdraw Limit (Enter 0 for default): ");
            withdrawLimit = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Set Credit Limit (Enter 0 for default): ");
            creditLimit = Double.parseDouble(scanner.nextLine());
            
            while(true){
                System.out.print("Set Processing Fee (%) (Enter 0 for default): ");
                processingFee = Double.parseDouble(scanner.nextLine()) / 100;
                if (processingFee > 1) {
                    System.out.println("Processing fee cannot be more than 100%");
                    continue;
                } else {
                    break;
                }
            }
                

            Bank newBank;
            if (depositLimit == 0.0 && withdrawLimit == 0.0 && creditLimit == 0.0 && processingFee == 0.0) {
                newBank = new Bank(bankSize(), name, passcode);
            } else {
                newBank = new Bank(bankSize(), name, passcode, depositLimit, withdrawLimit, creditLimit, processingFee);
            }

            addBank(newBank);
        } catch (NumberFormatException e) {
            System.out.println("Invalid! Please enter a valid number.");
        }
    }

    /**
     * Displays all registered banks
     */
    public static void showBanksMenu() {
        if (bankSize() == 0) {
            System.out.println("No banks registered yet.");
            return;
        }
        
        System.out.println("List of Registered Banks: ");
        for (int i = 0; i < bankSize(); i++) {
            System.out.printf("[%d]. Bank ID No.%d: %s\n", i + 1, banks.get(i).getID(), banks.get(i).getName());
        }
    }

    /**
     * Adds a new bank to the system if it doesn't already exist
     * @param b The bank to add
     */
    private static void addBank(Bank b) {
        // Check if a bank with the same name already exists
        for (Bank bank : banks) {
            if (bank.getName().equals(b.getName())) {
                System.out.println("A bank the same name already exists!");
                return;
            }
        }
        banks.add(b);
        System.out.println("Bank added successfully.");
    }

    /**
     * Finds a bank using a comparator
     * @param comparator The comparator to use for comparison
     * @param bank The bank to compare with
     * @return The matching bank or null if not found
     */
    public static Bank getBank(Comparator<Bank> comparator, Bank bank) {
        for (Bank b : banks) {
            if (comparator.compare(bank, b) == 0) {
                return b;
            }
        }
        return null; // Bank not found
    }

    /**
     * Finds an account by account number across all banks
     * @param accountNum The account number to search for
     * @return The account if found, null otherwise
     */
    public static Account findAccount(String accountNumber) {
        System.out.println("Searching for account: " + accountNumber);
        System.out.println("Number of banks: " + banks.size());
        
        for (Bank bank : banks) {
            System.out.println("Searching in bank: " + bank.getName());
            Account account = bank.getBankAccount(bank, accountNumber);
            if (account != null) {
                System.out.println("Account found!");
                return account;
            }
        }
        
        System.out.println("Account not found!");
        return null;
    }

    /**
     * Returns the number of registered banks
     * @return The number of banks
     */
    public static int bankSize() {
        return banks.size();
    }
    
    /**
     * Checks if an account exists in the specified bank
     * @param bank the bank to check
     * @param accountNumber the account number to check
     * @return true if the account exists, false otherwise
     */
    public static boolean accountExists(Bank bank, String accountNumber) {
        if (bank == null) {
            return false;
        }
        return bank.accountExists(accountNumber);
    }
}