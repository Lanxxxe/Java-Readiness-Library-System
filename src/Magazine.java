public class Magazine extends LibraryItem {

    private int issueNumber;

    public Magazine(String title, int issueNumber) {
        super(title);
        setIssueNumber(issueNumber);
    }

    public int getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(int issueNumber) {
        if (issueNumber <= 0) {
            throw new IllegalArgumentException("Issue number must be greater than zero");
        }
        this.issueNumber = issueNumber;
    }

    @Override
    public double getDailyLateFee() {
        return 0.5; // Magazines have a daily late fee of $0.50
    }

    @Override
    public int getBaseLoanPeriod() {
        return 14; // Magazines have a base loan period of 14 days
    }

    @Override
    public double getMaximumFine() {
        return 10.0; // Magazines have a maximum fine of $10.00
    }

    @Override
    public String toString() {
        return "Magazine: " + getTitle() + " (Issue #" + issueNumber + ")";
    }
}