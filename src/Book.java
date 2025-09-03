public class Book extends LibraryItem {

    private String isbn;

    public Book(String title, String isbn) {
        super(title);
        setISBN(isbn);
    }

    public String getISBN() {
        return isbn;
    }

    public void setISBN(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        this.isbn = isbn.trim();
    }

    @Override
    public double getDailyLateFee() {
        return 0.25; // Books have a daily late fee of $0.25
    }

    @Override
    public int getBaseLoanPeriod() {
        return 21; // Books have a base loan period of 21 days
    }

    @Override
    public double getMaximumFine() {
        return 20.0; // Books have a maximum fine of $20.00
    }

    @Override
    public String toString() {
        return "Book: " + getTitle() + " (ISBN: " + isbn + ")";
    }
}