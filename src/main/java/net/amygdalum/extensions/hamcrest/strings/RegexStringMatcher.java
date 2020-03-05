package net.amygdalum.extensions.hamcrest.strings;

import static java.util.regex.Pattern.DOTALL;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

public class RegexStringMatcher extends TypeSafeMatcher<String> {

	private Pattern pattern;
	private Map<Integer, Matcher<String>> groups;

	public RegexStringMatcher(String pattern) {
		this.pattern = compile(pattern);
		this.groups = new HashMap<>();
	}

	private Pattern compile(String pattern) {
		return Pattern.compile(pattern, DOTALL);
	}

	public RegexStringMatcher withGroup(int i, Matcher<String> matcher) {
		groups.put(i, matcher);
		return this;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("contains regex pattern ").appendValue(pattern);
		for (Map.Entry<Integer, Matcher<String>> group : groups.entrySet()) {
			description.appendText("\n\twith group " + group.getKey() + " matching ").appendDescriptionOf(group.getValue());
		}
	}

	@Override
	protected boolean matchesSafely(String item) {
		java.util.regex.Matcher matcher = pattern.matcher(item);
		if (!matcher.find()) {
			return false;
		}
		for (Map.Entry<Integer, Matcher<String>> group : groups.entrySet()) {
			int groupIndex = group.getKey();
			Matcher<String> groupMatcher = group.getValue();

			String foundgroup = matcher.group(groupIndex);

			if (!groupMatcher.matches(foundgroup)) {
				return false;
			}
		}

		return true;
	}

	public static RegexStringMatcher containsPattern(String pattern) {
		return new RegexStringMatcher(pattern);
	}

}
