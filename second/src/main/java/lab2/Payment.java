package lab2;

public class Payment {
    private String fio;
    private Integer day, month, year;
    private Double sum;

    public Payment(String fio, Integer day, Integer month, Integer year, Double sum) throws IllegalArgumentException {
        if (fio == null || day == null || month == null || year == null || sum == null) throw new IllegalArgumentException();
        this.fio = fio;
        if (month < 1 || month > 12) throw new IllegalArgumentException();
        this.month = month;
        if (day < 1 || day > 31) throw new IllegalArgumentException();
        this.day = day;
        this.year = year;
        if (sum < 0) throw new IllegalArgumentException();
        this.sum = sum;
    }

    public Payment() {}

    public Payment(Payment p) {
        this.fio = p.fio;
        this.day = p.day;
        this.month = p.month;
        this.year = p.year;
        this.sum = p.sum;
    }

	public String getFio() {
		return fio;
	}
	public void setFio(String fio) {
		this.fio = fio;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Double getSum() {
		return sum;
	}
	public void setSum(Double sum) {
		this.sum = sum;
	}

    public boolean equals(Payment p) {
        return fio.equals(p.fio) && day.equals(p.day) && month.equals(p.month) && year.equals(p.year) && sum.equals(p.sum);
    }

    public int hashCode() {
        return fio.hashCode() + day.hashCode() + month.hashCode() + year.hashCode() + sum.hashCode();
    }

    public String toString() {
        return String.format("Плательщик: %s, дата: %d.%d.%d, сумма: %d руб. %02d коп.", fio, day, month, year, sum.intValue(), Double.valueOf((sum * 100.0) % 100.0).intValue());
    }
}
