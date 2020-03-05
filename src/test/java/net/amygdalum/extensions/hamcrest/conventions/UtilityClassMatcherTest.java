package net.amygdalum.extensions.hamcrest.conventions;

import static net.amygdalum.extensions.hamcrest.conventions.UtilityClassMatcher.isUtilityClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;

import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

public class UtilityClassMatcherTest {

	@Test
	public void testDescribeTo() throws Exception {
		StringDescription description = new StringDescription();

		isUtilityClass().describeTo(description);

		assertThat(description.toString(), equalTo(""
			+ "should be declared final"
			+ "\nand have a private default constructor doing nothing"
			+ "\nand have only static methods"));
	}

	@Test
	public void testMismatchesSafelyNonFinal() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = isUtilityClass().matchesSafely(NotFinal.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("is not declared final"));
	}

	@Test
	public void testMismatchesSafelyNoPrivateConstructor() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = isUtilityClass().matchesSafely(NoPrivateConstructor.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("has not a private default constructor"));
	}

	@Test
	public void testMismatchesSafelyPrivateConstructorThrowingCheckedExceptions() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = isUtilityClass().matchesSafely(PrivateConstructorThrowingCheckedExceptions.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("constructor throws checked exceptions"));
	}
	
	@Test
	public void testMismatchesSafelyNonStaticMethods() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = isUtilityClass().matchesSafely(NonStaticMethods.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("has a non static method \"method\""));
	}
	
	@Test
	public void testMatchesSafelyUtilityClass() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = isUtilityClass().matchesSafely(Utility.class, description);
		
		assertThat(matches, is(true));
		assertThat(description.toString(), isEmptyString());
	}
	
	@Test
	public void testMatchesSafelyUtilityClassThrowingRuntimeException() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = isUtilityClass().matchesSafely(UtilityThrowingRuntimeException.class, description);
		
		assertThat(matches, is(true));
		assertThat(description.toString(), isEmptyString());
	}
	
	public static class NotFinal {
		
	}

	public static final class NoPrivateConstructor {
		
	}
	
	public static final class NonStaticMethods {
		
		private NonStaticMethods() {
		}
		
		public void method() {
		}
		
	}
	
	public static final class PrivateConstructorThrowingCheckedExceptions {
		
		private PrivateConstructorThrowingCheckedExceptions() throws IOException {
			throw new IOException();
		}
		
	}
	
	public static final class Utility {
		
		private Utility() {
		}
		
		public static void method() {
		}
		
	}
	
	public static final class UtilityThrowingRuntimeException {
		
		private UtilityThrowingRuntimeException() {
			throw new UnsupportedOperationException();
		}
		
		public static void method() {
		}
		
	}
	
}
