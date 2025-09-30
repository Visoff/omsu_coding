package lab2;

public class StringProcessor {
    public static String repeat(String input, Integer n) throws IllegalArgumentException {
        if (input == null || n == null || n < 0) throw new IllegalArgumentException();
        String res = new String();
        for (int i = 0; i < n; i++) {
            res += input;
        }
        return res;
    }

    public static Integer count(String haystack, String needle) throws IllegalArgumentException {
        if (haystack == null || needle == null) throw new IllegalArgumentException();
        int res = 0;
        for (int i = 0; i < haystack.length() - needle.length() + 1; i++) {
            if (haystack.substring(i, i + needle.length()).equals(needle)) {
                res++;
            }
        }
        return res;
    }

    public static String convertToNumberWords(String in) {
        if (in == null) throw new IllegalArgumentException();
        String res = new String();
        for (char ch : in.toCharArray()) {
            switch (ch) {
                case '1':
                    res += "один";
                    break;
                case '2':
                    res += "два";
                    break;
                case '3':
                    res += "три";
                    break;
                default:
                    res += ch;
                    break;
            }
        }
        return res;
    }

    public static void removeEvenCharacters(StringBuilder sb) throws IllegalArgumentException {
        if (sb == null) throw new IllegalArgumentException();
        for (int i = sb.length()-1 - (sb.length() % 2); i >= 0; i -= 2) {
            sb.deleteCharAt(i);
        }
    }

    public static String reverseWords(String in) {
        if (in == null) throw new IllegalArgumentException();
        char chars[] = in.toCharArray();
        String res = new String();
        int word_ptr = chars.length-1;
        while (word_ptr >= 0 && chars[word_ptr] == ' ') word_ptr--;
        while (word_ptr >= 0 && chars[word_ptr] != ' ') word_ptr--;
        word_ptr++;
        for (int i = 0; i < in.length(); i++) {
            if (chars[i] == ' ') {
                res += " ";
                continue;
            }
            for (int j = word_ptr; j < chars.length && chars[j] != ' '; j++) {
                res += chars[j];
            }
            word_ptr--;
            while (word_ptr >= 0 && chars[word_ptr] == ' ') word_ptr--;
            while (word_ptr >= 0 && chars[word_ptr] != ' ') word_ptr--;
            word_ptr++;
            while (i < chars.length && chars[i] != ' ') i++;
            i--;
        }
        return res;
    }

    public static String replaceAllHexToDecimal(String in) {
        if (in == null) throw new IllegalArgumentException();
        char chars[] = in.toCharArray();
        String res = new String();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length-1 && chars[i] == '0' && chars[i+1] == 'x' && i+2 < chars.length && ('0' <= chars[i+2] && chars[i+2] <= '9' || 'a' <= chars[i+2] && chars[i+2] <= 'f' || 'A' <= chars[i+2] && chars[i+2] <= 'F')) {
                Integer tmp = 0;
                int j;
                for (j = i+2; j < chars.length; j++) {
                    if ('0' <= chars[j] && chars[j] <= '9') {
                        tmp = tmp*16 + chars[j] - '0';
                    } else if ('a' <= chars[j] && chars[j] <= 'f') {
                        tmp = tmp*16 + chars[j] - 'a' + 10;
                    } else if ('A' <= chars[j] && chars[j] <= 'F') {
                        tmp = tmp*16 + chars[j] - 'A' + 10;
                    } else {
                        break;
                    }
                }
                res += Integer.toUnsignedString(tmp);
                i = j-1;
                continue;
            }
            res += chars[i];
        }
        return res;
    }
}
