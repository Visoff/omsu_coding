package lab2;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringProcessorTest {
    @Test
    public void repeat() {
        assertEquals("aa", StringProcessor.repeat("a", 2));
        assertEquals("abab", StringProcessor.repeat("ab", 2));
        assertEquals("ababab", StringProcessor.repeat("ab", 3));
    }

    @Test
    public void count() {
        assertEquals(2, StringProcessor.count("aa", "a"));
        assertEquals(3, StringProcessor.count("aaa", "a"));
        assertEquals(0, StringProcessor.count("aaa", "b"));
        assertEquals(1, StringProcessor.count("abaa", "ab"));
    }

    @Test
    public void convertToNumberWords() {
        assertEquals("один", StringProcessor.convertToNumberWords("1"));
        assertEquals("одиндва", StringProcessor.convertToNumberWords("12"));
        assertEquals("одиндватри", StringProcessor.convertToNumberWords("123"));
        assertEquals("одиндватри4", StringProcessor.convertToNumberWords("1234"));
    }

    @Test
    public void removeEvenCharacters() {
        StringBuilder sb = new StringBuilder("12345678");
        StringProcessor.removeEvenCharacters(sb);
        assertEquals("1357", sb.toString());
    }

    @Test
    public void reverseWords() {
        assertEquals("1 2 3", StringProcessor.reverseWords("3 2 1"));
        assertEquals("  dd  cc bbb aaa", StringProcessor.reverseWords("  aaa  bbb cc dd"));
    }

    @Test
    public void replaceAllHexToDecimal() {
        assertEquals("1 2 3", StringProcessor.replaceAllHexToDecimal("1 0x2 0x3"));
        assertEquals("1 2 3", StringProcessor.replaceAllHexToDecimal("1 0x2 0x3"));
        assertEquals("Васе 16 лет", StringProcessor.replaceAllHexToDecimal("Васе 0x00000010 лет"));
    }
}
