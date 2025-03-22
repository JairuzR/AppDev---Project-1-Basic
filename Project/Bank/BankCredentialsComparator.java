package Bank;

import java.util.Comparator;

public class BankCredentialsComparator implements Comparator<Bank> {
    @Override
    public int compare(Bank b1, Bank b2) {
        if (b1.getID() == b2.getID() && b1.getPasscode().equals(b2.getPasscode())) {
            return 0;
        } else {
            return 1;
        }
    }
    
}
