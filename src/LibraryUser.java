import java.util.*;

public abstract class LibraryUser {

    private String name;
    private List<Loan> loans;

    // LibraryUser Constructor
    public LibraryUser (String name) {
        setName(name);
        this.loans = new ArrayList<>();
    }

    // Setter for the user's name. Should throw an appropriate exception if the name is not valid.
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name.trim();
    }

    // Getter method for the user's name.
    public String getName() {
        return name;
    }

    // Assigns the specified loan to the user. This method should perform appropriate validation checks to ensure that
    // the loan is valid for the user, and throw appropriate exceptions as needed.
    public void assignLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }

        if (loans.contains(loan)) {
            throw new IllegalArgumentException("Loan already assigned to this user");
        }

        if (isAtLoanLimit()) {
            throw new LoanLimitExceededException("User has reached their loan limit");
        }

        if (isSuspended()) {
            throw new AccountSuspendedException("User account is suspended due to excessive fines");
        }

        loans.add(loan);
    }

    // Removes the specified loan from the user. Should throw an appropriate exception if the loan is not valid.
    public void removeLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }

        if (!loans.contains(loan)) {
            throw new IllegalArgumentException("Loan is not assigned to this user");
        }

        loans.remove(loan);
    }

    // Returns true if the user's current fine total equals or exceeds their suspension limit.
    public boolean isSuspended () {
        return getTotalFines() >= getFineSuspensionLimit();
    }

    // Returns a list of loans currently active for the user.
    // This should be an unmodifiable list to prevent unexpected modification.
    public List<Loan> getLoans () {
        return Collections.unmodifiableList(loans);
    }

    // Returns the total amount of fines that the user has accrued.
    public double getTotalFines() {
        double totalFines = 0.0;
        for (Loan loan : loans) {
            totalFines += loan.getLoanFine();
        }
        return totalFines;
    }

    // Returns true if the user is already at their loan limit and cannot borrow more items.
    public boolean isAtLoanLimit () {
        return loans.size() >= getLoanLimit();
    }

    // Abstract methods that must be implemented by subclasses
    public abstract int getLoanLimit();
    public abstract int getLoanPeriodForItem(LibraryItem item);
    public abstract double getFineRateModifier();
    public abstract double getFineSuspensionLimit();
    public abstract String getUserRole();
}