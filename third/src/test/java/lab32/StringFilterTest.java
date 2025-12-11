package lab32;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StringFilterTest {

    @Test
    void testBeginStringFilter() {
        Filter filter = new BeginStringFilter("hello");

        assertTrue(filter.apply("hello world"));
        assertTrue(filter.apply("hello"));
        assertFalse(filter.apply("world hello"));
        assertFalse(filter.apply("helo"));
        assertFalse(filter.apply(""));
        assertThrows(NullPointerException.class, () -> filter.apply(null));

        Filter emptyFilter = new BeginStringFilter("");
        assertTrue(emptyFilter.apply("any string"));
        assertTrue(emptyFilter.apply(""));
        assertThrows(NullPointerException.class, () -> emptyFilter.apply(null));
    }

    @Test
    void testContainsStringFilter() {
        Filter filter = new ContainsStringFilter("world");

        assertTrue(filter.apply("hello world"));
        assertTrue(filter.apply("world"));
        assertTrue(filter.apply("world hello"));
        assertFalse(filter.apply("hello"));
        assertFalse(filter.apply(""));
        assertThrows(NullPointerException.class, () -> filter.apply(null));

        Filter emptyFilter = new ContainsStringFilter("");
        assertTrue(emptyFilter.apply("any string"));
        assertTrue(emptyFilter.apply(""));
        assertThrows(NullPointerException.class, () -> emptyFilter.apply(null));
    }

    @Test
    void testEndStringFilter() {
        Filter filter = new EndStringFilter("world");

        assertTrue(filter.apply("hello world"));
        assertTrue(filter.apply("world"));
        assertFalse(filter.apply("world hello"));
        assertFalse(filter.apply("woorld"));
        assertFalse(filter.apply(""));
        assertThrows(NullPointerException.class, () -> filter.apply(null));

        Filter emptyFilter = new EndStringFilter("");
        assertTrue(emptyFilter.apply("any string"));
        assertTrue(emptyFilter.apply(""));
        assertThrows(NullPointerException.class, () -> emptyFilter.apply(null));
    }
}
