package net.amygdalum.extensions.hamcrest.conventions;

import static net.amygdalum.extensions.hamcrest.conventions.EqualityMatcher.satisfiesDefaultEquality;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.isEmptyString;
import static org.junit.Assert.assertThat;

import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

public class EqualityMatcherTest {

	@Test
	public void testDescribeTo() throws Exception {
		StringDescription description = new StringDescription();

		satisfiesDefaultEquality().describeTo(description);

		assertThat(description.toString(), equalTo("should satisfy common equality contraints as"
			+ "\n- object should not equal null or object of a different class"
			+ "\n- object should equal itself"
			+ "\n- equal objects have equal hashcodes"));
	}

	@Test
	public void testDescribeToWithToString() throws Exception {
		StringDescription description = new StringDescription();

		satisfiesDefaultEquality().includingToString().describeTo(description);

		assertThat(description.toString(), equalTo("should satisfy common equality contraints as"
			+ "\n- object should not equal null or object of a different class"
			+ "\n- object should equal itself"
			+ "\n- equal objects have equal hashcodes"
			+ "\n- equal objects have equal toString"));
	}

	@Test
	public void testDescribeToWithConstraints() throws Exception {
		StringDescription description = new StringDescription();

		satisfiesDefaultEquality()
			.andEqualTo("string")
			.andNotEqualTo(1)
			.describeTo(description);

		assertThat(description.toString(), equalTo("should satisfy common equality contraints as"
			+ "\n- object should not equal null or object of a different class"
			+ "\n- object should equal itself"
			+ "\n- equal objects have equal hashcodes"
			+ "\nand special contrains given:"
			+ "\n- should equal \"string\""
			+ "\n- should not equal <1>"));
	}

	@Test
	public void testDescribeToWithEqualConstraints() throws Exception {
		StringDescription description = new StringDescription();

		satisfiesDefaultEquality()
			.andEqualTo("string")
			.describeTo(description);

		assertThat(description.toString(), equalTo("should satisfy common equality contraints as"
			+ "\n- object should not equal null or object of a different class"
			+ "\n- object should equal itself"
			+ "\n- equal objects have equal hashcodes"
			+ "\nand special contrains given:"
			+ "\n- should equal \"string\""));
	}

	@Test
	public void testDescribeToWithNonEqualConstraints() throws Exception {
		StringDescription description = new StringDescription();

		satisfiesDefaultEquality()
			.andNotEqualTo(1)
			.describeTo(description);

		assertThat(description.toString(), equalTo("should satisfy common equality contraints as"
			+ "\n- object should not equal null or object of a different class"
			+ "\n- object should equal itself"
			+ "\n- equal objects have equal hashcodes"
			+ "\nand special contrains given:"
			+ "\n- should not equal <1>"));
	}

	@Test
	public void testMatchesSafely() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality().matchesSafely(new DefaultEqual(), description);

		assertThat(matches, is(true));
		assertThat(description.toString(), isEmptyString());
	}

	@Test
	public void testMatchesSafelyWithConstraints() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andEqualTo(new DefaultEqual("field"))
			.andNotEqualTo(new DefaultEqual("otherfield"))
			.matchesSafely(new DefaultEqual("field"), description);

		assertThat(matches, is(true));
		assertThat(description.toString(), isEmptyString());
	}

	@Test
	public void testMismatchesSafelyWithEqualConstraints() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andEqualTo(new BrokenEqual("field"))
			.matchesSafely(new BrokenEqual("field"), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should equal <field>, was <field>"));
	}

	@Test
	public void testMismatchesSafelyWithEqualConstraintsAsymetric() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andEqualTo(new BrokenEqualAsymetric(false))
			.matchesSafely(new BrokenEqualAsymetric(true), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should equal <false>, was <true>"));
	}

	@Test
	public void testMismatchesSafelyWithNonEqualConstraints() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andNotEqualTo(new BrokenNotEqual("field"))
			.matchesSafely(new BrokenNotEqual("field"), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should not equal <field>, was <field>"));
	}

	@Test
	public void testMismatchesSafelyWithNonEqualConstraintsAsymetric() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andNotEqualTo(new BrokenEqualAsymetric(true))
			.matchesSafely(new BrokenEqualAsymetric(false), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should not equal <true>, was <false>"));
	}

	@Test
	public void testMismatchesSafelyWithEqualConstraintsToString() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andEqualTo(new DefaultEqualNoToString("field"))
			.includingToString()
			.matchesSafely(new DefaultEqualNoToString("field"), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), containsString("should have same toString"));
	}

	@Test
	public void testMatchesSafelyWithEqualConstraintsToString() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andEqualTo(new DefaultEqualToString("field"))
			.includingToString()
			.matchesSafely(new DefaultEqualToString("field"), description);

		assertThat(matches, is(true));
		assertThat(description.toString(), isEmptyString());
	}

	@Test
	public void testMatchesSafelyWithNonEqualConstraints() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andNotEqualTo(new DefaultEqual("field2"))
			.matchesSafely(new DefaultEqual("field1"), description);

		assertThat(matches, is(true));
		assertThat(description.toString(), isEmptyString());
	}

	@Test
	public void testMismatchesNull() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality().matchesSafely(null, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should be not null"));
	}

	@Test
	public void testMismatchesNotSelfEqual() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality().matchesSafely(new NotSelfEqual(), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should equal self"));
	}

	@Test
	public void testMismatchesSelfEqualButDifferentHashcodes() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality().matchesSafely(new SelfEqualButDifferentHashcodes(), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should equal self"));
	}

	@Test
	public void testMismatchesOnDifferentHashcodes() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality()
			.andEqualTo(new DefaultEqualButDifferentHashcodes("field", 1))
			.matchesSafely(new DefaultEqualButDifferentHashcodes("field", 2), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should have same hashcode: <field1>, was <field2>"));
	}

	@Test
	public void testMismatchesNullEqual() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality().matchesSafely(new NullEqual(), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should not equal null"));
	}

	@Test
	public void testMismatchesEqualToOtherClasses() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = satisfiesDefaultEquality().matchesSafely(new EqualToOtherClasses(), description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("should not equal object of other class"));
	}

	private static class NullEqual {

		@Override
		public boolean equals(Object obj) {
			return true;
		}
	}

	private static class EqualToOtherClasses {

		@Override
		public boolean equals(Object obj) {
			return obj != null;
		}
	}

	private static class NotSelfEqual {

		@Override
		public boolean equals(Object obj) {
			return false;
		}
	}

	private static class SelfEqualButDifferentHashcodes {

		private static int no;

		@Override
		public int hashCode() {
			return no++;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return true;
		}
	}

	private static class DefaultEqual {
		protected String field;

		public DefaultEqual() {
		}

		public DefaultEqual(String field) {
			this.field = field;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((field == null) ? 0 : field.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			DefaultEqual other = (DefaultEqual) obj;
			if (field == null) {
				if (other.field != null) {
					return false;
				}
			} else if (!field.equals(other.field)) {
				return false;
			}
			return true;
		}

	}

	private static class DefaultEqualButDifferentHashcodes {
		protected String field;
		private int hashCode;

		public DefaultEqualButDifferentHashcodes(String field, int hashCode) {
			this.field = field;
			this.hashCode = hashCode;
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			DefaultEqualButDifferentHashcodes other = (DefaultEqualButDifferentHashcodes) obj;
			if (field == null) {
				if (other.field != null) {
					return false;
				}
			} else if (!field.equals(other.field)) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return field + hashCode;
		}
	}

	private static class BrokenEqual {
		protected String field;

		public BrokenEqual(String field) {
			this.field = field;
		}

		@Override
		public String toString() {
			return field;
		}
	}

	private static class BrokenEqualAsymetric {
		protected boolean field;

		public BrokenEqualAsymetric(boolean field) {
			this.field = field;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return field;
		}

		@Override
		public String toString() {
			return "" + field;
		}
	}

	private static class BrokenNotEqual {
		protected String field;

		public BrokenNotEqual(String field) {
			this.field = field;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			return true;
		}

		@Override
		public String toString() {
			return field;
		}
	}

	private static class DefaultEqualToString extends DefaultEqual {

		public DefaultEqualToString(String field) {
			super(field);
		}

		@Override
		public String toString() {
			return field;
		}
	}

	private static class DefaultEqualNoToString extends DefaultEqual {

		public DefaultEqualNoToString(String field) {
			super(field);
		}

		@Override
		public String toString() {
			return "" + System.identityHashCode(this);
		}
	}
}
