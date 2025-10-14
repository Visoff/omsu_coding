package lab32;

public class BeginStringFilter implements Filter {
    private String pattern;

    public BeginStringFilter(String pattern) {
        this.pattern = pattern;
    }

	@Override
	public boolean apply(String str) {
        if (str.length() < pattern.length()) {
            return false;
        }
        return str.substring(0, pattern.length()).equals(pattern);
	}
}
