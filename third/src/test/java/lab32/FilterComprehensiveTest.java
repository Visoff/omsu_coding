package lab32;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FilterComprehensiveTest {

    @Test
    public void testBeginStringFilterEdgeCases() {
        BeginStringFilter filter = new BeginStringFilter("test");
        
        // Exact match
        assertTrue(filter.apply("test"));
        
        // Longer string that begins with pattern
        assertTrue(filter.apply("test123"));
        assertTrue(filter.apply("test with spaces"));
        
        // Does not begin with pattern
        assertFalse(filter.apply("not test"));
        assertFalse(filter.apply("TEST")); // case sensitive
        assertFalse(filter.apply(""));
        
        // Pattern longer than string
        assertFalse(filter.apply("tes"));
    }

    @Test
    public void testBeginStringFilterWithEmptyPattern() {
        BeginStringFilter emptyFilter = new BeginStringFilter("");
        
        // Empty pattern should match everything
        assertTrue(emptyFilter.apply(""));
        assertTrue(emptyFilter.apply("any string"));
        assertTrue(emptyFilter.apply("123"));
    }

    @Test
    public void testEndStringFilterEdgeCases() {
        EndStringFilter filter = new EndStringFilter("end");
        
        // Exact match
        assertTrue(filter.apply("end"));
        
        // Longer string that ends with pattern
        assertTrue(filter.apply("the end"));
        assertTrue(filter.apply("123end"));
        
        // Does not end with pattern
        assertFalse(filter.apply("end ")); // space at end
        assertFalse(filter.apply("END")); // case sensitive
        assertFalse(filter.apply("en"));
        
        // Pattern longer than string
        assertFalse(filter.apply("nd"));
    }

    @Test
    public void testEndStringFilterWithEmptyPattern() {
        EndStringFilter emptyFilter = new EndStringFilter("");
        
        // Empty pattern should match everything
        assertTrue(emptyFilter.apply(""));
        assertTrue(emptyFilter.apply("any string"));
        assertTrue(emptyFilter.apply("123"));
    }

    @Test
    public void testFiltersWithNullInput() {
        BeginStringFilter beginFilter = new BeginStringFilter("pattern");
        EndStringFilter endFilter = new EndStringFilter("pattern");
        
        assertThrows(NullPointerException.class, () -> beginFilter.apply(null));
        assertThrows(NullPointerException.class, () -> endFilter.apply(null));
    }

    @Test
    public void testFiltersWithSpecialCharacters() {
        BeginStringFilter beginFilter = new BeginStringFilter("$pecial");
        EndStringFilter endFilter = new EndStringFilter("end!");
        
        assertTrue(beginFilter.apply("$pecial chars"));
        assertFalse(beginFilter.apply("special chars")); // no dollar sign
        
        assertTrue(endFilter.apply("this is the end!"));
        assertFalse(endFilter.apply("this is the end")); // no exclamation
    }

    @Test
    public void testFiltersWithUnicode() {
        BeginStringFilter beginFilter = new BeginStringFilter("中文");
        EndStringFilter endFilter = new EndStringFilter("end");
        
        assertTrue(beginFilter.apply("中文测试"));
        assertFalse(beginFilter.apply("测试中文"));
        
        assertTrue(endFilter.apply("中文end"));
        assertFalse(endFilter.apply("end中文"));
    }
}
