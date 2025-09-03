public abstract class LibraryItem {

    private String title;
    private Loan currentLoan;

    // LibraryItem Constructor.
    public LibraryItem(String title) {
        setTitle(title);
    }

    //  Getter method for the item's title.
    public String getTitle() {
        return title;
    }

    // Setter method for the item's title. Should throw an appropriate exception if the title is not valid.
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title.trim();
    }

    // Returns the current loan information for the item, or null if not currently loaned.
    public Loan getCurrentLoan() {
        return currentLoan;
    }

    // Assigns the specified loan to the item. This method should perform appropriate validation checks to ensure
    // that the loan is valid for the item, and throw appropriate exceptions as needed.
    public void assignLoan(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }

        if (isOnLoan()) {
            throw new ItemUnavailableException("Item is already on loan");
        }

        if (loan.getLibraryItem() != this) {
            throw new IllegalArgumentException("Loan is not for this library item");
        }

        this.currentLoan = loan;
    }

    // Removes the specified loan from the user. Should throw an appropriate exception if the loan is not valid.
    public void removeLoan() {
        if (!isOnLoan()) {
            throw new IllegalArgumentException("Item is not currently on loan");
        }

        this.currentLoan = null;
    }

    // Returns true if currently loaned, otherwise false.
    public boolean isOnLoan() {
        return currentLoan != null;
    }

    // Returns true if currently loaned, otherwise false.
    public boolean isAvailableForLoan() {
        return !isOnLoan();
    }

    // Returns the specific daily late fee for the library item (use the table provided).
    // Use the table provided in the assignment documentation to generate the values for the daily late fee.
    public abstract double getDailyLateFee ();

    // Returns the base loan period for the item (before any additional time due to user type is considered).
    // Use the table provided in the assignment documentation to generate the values for the base loan period.
    public abstract int getBaseLoanPeriod();

    // Returns the maximum possible fine for an overdue loan on this item.
    // Use the table provided in the assignment documentation to generate the values for the maximum fine.
    public abstract double getMaximumFine();
}