package lab2;

public class IncomeCertificate {
    private int year;
    private String citizenName;
    private String organizationName;
    private double[] monthlyIncome;

    private static final int NUMBER_OF_MONTHS = 12;

    public IncomeCertificate
    (int year, String citizenName, String organizationName, double[] monthlyIncome)
    throws IllegalArgumentException {
        this.year = year;
        if (citizenName == null) {
            throw new IllegalArgumentException("Citizen name is null");
        }
        this.citizenName = citizenName;
        if (organizationName == null) {
            throw new IllegalArgumentException("Organization name is null");
        }
        this.organizationName = organizationName;
        if (monthlyIncome == null) {
            throw new IllegalArgumentException("Monthly income is null");
        }
        if (monthlyIncome.length != NUMBER_OF_MONTHS) {
            throw new IllegalArgumentException("Monthly income length is not 12");
        }
        this.monthlyIncome = new double[NUMBER_OF_MONTHS];
        System.arraycopy(monthlyIncome, 0, this.monthlyIncome, 0, NUMBER_OF_MONTHS);
    }

	public int getYear() {
		return year;
	}
	public String getCitizenName() {
		return citizenName;
	}
	public String getOrganizationName() {
		return organizationName;
	}
	public double[] getMonthlyIncome() {
        double[] copy = new double[monthlyIncome.length];
        System.arraycopy(monthlyIncome, 0, copy, 0, monthlyIncome.length);
        return copy;
	}

    @Override()
    public String toString() {
        String[][] table = new String[NUMBER_OF_MONTHS + 1][2];
        int[] widths = {1, 1};
        String[] header = {"Месяц", "Сумма"};
        table[0] = header;
        for (int i = 0; i < NUMBER_OF_MONTHS; i++) {
            table[i+1][0] = String.format("%d", i+1);
            widths[0] = Math.max(widths[0], table[i][0].length());
            table[i+1][1] = String.format("%.2f", monthlyIncome[i]);
            widths[1] = Math.max(widths[1], table[i][1].length());
        }
        String[] rows = new String[NUMBER_OF_MONTHS+1];
        for (int i = 0; i <= NUMBER_OF_MONTHS; i++) {
            rows[i] = String.format(
                "| %-" + widths[0] + "s | %-" + widths[1] + "s |",
                table[i][0],
                table[i][1]
            );
        }
        String hr = StringProcessor.repeat("-", widths[0] + widths[1] + 9) + "\n";
        String tablestring = hr + String.join("\n", rows) + "\n" + hr;
        return String.format("Год: %d\nГражданин: %s\nОрганизация: %s\nПолученный доход и начисленный налог:\n",
            year, citizenName, organizationName
        ) + tablestring;
    }
}
