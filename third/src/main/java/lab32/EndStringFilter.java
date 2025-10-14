package lab32;

public class EndStringFilter implements Filter {
    private String pattern;

    public EndStringFilter(String pattern) {
        this.pattern = pattern;
    }

	@Override
	public boolean apply(String str) {
        if (str.length() < pattern.length()) {
            return false;
        }
        return str.substring(str.length() - pattern.length()).equals(pattern);
	}
}
