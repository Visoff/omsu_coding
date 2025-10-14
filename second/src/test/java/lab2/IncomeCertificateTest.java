package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class IncomeCertificateTest {

    @Test
    void testConstructorWithValidParameters() {
        double[] monthlyIncome = {1000.0, 2000.0, 3000.0, 4000.0, 5000.0, 6000.0, 
                                 7000.0, 8000.0, 9000.0, 10000.0, 11000.0, 12000.0};
        
        IncomeCertificate certificate = new IncomeCertificate(2023, "John Doe", "Company Inc", monthlyIncome);
        
        assertEquals(2023, certificate.getYear());
        assertEquals("John Doe", certificate.getCitizenName());
        assertEquals("Company Inc", certificate.getOrganizationName());
        assertArrayEquals(monthlyIncome, certificate.getMonthlyIncome(), 0.001);
    }

    @Test
    void testConstructorWithNullCitizenName() {
        double[] monthlyIncome = new double[12];
        assertThrows(IllegalArgumentException.class, 
            () -> new IncomeCertificate(2023, null, "Company Inc", monthlyIncome));
    }

    @Test
    void testConstructorWithNullOrganizationName() {
        double[] monthlyIncome = new double[12];
        assertThrows(IllegalArgumentException.class, 
            () -> new IncomeCertificate(2023, "John Doe", null, monthlyIncome));
    }

    @Test
    void testConstructorWithNullMonthlyIncome() {
        assertThrows(IllegalArgumentException.class, 
            () -> new IncomeCertificate(2023, "John Doe", "Company Inc", null));
    }

    @Test
    void testConstructorWithInvalidMonthlyIncomeLength() {
        double[] monthlyIncome = new double[10]; 
        assertThrows(IllegalArgumentException.class, 
            () -> new IncomeCertificate(2023, "John Doe", "Company Inc", monthlyIncome));
    }

    @Test
    void testConstructorWithEmptyMonthlyIncome() {
        double[] monthlyIncome = new double[12]; 
        assertDoesNotThrow(() -> new IncomeCertificate(2023, "John Doe", "Company Inc", monthlyIncome));
    }

    @Test
    void testGettersReturnCorrectValues() {
        double[] monthlyIncome = {100.0, 200.0, 300.0, 400.0, 500.0, 600.0, 
                                 700.0, 800.0, 900.0, 1000.0, 1100.0, 1200.0};
        
        IncomeCertificate certificate = new IncomeCertificate(2024, "Alice Smith", "Tech Corp", monthlyIncome);
        
        assertEquals(2024, certificate.getYear());
        assertEquals("Alice Smith", certificate.getCitizenName());
        assertEquals("Tech Corp", certificate.getOrganizationName());
        
        double[] returnedIncome = certificate.getMonthlyIncome();
        assertArrayEquals(monthlyIncome, returnedIncome, 0.001);
        assertEquals(12, returnedIncome.length);
    }

    @Test
    void testMonthlyIncomeArrayIsCopied() {
        double[] originalIncome = new double[12];
        originalIncome[0] = 1000.0;
        
        IncomeCertificate certificate = new IncomeCertificate(2023, "Test", "Test", originalIncome);
        
        
        originalIncome[0] = 9999.0;
        
        assertEquals(1000.0, certificate.getMonthlyIncome()[0], 0.001);
    }
}
