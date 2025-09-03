public class Academic extends LibraryUser {

    public Academic(String name) {
        super(name);
    }

    @Override
    public int getLoanPeriodForItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (item instanceof Magazine) {
            return 28; // Academics get 28 days for magazines
        } else if (item instanceof DVD) {
            return 21; // Academics get 21 days for DVDs
        } else if (item instanceof Book) {
            return 35; // Academics get 35 days for books (including ReferenceOnlyBook)
        } else {
            return 14; // Default period for any other item types
        }
    }

    @Override
    public int getLoanLimit() {
        return 10; // Academics can borrow up to 10 items
    }

    @Override
    public double getFineRateModifier() {
        return 1.0; // Academics pay the standard fine rate
    }

    @Override
    public double getFineSuspensionLimit() {
        return 20.0; // Academics are suspended when fines reach $20
    }

    @Override
    public String getUserRole() {
        return "Academic";
    }
}