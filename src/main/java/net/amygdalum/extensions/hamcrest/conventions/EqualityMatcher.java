package net.amygdalum.extensions.hamcrest.conventions;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * The EqualityMatcher matches an object to comply to the common (or slightly modified) equality conventions, default are:
 * * no object should equal `null`
 * * no object should equal an object of another class
 * * every object should equal `this`
 * * same objects should have the same hashcode
 * 
 * This matcher is fluently extensible by samples that are equal, or not equal.
 * 
 * This matcher is configurable to check the toString() representation (equal objects should have the same representation, non-equal should not).
 */
public class EqualityMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

	private static final Object differentObject = new Object();

	private List<T> equals;
	private List<T> notEquals;
	private boolean toString;

	public EqualityMatcher() {
		this.equals = new ArrayList<>();
		this.notEquals = new ArrayList<>();
		this.toString = false;
	}

	public static <T> EqualityMatcher<T> satisfiesDefaultEquality() {
		return new EqualityMatcher<T>();
	}

	public EqualityMatcher<T> andEqualTo(T element) {
		this.equals.add(element);
		return this;
	}

	public EqualityMatcher<T> andNotEqualTo(T element) {
		this.notEquals.add(element);
		return this;
	}

	public EqualityMatcher<T> includingToString() {
		this.toString = true;
		return this;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("should satisfy common equality contraints as");
		description.appendText("\n- object should not equal null or object of a different class");
		description.appendText("\n- object should equal itself");
		description.appendText("\n- equal objects have equal hashcodes");
		if (toString) {
			description.appendText("\n- equal objects have equal toString");
		}
		if (!equals.isEmpty() || !notEquals.isEmpty()) {
			description.appendText("\nand special contrains given:");
			for (T element : equals) {
				description.appendText("\n- should equal ").appendValue(element);
			}
			for (T element : notEquals) {
				description.appendText("\n- should not equal ").appendValue(element);
			}
		}
	}

	@Override
	protected boolean matchesSafely(T item, Description mismatchDescription) {
		if (item == null) {
			mismatchDescription.appendText("should be not null");
			return false;
		}

		if (!item.equals(item) || item.hashCode() != item.hashCode()) {
			mismatchDescription.appendText("should equal self");
			return false;
		}
		for (T element : equals) {
			if (!item.equals(element) || !element.equals(item)) {
				mismatchDescription.appendText("should equal ").appendValue(element).appendText(", was ").appendValue(item);
				return false;
			} else if (item.hashCode() != element.hashCode()) {
				mismatchDescription.appendText("should have same hashcode: ").appendValue(element).appendText(", was ").appendValue(item);
				return false;
			} else if (toString && !item.toString().equals(element.toString())) {
				mismatchDescription.appendText("should have same toString: ").appendValue(element.toString()).appendText(", was ").appendValue(item.toString());
				return false;
			}
		}

		if (item.equals(null)) {
			mismatchDescription.appendText("should not equal null");
			return false;
		}
		if (item.equals(differentObject)) {
			mismatchDescription.appendText("should not equal object of other class");
			return false;
		}
		for (T element : notEquals) {
			if (item.equals(element) || element.equals(item)) {
				mismatchDescription.appendText("should not equal ").appendValue(element).appendText(", was ").appendValue(item);
				return false;
			} else {
				element.hashCode();
			}
		}
		return true;
	}

}
