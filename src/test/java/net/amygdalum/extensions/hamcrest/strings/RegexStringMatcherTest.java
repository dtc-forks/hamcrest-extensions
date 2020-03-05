package net.amygdalum.extensions.hamcrest.strings;

import static net.amygdalum.extensions.hamcrest.strings.RegexStringMatcher.containsPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

public class RegexStringMatcherTest {

	@Test
	public void testMatchesSafelyWithOneCharWildcard() throws Exception {
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, 'a', is matched"), is(true));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, 'Z', is matched"), is(true));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, '1', is matched"), is(true));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, '%', is matched"), is(true));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, 'ß', is matched"), is(true));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, ' ', is matched"), is(true));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, '\n', is matched"), is(true));
	}

	@Test
	public void testMatchesSafelyWithOptionalCharWildcard() throws Exception {
		assertThat(containsPattern("just 1 char, '.?', is matched").matches("just 1 char, 'x', is matched"), is(true));
		assertThat(containsPattern("just 1 char, '.?', is matched").matches("just 1 char, '', is matched"), is(true));
	}

	@Test
	public void testMatchesSafelyWithOneCharWildcardFails() throws Exception {
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, '', is matched"), is(false));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, 'ab', is matched"), is(false));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, 'ZZ', is matched"), is(false));
		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, '\t\n', is matched"), is(false));

		assertThat(containsPattern("just 1 char, '.', is matched").matches("just 1 char, '\t'\n, is matched"), is(false));
	}

	@Test
	public void testMatchesSafelyWithMultiCharWildcard() throws Exception {
		assertThat(containsPattern("any chars, '.*', are matched").matches("any chars, 'abcde', are matched"), is(true));
		assertThat(containsPattern("any chars, '.*', are matched").matches("any chars, 'ZYX', are matched"), is(true));
		assertThat(containsPattern("any chars, '.*', are matched").matches("any chars, 'errare humanum est', are matched"), is(true));
		assertThat(containsPattern("any chars, '.*', are matched").matches("any chars, 'with\nsome\twhitespace chars', are matched"), is(true));
		assertThat(containsPattern("any chars, '.*', are matched").matches("any chars, '', are matched"), is(true));
	}

	@Test
	public void testMatchesSafelyWithMultiCharWildcardFails() throws Exception {
		assertThat(containsPattern("any chars, '.*', are matched").matches("any chars, 'abcde'\n, are matched"), is(false));
	}

	@Test
	public void testMatchesSafelyWithGroups() throws Exception {
		assertThat(containsPattern("pattern (.*):(.*) is matched")
			.withGroup(1, equalTo("key"))
			.withGroup(2, equalTo("value"))
			.matches("pattern key:value is matched"), is(true));
		assertThat(containsPattern("pattern (.*):(.*) is matched")
			.withGroup(1, equalTo("key"))
			.withGroup(2, equalTo("value"))
			.matches("pattern keyvalue is matched"), is(false));
		assertThat(containsPattern("pattern (.*):(.*) is matched")
			.withGroup(1, equalTo("key"))
			.withGroup(2, equalTo("value"))
			.matches("pattern key1:value is matched"), is(false));
		assertThat(containsPattern("pattern (.*):(.*) is matched")
			.withGroup(1, equalTo("key"))
			.withGroup(2, equalTo("value"))
			.matches("pattern key:value1 is matched"), is(false));
	}

	@Test
	public void testDescribeTo() throws Exception {
		StringDescription description = new StringDescription();

		containsPattern("just 1 char, '.', is matched").describeTo(description);

		assertThat(description.toString(), equalTo("contains regex pattern <just 1 char, '.', is matched>"));
	}

	@Test
	public void testDescribeToWithGroups() throws Exception {
		StringDescription description = new StringDescription();

		containsPattern("pattern (.*):(.*) is matched")
			.withGroup(1, equalTo("key"))
			.withGroup(2, equalTo("value"))
			.describeTo(description);

		assertThat(description.toString(), equalTo(""
			+ "contains regex pattern <pattern (.*):(.*) is matched>"
			+ "\n\twith group 1 matching \"key\""
			+ "\n\twith group 2 matching \"value\""));
	}

	@Test
	public void testDescribeMismatch() throws Exception {
		StringDescription description = new StringDescription();

		containsPattern("just 1 char, '.', is matched").describeMismatch("just 1 char, 'ab', is matched", description);

		assertThat(description.toString(), equalTo("was \"just 1 char, 'ab', is matched\""));
	}

}
