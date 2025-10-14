package lab32;

public class ContainsStringFilter implements Filter {
    private String pattern;

    public ContainsStringFilter(String pattern) {
        this.pattern = pattern;
    }

	@Override
	public boolean apply(String str) {
        return str.contains(pattern);
	}
}
