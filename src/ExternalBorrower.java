public class ExternalBorrower extends LibraryUser {

    public ExternalBorrower(String name) {
        super(name);
    }

    @Override
    public int getLoanPeriodForItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (item instanceof Magazine) {
            return 14; // External borrowers get 14 days for magazines
        } else if (item instanceof DVD) {
            return 7; // External borrowers get 7 days for DVDs
        } else if (item instanceof Book) {
            return 21; // External borrowers get 21 days for books (including ReferenceOnlyBook)
        } else {
            return 7; // Default period for any other item types
        }
    }

    @Override
    public int getLoanLimit() {
        return 2; // can borrow up to 2 items
    }

    @Override
    public double getFineRateModifier() {
        return 2.0; // fine rate is double the standard rate
    }

    @Override
    public double getFineSuspensionLimit() {
        return 5; // suspended when fines reach $5
    }

    @Override
    public String getUserRole() {
        return "External Borrower";
    }
}