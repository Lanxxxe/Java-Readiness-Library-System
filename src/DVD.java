public class DVD extends LibraryItem {

    private int runtimeMinutes;

    public DVD(String title, int runtimeMinutes) {
        super(title);
        setRuntimeLength(runtimeMinutes);
    }

    public int getRuntimeLength() {
        return runtimeMinutes;
    }

    public void setRuntimeLength(int runtimeMinutes) {
        if (runtimeMinutes <= 0) {
            throw new IllegalArgumentException("Runtime must be greater than zero");
        }
        this.runtimeMinutes = runtimeMinutes;
    }

    @Override
    public double getDailyLateFee() {
        return 1.00; // DVDs have a daily late fee of $1.00
    }

    @Override
    public int getBaseLoanPeriod() {
        return 7; // DVDs have a base loan period of 7 days
    }

    @Override
    public double getMaximumFine() {
        return 30.0; // DVDs have a maximum fine of $30.00
    }

    @Override
    public String toString() {
        return "DVD: " + getTitle() + " (" + runtimeMinutes + " minutes)";
    }
}