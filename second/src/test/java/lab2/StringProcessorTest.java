package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringProcessorTest {
    @Test
    public void repeat() {
        assertEquals("aa", StringProcessor.repeat("a", 2));
        assertEquals("abab", StringProcessor.repeat("ab", 2));
        assertEquals("ababab", StringProcessor.repeat("ab", 3));
        assertEquals("", StringProcessor.repeat("a", 0));
        assertEquals("", StringProcessor.repeat("", 10));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.repeat("a", -1));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.repeat("a", null));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.repeat(null, 0));
    }

    @Test
    public void count() {
        assertEquals(2, StringProcessor.count("aa", "a"));
        assertEquals(2, StringProcessor.count("aaa", "aa"));
        assertEquals(0, StringProcessor.count("aaa", "b"));
        assertEquals(1, StringProcessor.count("abaa", "ab"));
        assertEquals(4, StringProcessor.count("ababababa", "aba"));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.count("a", null));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.count(null, "b"));
    }

    @Test
    public void convertToNumberWords() {
        assertEquals("один", StringProcessor.convertToNumberWords("1"));
        assertEquals("одиндва", StringProcessor.convertToNumberWords("12"));
        assertEquals("одиндватри", StringProcessor.convertToNumberWords("123"));
        assertEquals("одиндватри4", StringProcessor.convertToNumberWords("1234"));
        assertEquals("одиндватри", StringProcessor.convertToNumberWords("один2три"));
        assertEquals("одиндватри", StringProcessor.convertToNumberWords("одиндватри"));
        assertEquals("", StringProcessor.convertToNumberWords(""));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.convertToNumberWords(null));
    }

    @Test
    public void removeEvenCharacters() {
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.removeEvenCharacters(null));
        StringBuilder sb = new StringBuilder("hello12345678world");
        StringProcessor.removeEvenCharacters(sb);
        assertEquals("hlo2468ol", sb.toString());
        sb = new StringBuilder("");
        StringProcessor.removeEvenCharacters(sb);
        assertEquals("", sb.toString());
        sb = new StringBuilder("a");
        StringProcessor.removeEvenCharacters(sb);
        assertEquals("a", sb.toString());
        sb = new StringBuilder("ab");
        StringProcessor.removeEvenCharacters(sb);
        assertEquals("a", sb.toString());
        sb = new StringBuilder("abc");
        StringProcessor.removeEvenCharacters(sb);
        assertEquals("ac", sb.toString());
    }

    @Test
    public void reverseWords() {
        assertEquals("1 2 3", StringProcessor.reverseWords("3 2 1"));
        assertEquals("  dd  cc bbb aaa", StringProcessor.reverseWords("  aaa  bbb cc dd"));
        assertEquals("", StringProcessor.reverseWords(""));
        assertEquals("bbb  aaa", StringProcessor.reverseWords("aaa  bbb"));
        assertEquals("   world hello ", StringProcessor.reverseWords("   hello world "));
        assertEquals("hello", StringProcessor.reverseWords("hello"));
        assertEquals("  hello  ", StringProcessor.reverseWords("  hello  "));
        assertEquals("d c  b   a", StringProcessor.reverseWords("a b  c   d"));
        assertEquals("   ", StringProcessor.reverseWords("   "));
        assertEquals("c b a", StringProcessor.reverseWords("a b c"));
        assertEquals("  c  b  a  ", StringProcessor.reverseWords("  a  b  c  "));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.reverseWords(null));
    }

    @Test
    public void replaceAllHexToDecimal() {
        assertEquals("1 2 3", StringProcessor.replaceAllHexToDecimal("1 0x2 0x3"));
        assertEquals("1 2 3", StringProcessor.replaceAllHexToDecimal("1 0x2 0x3"));
        assertEquals("Васе 16 лет", StringProcessor.replaceAllHexToDecimal("Васе 0x00000010 лет"));
        assertEquals("16", StringProcessor.replaceAllHexToDecimal("0x10"));
        assertEquals("The value is 255", StringProcessor.replaceAllHexToDecimal("The value is 0xFF"));
        assertEquals("26 and 43", StringProcessor.replaceAllHexToDecimal("0x1A and 0x2B"));
        assertEquals("2748", StringProcessor.replaceAllHexToDecimal("0xabc"));
        assertEquals("2748", StringProcessor.replaceAllHexToDecimal("0xABC"));
        assertEquals("10597059", StringProcessor.replaceAllHexToDecimal("0xA1b2C3"));
        assertEquals("16 is a number", StringProcessor.replaceAllHexToDecimal("0x10 is a number"));
        assertEquals("Number: 16", StringProcessor.replaceAllHexToDecimal("Number: 0x10"));
        assertEquals("This is just text", StringProcessor.replaceAllHexToDecimal("This is just text"));
        assertEquals("", StringProcessor.replaceAllHexToDecimal(""));
        assertEquals("291", StringProcessor.replaceAllHexToDecimal("0x123"));
        assertEquals("255", StringProcessor.replaceAllHexToDecimal("0x00FF"));
        assertEquals("4294967295", StringProcessor.replaceAllHexToDecimal("0xFFFFFFFF"));
        assertEquals("0", StringProcessor.replaceAllHexToDecimal("0x0"));
        assertEquals("0b0", StringProcessor.replaceAllHexToDecimal("0b0"));
        assertEquals("0x and 0xG", StringProcessor.replaceAllHexToDecimal("0x and 0xG"));
        assertEquals("Error code: 3735928559", StringProcessor.replaceAllHexToDecimal("Error code: 0xDEADBEEF"));
        assertEquals("16 32 48", StringProcessor.replaceAllHexToDecimal("0x10 0x20 0x30"));
        assertThrows(IllegalArgumentException.class, () -> StringProcessor.replaceAllHexToDecimal(null));
    }
}
