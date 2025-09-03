public class Student extends LibraryUser {
    public Student(String name) {
        super(name);
    }

    @Override
    public int getLoanPeriodForItem(LibraryItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if (item instanceof Magazine) {
            return 21; // Students get 21 days for magazines
        } else if (item instanceof DVD) {
            return 14; // Students get 14 days for DVDs
        } else if (item instanceof Book) {
            return 28; // Students get 28 days for books (including ReferenceOnlyBook)
        } else {
            return 7; // Default period for any other item types
        }
    }

    @Override
    public int getLoanLimit() {
        return 5; // Students can borrow up to 5 items
    }


    @Override
    public double getFineRateModifier() {
        return 0.75; // Students pay standard fine rate (no modifier)
    }

    @Override
    public double getFineSuspensionLimit() {
        return 10.0; // Students are suspended when fines reach $10
    }

    @Override
    public String getUserRole() {
        return "Student";
    }
}