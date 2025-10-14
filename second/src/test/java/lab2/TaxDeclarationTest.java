package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaxDeclarationTest {

    private IncomeCertificate createTestCertificate(int year, String name, double[] income) {
        return new IncomeCertificate(year, name, "Test Company", income);
    }

    @Test
    void testConstructorWithValidParameters() {
        double[] income1 = {1000.0, 2000.0, 3000.0, 4000.0, 5000.0, 6000.0, 
                          7000.0, 8000.0, 9000.0, 10000.0, 11000.0, 12000.0};
        IncomeCertificate cert1 = createTestCertificate(2023, "John Doe", income1);
        
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert1});
        
        assertEquals(2023, declaration.getYear());
        assertEquals("John Doe", declaration.getCitizenName());
        assertArrayEquals(income1, declaration.getMonthlyIncome(), 0.001);
    }

    @Test
    void testConstructorWithMultipleCertificates() {
        double[] income1 = {1000.0, 2000.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double[] income2 = {0.0, 0.0, 3000.0, 4000.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        double[] income3 = {0.0, 0.0, 0.0, 0.0, 5000.0, 6000.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        
        IncomeCertificate cert1 = createTestCertificate(2023, "John Doe", income1);
        IncomeCertificate cert2 = createTestCertificate(2023, "John Doe", income2);
        IncomeCertificate cert3 = createTestCertificate(2023, "John Doe", income3);
        
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", 
            new IncomeCertificate[]{cert1, cert2, cert3});
        
        double[] expectedMonthlyIncome = {1000.0, 2000.0, 3000.0, 4000.0, 5000.0, 6000.0, 
                                        0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
        assertArrayEquals(expectedMonthlyIncome, declaration.getMonthlyIncome(), 0.001);
    }

    @Test
    void testConstructorWithNullCitizenName() {
        double[] income = new double[12];
        IncomeCertificate cert = createTestCertificate(2023, "John Doe", income);
        
        assertThrows(IllegalArgumentException.class, 
            () -> new TaxDeclaration(2023, null, new IncomeCertificate[]{cert}));
    }

    @Test
    void testConstructorWithYearMismatch() {
        double[] income = new double[12];
        IncomeCertificate cert = createTestCertificate(2022, "John Doe", income); 
        
        assertThrows(IllegalArgumentException.class, 
            () -> new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert}));
    }

    @Test
    void testConstructorWithCitizenNameMismatch() {
        double[] income = new double[12];
        IncomeCertificate cert = createTestCertificate(2023, "Different Name", income);
        
        assertThrows(IllegalArgumentException.class, 
            () -> new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert}));
    }

    @Test
    void testYearlyIncomeCalculation() {
        double[] income = {1000.0, 2000.0, 3000.0, 4000.0, 5000.0, 6000.0, 
                         7000.0, 8000.0, 9000.0, 10000.0, 11000.0, 12000.0};
        IncomeCertificate cert = createTestCertificate(2023, "John Doe", income);
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert});
        
        double[] yearlyIncome = declaration.getYearlyIncomeInMonth();
        
        assertEquals(1000.0, yearlyIncome[0], 0.001);  
        assertEquals(3000.0, yearlyIncome[1], 0.001);  
        assertEquals(6000.0, yearlyIncome[2], 0.001);  
        assertEquals(10000.0, yearlyIncome[3], 0.001); 
        assertEquals(15000.0, yearlyIncome[4], 0.001); 
        assertEquals(78000.0, yearlyIncome[11], 0.001); 
    }

    @Test
    void testTaxCalculationNoTaxBelowThreshold() {
        
        double[] income = {1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 
                         1000.0, 1000.0, 1000.0, 1000.0, 1000.0, 13000.0};
        IncomeCertificate cert = createTestCertificate(2023, "John Doe", income);
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert});
        
        double[] yearlyTax = declaration.getYearlyTaxInMonth();
        
        
        for (int i = 0; i < 12; i++) {
            assertEquals(0.0, yearlyTax[i], 0.001, "Month " + (i + 1) + " should have 0 tax");
        }
        assertEquals(0.0, declaration.getTotalTax(), 0.001);
    }

    @Test
    void testTaxCalculationLowRateOnly() {
        
        double[] income = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50000.0};
        IncomeCertificate cert = createTestCertificate(2023, "John Doe", income);
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert});
        
        double[] yearlyTax = declaration.getYearlyTaxInMonth();
        
        
        for (int i = 0; i < 11; i++) {
            assertEquals(0.0, yearlyTax[i], 0.001, "Month " + (i + 1) + " should have 0 tax");
        }
        
        
        assertEquals(3380.0, yearlyTax[11], 0.001);
        assertEquals(3380.0, declaration.getTotalTax(), 0.001);
    }

    @Test
    void testTaxCalculationMixedRates() {
        
        double[] income = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 300000.0};
        IncomeCertificate cert = createTestCertificate(2023, "John Doe", income);
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert});
        
        double[] yearlyTax = declaration.getYearlyTaxInMonth();
        
        
        
        
        
        assertEquals(40080.0, yearlyTax[11], 0.001);
        assertEquals(40080.0, declaration.getTotalTax(), 0.001);
    }

    @Test
    void testTaxCalculationProgressive() {
        
        double[] income = {6000.0, 6000.0, 6000.0, 8000.0, 60000.0, 60000.0, 
                         60000.0, 60000.0, 100000.0, 100000.0, 100000.0, 100000.0};
        IncomeCertificate cert = createTestCertificate(2023, "John Doe", income);
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert});
        
        double[] yearlyTax = declaration.getYearlyTaxInMonth();
        double[] cumulativeIncome = declaration.getYearlyIncomeInMonth();
        
        
        assertEquals(0.0, yearlyTax[0], 0.001); 
        assertEquals(0.0, yearlyTax[1], 0.001); 
        assertEquals(0.0, yearlyTax[2], 0.001); 
        
        
        assertEquals(260.0, yearlyTax[3], 0.001); 
        
        
        
        
        assertEquals(8060.0, yearlyTax[4], 0.001); 
        
        
        
        
        
        assertEquals(33280.0, yearlyTax[7], 0.001); 
        
        
        
        
        
        assertEquals(113280.0, yearlyTax[11], 0.001); 
        assertEquals(113280.0, declaration.getTotalTax(), 0.001);
    }

    @Test
    void testGettersReturnCopies() {
        double[] income = {1000.0, 2000.0, 3000.0, 4000.0, 5000.0, 6000.0, 
                         7000.0, 8000.0, 9000.0, 10000.0, 11000.0, 12000.0};
        IncomeCertificate cert = createTestCertificate(2023, "John Doe", income);
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{cert});
        
        
        double[] monthlyIncome = declaration.getMonthlyIncome();
        monthlyIncome[0] = 9999.0; 
        
        double[] originalMonthlyIncome = declaration.getMonthlyIncome();
        assertEquals(1000.0, originalMonthlyIncome[0], 0.001); 
    }

    @Test
    void testEmptyCertificates() {
        
        assertDoesNotThrow(() -> new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{}));
        
        TaxDeclaration declaration = new TaxDeclaration(2023, "John Doe", new IncomeCertificate[]{});
        
        double[] monthlyIncome = declaration.getMonthlyIncome();
        for (double income : monthlyIncome) {
            assertEquals(0.0, income, 0.001);
        }
        
        double[] yearlyIncome = declaration.getYearlyIncomeInMonth();
        for (double income : yearlyIncome) {
            assertEquals(0.0, income, 0.001);
        }
        
        double[] yearlyTax = declaration.getYearlyTaxInMonth();
        for (double tax : yearlyTax) {
            assertEquals(0.0, tax, 0.001);
        }
        
        assertEquals(0.0, declaration.getTotalTax(), 0.001);
    }
}
