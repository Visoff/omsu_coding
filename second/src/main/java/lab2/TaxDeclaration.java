package lab2;

import java.util.Objects;

public class TaxDeclaration {
    private int year;
    private String citizenName;
    private double[] monthlyIncome;
    private double[] yearlyIncomeInMonth;
    private double[] yearlyTaxInMonth;

    private static final int NUMBER_OF_MONTHS = 12;
    private static final double LOW_TAX_THRESHOLD = 24_000.0;
    private static final double HIGH_TAX_THRESHOLD = 240_000.0;
    private static final double LOW_TAX_RATE = 0.13;
    private static final double HIGH_TAX_RATE = 0.2;

    public TaxDeclaration
    (int year, String citizenName, IncomeCertificate[] incomeCertificates)
    throws IllegalArgumentException {
        this.year = year;
        if (citizenName == null) {
            throw new IllegalArgumentException("Citizen name is null");
        }
        this.citizenName = citizenName;
        this.monthlyIncome = new double[NUMBER_OF_MONTHS];
        this.yearlyIncomeInMonth = new double[NUMBER_OF_MONTHS];
        this.yearlyTaxInMonth = new double[NUMBER_OF_MONTHS];
        for (IncomeCertificate certificate : incomeCertificates) {
            if (certificate.getYear() != year) {
                throw new IllegalArgumentException("Incorrect year");
            }
            if (!Objects.equals(certificate.getCitizenName(), citizenName)) {
                throw new IllegalArgumentException("Incorrect citizen name");
            }
            for (int i = 0; i < NUMBER_OF_MONTHS; i++) {
                monthlyIncome[i] += certificate.getMonthlyIncome()[i];
            }
        }
        yearlyIncomeInMonth[0] = monthlyIncome[0];
        for (int i = 1; i < NUMBER_OF_MONTHS; i++) {
            yearlyIncomeInMonth[i] = yearlyIncomeInMonth[i-1] + monthlyIncome[i];
        }
        for (int i = 0; i < NUMBER_OF_MONTHS; i++) {
            if (this.yearlyIncomeInMonth[i] < LOW_TAX_THRESHOLD) {
                continue;
            }
            this.yearlyTaxInMonth[i] =
                (
                 Math.min(this.yearlyIncomeInMonth[i], HIGH_TAX_THRESHOLD) - LOW_TAX_THRESHOLD
                ) * LOW_TAX_RATE;
            if (this.yearlyIncomeInMonth[i] < HIGH_TAX_THRESHOLD) {
                continue;
            }
            this.yearlyTaxInMonth[i] +=
                (this.yearlyIncomeInMonth[i] - HIGH_TAX_THRESHOLD) * HIGH_TAX_RATE;
        }
    }

	public int getYear() {
		return year;
	}
	public String getCitizenName() {
		return citizenName;
	}
	public double[] getMonthlyIncome() {
        double[] result = new double[NUMBER_OF_MONTHS];
        System.arraycopy(monthlyIncome, 0, result, 0, NUMBER_OF_MONTHS);
        return result;
	}
	public double[] getYearlyIncomeInMonth() {
        double[] result = new double[NUMBER_OF_MONTHS];
        System.arraycopy(yearlyIncomeInMonth, 0, result, 0, NUMBER_OF_MONTHS);
        return result;
	}
	public double[] getYearlyTaxInMonth() {
        double[] result = new double[NUMBER_OF_MONTHS];
        System.arraycopy(yearlyTaxInMonth, 0, result, 0, NUMBER_OF_MONTHS);
        return result;
	}
    public double getTotalTax() {
        return yearlyTaxInMonth[NUMBER_OF_MONTHS-1];
    }

    @Override()
    public String toString() {
        String[][] table = new String[NUMBER_OF_MONTHS + 1][4];
        int[] widths = {1, 1, 1, 1};
        String[] header = {"Месяц", "Доход за месяц", "Доход за год", "Суммарный налог"};
        table[0] = header;
        for (int i = 0; i < NUMBER_OF_MONTHS; i++) {
            table[i+1][0] = String.format("%d", i+1);
            widths[0] = Math.max(widths[0], table[i][0].length());
            table[i+1][1] = String.format("%.2f", monthlyIncome[i]);
            widths[1] = Math.max(widths[1], table[i][1].length());
            table[i+1][2] = String.format("%.2f", yearlyIncomeInMonth[i]);
            widths[2] = Math.max(widths[2], table[i][2].length());
            table[i+1][3] = String.format("%.2f", yearlyTaxInMonth[i]);
            widths[3] = Math.max(widths[3], table[i][3].length());
        }
        String[] rows = new String[NUMBER_OF_MONTHS+1];
        for (int i = 0; i <= NUMBER_OF_MONTHS; i++) {
            rows[i] = String.format(
                "| %-" + widths[0] + "s | %-" + widths[1] + "s | %-" + widths[2] + "s | %-" + widths[3] + "s |",
                table[i][0],
                table[i][1],
                table[i][2],
                table[i][3]
            );
        }
        String hr = StringProcessor.repeat("-", widths[0] + widths[1] + widths[2] + widths[3] + 13) + "\n";
        String tablestring = hr + String.join("\n", rows) + "\n" + hr;
        return String.format("Год: %d\nГражданин: %s\nПолученный доход и начисленный налог:\n",
            year, citizenName
        ) + tablestring;
    }
}
