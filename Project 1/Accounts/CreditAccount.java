package Accounts;

import Bank.*;
/**
 * Represents a Credit Account with loan functionalities.
 */
public class CreditAccount extends Account implements Payment, Recompense {

    private double loan;
    
    public double getLoan() {
        return loan;
    }

    /**
     * Constructs a credit amount.
     *
     * @param bank          The bank.
     * @param AccountNumber The account number.
     * @param OwnerFName    The owners first name.
     * @param OwnerLName    The owners last name.
     * @param OwnerEmail    The owners email.
     * @param pin           The owners pin.
     */
    public CreditAccount(Bank bank, String AccountNumber, String OwnerFName, String OwnerLName, String OwnerEmail, String pin) {
        super(bank, AccountNumber, OwnerFName, OwnerLName, OwnerEmail, pin);
        this.loan = 0.0;
    }
    
    /**
     * Retrieves the loan statement.
     * @return Loan details as a formatted string.
     */
    public String getLoanStatement() {
        return "Outstanding Loan Balance: " + String.format("%.2f", getLoan());
    }
    
    /**
     * Checks if credit adjustment is possible.
     * @param amountAdjustment The amount to adjust.
     * @return True if the adjustment is valid.
     */
    private boolean canCredit(double amountAdjustment) {
        return getLoan() - amountAdjustment >= 0;
    }
    
    /**
     * Adjusts the loan amount (ex: repayments).
     *
     * @param amountAdjustment Amount to be adjusted.
     */
    private void adjustLoanAmount(double amountAdjustment) {
        if (canCredit(amountAdjustment)) {
            loan -= amountAdjustment;
        } else {
            throw new IllegalStateException("Cannot adjust loan beyond current debt.");
        }
    }

    @Override
    public boolean recompense(double amount) {
        if (amount > 0 && canCredit(amount)) {
            adjustLoanAmount(amount);
            return true;
        }
        return false;
    }

    @Override
    public boolean pay(Account account, double amount) throws IllegalAccountType {
        if (amount <= 0) return false;
        // Assume valid account type checks happen here.
        return true;
    }


   @Override
    public String toString() {
        return super.toString() + ", Loan: $" + String.format("%.2f", loan);
    }  
}