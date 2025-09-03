public class Loan {

    private LibraryItem libraryItem;
    private LibraryUser user;
    private DateManager dateManager;
    private int checkoutDay;
    private int returnDay;

    // Loan constructor.
    public Loan(LibraryItem libraryItem, LibraryUser user, DateManager dateManager) {
        if (libraryItem == null) {
            throw new IllegalArgumentException("Library item cannot be null");
        }
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (dateManager == null) {
            throw new IllegalArgumentException("Date manager cannot be null");
        }

        this.libraryItem = libraryItem;
        this.user = user;
        this.dateManager = dateManager;
        this.checkoutDay = dateManager.getCurrentDay();

        // Calculate return day based on item's base loan period and user's loan period
        int baseLoanPeriod = libraryItem.getBaseLoanPeriod();
        int userLoanPeriod = user.getLoanPeriodForItem(libraryItem);
        // Use the longer of the two periods
        int totalLoanPeriod = Math.max(baseLoanPeriod, userLoanPeriod);
        this.returnDay = checkoutDay + totalLoanPeriod;
    }

    // This processes the loan, recording the loan against the item and against the user. This should throw appropriate
    // exceptions if the loan cannot be processed (e.g., if a user is already at their loan limit). Note: You do not
    // need to throw the errors from this method specifically, as long as the errors are thrown when the method is called.
    public void processLoan() {
        // Assign the loan to both the user and the item
        // These methods will throw appropriate exceptions if the loan cannot be processed
        user.assignLoan(this);
        libraryItem.assignLoan(this);
    }

    // This handles the logic for returning the item. This will remove the record of the loan from both the user
    // and the item.
    public void processReturn () {
        user.removeLoan(this);
        libraryItem.removeLoan();
    }

    // Should return true if the item is currently overdue. An item is not overdue until the current day
    // exceeds the return day.
    public boolean isOverdue() {
        return dateManager.getCurrentDay() > returnDay;
    }

    // Should return the current value of the fine. If the item currently hasn't accrued a fine because it is not
    // yet overdue, this should return 0.
    public double getLoanFine () {
        if (!isOverdue()) {
            return 0.0;
        }

        int overdueDays = getOverdueDays();
        double dailyFee = libraryItem.getDailyLateFee();
        double fineRateModifier = user.getFineRateModifier();
        double maxFine = libraryItem.getMaximumFine();

        // Calculate fine: overdue days * daily fee * user's fine rate modifier
        double calculatedFine = overdueDays * dailyFee * fineRateModifier;

        // Cap the fine at the maximum fine for the item
        return Math.min(calculatedFine, maxFine);
    }

    // Should return the current number of days that the item is overdue (or zero if not yet overdue).
    public int getOverdueDays() {
        if (!isOverdue()) {
            return 0;
        }
        return dateManager.getCurrentDay() - returnDay;
    }

    // Should return the day that the library item was borrowed.
    public int getCheckoutDay() {
        return checkoutDay;
    }

    // Returns the day at which the item needs to be returned before fines begin.
    public int getReturnDay() {
        return returnDay;
    }

    // Returns the user that took out this loan.
    public LibraryUser getUser() {
        return user;
    }

    // Returns the library item that was borrowed.
    public LibraryItem getLibraryItem() {
        return libraryItem;
    }

    // Alias method to match requirement naming
    public int getCheckoutData() {
        return getCheckoutDay();
    }

    // Alias method to match requirement naming
    public int countDaysPastDue() {
        return getOverdueDays();
    }
}