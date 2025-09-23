package lab2;

public class Payment {
    private String fio;
    private Integer day, month, year;
    private Double sum;

    public Payment(String fio, Integer day, Integer month, Integer year, Double sum) {
        this.fio = fio;
        this.day = day;
        this.month = month;
        this.year = year;
        this.sum = sum;
    }

    public Payment() {}

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
        return "Плательщик: " + fio + ", дата: " + day + "." + month + "." + year + ", сумма: " + (sum.intValue()) + " руб. " + Double.valueOf((sum * 100.0) % 100.0).intValue() + " коп.";
    }
}
