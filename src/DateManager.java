public class DateManager {
    private int currentDay;

    public int getCurrentDay() {
        return currentDay;
    }

    public void advanceDays (int days) {
        currentDay += days;
    }
}
