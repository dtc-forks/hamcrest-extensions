package net.amygdalum.extensions.hamcrest.strings;

import static java.util.regex.Pattern.DOTALL;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class WildcardStringMatcher extends TypeSafeMatcher<String> {

	private WildcardPattern pattern;

	public WildcardStringMatcher(String pattern) {
		this.pattern = compile(pattern);
	}

	private static WildcardPattern compile(String pattern) {
		StringTokenizer t = new StringTokenizer(pattern, "?*", true);
		StringBuilder buffer = new StringBuilder();
		while (t.hasMoreTokens()) {
			String nextToken = t.nextToken();
			if ("?".equals(nextToken)) {
				buffer.append(".?");
			} else if ("*".equals(nextToken)) {
				buffer.append(".*?");
			} else {
				buffer.append(Pattern.quote(nextToken));
			}
		}
		return new WildcardPattern(pattern, Pattern.compile(buffer.toString(), DOTALL));
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("contains wildcard pattern ").appendValue(pattern.rawPattern);
	}

	@Override
	protected boolean matchesSafely(String item) {
		return pattern.pattern.matcher(item).find();
	}

	public static WildcardStringMatcher containsPattern(String pattern) {
		return new WildcardStringMatcher(pattern);
	}

	private static class WildcardPattern {
		public String rawPattern;
		public Pattern pattern;

		public WildcardPattern(String rawPattern, Pattern pattern) {
			this.rawPattern = rawPattern;
			this.pattern = pattern;
		}
		
	}
}
