public class ReferenceOnlyBook extends Book {
    public ReferenceOnlyBook(String title, String isbn) {
        super(title, isbn);
    }

    @Override
    public void assignLoan(Loan loan) {
        throw new ItemNotLoanableException("Reference books cannot be loaned");
    }

    @Override
    public boolean isAvailableForLoan() {
        return false; // Reference books are never available for loan
    }

    @Override
    public int getBaseLoanPeriod() {
        return 0; // Reference books have no loan period as they cannot be loaned
    }

    @Override
    public double getDailyLateFee() {
        return 0.0; // Reference books have no late fees as they cannot be loaned
    }

    @Override
    public double getMaximumFine() {
        return 0.0; // Reference books have no maximum fine as they cannot be loaned
    }

    @Override
    public String toString() {
        return getTitle() + " (Reference Book, ISBN: " + getISBN() + ")";
    }
}