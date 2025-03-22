package Accounts;

import Bank.*;
import Main.*;
import java.util.Scanner;

public class SavingsAccountLauncher extends AccountLauncher {
    private static final Scanner scanner = new Scanner(System.in); // Scanner for user input

    // Method to initialize a savings account
    public static void savingsAccountInit() {
        while (true) {
            Main.showMenuHeader("Savings Account Main Menu");
            Main.showMenu(51);
            Main.setOption();

            int option = Main.getOption();
            switch (option) {
                case 1:
                    showBalance();
                    break;
                case 2:
                    depositProcess();
                    break;
                case 3:
                    withdrawProcess();
                    break;
                case 4:
                    fundTransferProcess();
                    break;
                case 5:
                    showTransactions();
                    break;
                case 6:
                    return; // Exit the menu
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    // Method to show the balance of the savings account
    private static void showBalance() {
        double balance = getLoggedAccount().getAccountBalance();
        System.out.println("Current balance: $" + balance);
    }

    // Method for the deposit process
    private static void depositProcess() {
        System.out.print("Enter deposit amount: ");
        double depositAmount = getDoubleInput();

        if (depositAmount > 0) {
            boolean depositSuccess = getLoggedAccount().cashDeposit(depositAmount);
            if (depositSuccess) {
                System.out.println("Deposit of $" + depositAmount + " processed successfully.");
            } else {
                System.out.println("Deposit failed.");
            }
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    // Method for the withdrawal process
    private static void withdrawProcess() {
        System.out.print("Enter withdrawal amount: ");
        double withdrawAmount = getDoubleInput();

        if (withdrawAmount > 0) {
            boolean withdrawSuccess = getLoggedAccount().withdrawal(withdrawAmount);
            if (withdrawSuccess) {
                System.out.println("Withdrawal of $" + withdrawAmount + " processed successfully.");
            } else {
                System.out.println("Insufficient balance.");
            }
        } else {
            System.out.println("Invalid withdrawal amount.");
        }
    }

    // Method for the fund transfer process
    // Method for the fund transfer process
    private static void fundTransferProcess() {
        System.out.println("\nAvailable Banks:");
        for (Bank bank : BankLauncher.banks) {
            System.out.printf("ID: %d - Name: %s\n", bank.getID(), bank.getName());
        }

        System.out.print("Enter bank ID: ");
        int bankID = getIntInput();

        System.out.print("Enter target account number: ");
        String targetAccountNumber = scanner.nextLine();

        System.out.print("Enter transfer amount: $");
        double transferAmount = getDoubleInput();

        Bank targetBank = null;
        for (Bank bank : BankLauncher.banks) {
            if (bank.getID() == bankID) {
                targetBank = bank;
                break;
            }
        }

        if (targetBank != null) {
            Account targetAccount = targetBank.getBankAccount(targetBank, targetAccountNumber);
            if (targetAccount != null) {
                try {
                    boolean transferSuccess = getLoggedAccount().transfer(targetAccount, transferAmount);
                    if (transferSuccess) {
                        System.out.println("Fund transfer of $" + transferAmount + " processed successfully.");
                    } else {
                        System.out.println("Transfer failed. Please check your balance.");
                    }
                } catch (IllegalAccountType e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
                System.out.println("Target account not found.");
            }
        } else {
            System.out.println("Invalid bank ID.");
        }
    }

    // Method to show transactions
    private static void showTransactions() {
        System.out.println("<-----Transactions----->");
        String transactionLog = getLoggedAccount().getTransactionInfo();
        System.out.println(transactionLog);
    }

    // Helper method to get a double input from the user
    private static double getDoubleInput() {
        double value = 0.0;
        while (true) {
            try {
                value = Double.parseDouble(scanner.nextLine());
                break; // Exit loop if input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    // Helper method to get an integer input from the user
    private static int getIntInput() {
        int value = 0;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                break; // Exit loop if input is valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    // Method to get the logged savings account
    protected static SavingsAccount getLoggedAccount() {
        return (SavingsAccount) AccountLauncher.getLoggedAccount();
    }
}