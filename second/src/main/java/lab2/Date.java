package lab2;

public class Date {
    private int day, month, year;

    static final int MIN_MONTH = 1;
    static final int MAX_MONTH = 12;
    static final int MIN_DAY = 1;
    static final int MAX_DAY = 31;
    static final int DAYS_IN_MONTH[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    static final int FEBRUARY = 2;
    static final int FEBRUARY_LEAP_YEAR = 29;

    private static int max_day_in_month(int month, int year) {
        final int four = 4;
        final int hundred = 100;
        if (
                month == FEBRUARY &&
                year % four == 0 &&
                year % hundred != 0 ||
                year % (four * hundred) == 0
            ) {
            return DAYS_IN_MONTH[FEBRUARY - 1] + 1;
        }
        return DAYS_IN_MONTH[month - 1];
    }

    public Date(int day, int month, int year) throws IllegalArgumentException {
        this.year = year;
        if (month < MIN_MONTH || month > MAX_MONTH) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        this.month = month;
        if (day < MIN_DAY || day > max_day_in_month(month, year)) {
            throw new IllegalArgumentException("Illegal day");
        }
        this.day = day;
    }

    public Date(Date d) {
        this.day = d.day;
        this.month = d.month;
        this.year = d.year;
    }

    @Override
    public String toString() {
        return String.format("%02d.%02d.%04d", day, month, year);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Date) {
            Date d = (Date)obj;
            return d.day == day && d.month == month && d.year == year;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return day + month + year;
    }

	public int getDay() {
		return day;
	}
	public int getMonth() {
		return month;
	}
	public int getYear() {
		return year;
	}
}
