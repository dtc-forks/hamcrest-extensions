package net.amygdalum.extensions.hamcrest.conventions;

import java.lang.reflect.Method;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * The EnumMatcher matches enums. 
 * * asserting that the given class is an enum class
 * * asserting that all values can be constructed via valueOf(String)
 */
public class EnumMatcher extends TypeSafeDiagnosingMatcher<Class<? extends Enum<?>>> {

	private Integer count;

	public EnumMatcher() {
	}

	public EnumMatcher withElements(int count) {
		this.count = count;
		return this;
	}

	public static EnumMatcher isEnum() {
		return new EnumMatcher();
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("should be an enum");
		if (count != null) {
			description.appendText(" with number of elements ").appendValue(count);
		}
	}

	@Override
	protected boolean matchesSafely(Class<? extends Enum<?>> item, Description mismatchDescription) {
		if (!Enum.class.isAssignableFrom(item)) {
			mismatchDescription.appendText("is not an enum");
			return false;
		}
		Enum<?>[] enumConstants=item.getEnumConstants();
		if (count != null && enumConstants.length != count) {
			mismatchDescription.appendText("number of elements was ").appendValue(item.getEnumConstants().length);
			return false;
		}
		for (Enum<?> enumConstant : enumConstants) {
			try {
				Method valueOf = item.getDeclaredMethod("valueOf", String.class);
				Object result = valueOf.invoke(null, enumConstant.name());
				if (result != enumConstant) {
					throw new IllegalStateException("enum "+ item.getSimpleName() + " has rewritten valueOf method");
				}
			} catch (ReflectiveOperationException | SecurityException e) {
				mismatchDescription.appendText("fails with " + e.getMessage());
				return false;
			}
		}
		return true;
	}

}
