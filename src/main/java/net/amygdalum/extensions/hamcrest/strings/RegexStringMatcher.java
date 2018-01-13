package net.amygdalum.extensions.hamcrest.strings;

import static java.util.regex.Pattern.DOTALL;

import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class RegexStringMatcher extends TypeSafeMatcher<String> {

	private Pattern pattern;

	public RegexStringMatcher(String pattern) {
		this.pattern = compile(pattern);
	}

	private Pattern compile(String pattern) {
		return Pattern.compile(pattern, DOTALL);
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("contains regex pattern ").appendValue(pattern);
	}

	@Override
	protected boolean matchesSafely(String item) {
		return pattern.matcher(item).find();
	}

	public static RegexStringMatcher containsPattern(String pattern) {
		return new RegexStringMatcher(pattern);
	}

}
