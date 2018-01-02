package net.amygdalum.extensions.hamcrest.exceptions;

import static net.amygdalum.extensions.hamcrest.conventions.UtilityClassMatcher.isUtilityClass;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import net.amygdalum.extensions.hamcrest.exceptions.Exceptions.WithResult;
import net.amygdalum.extensions.hamcrest.exceptions.Exceptions.WithoutResult;

public class ExceptionsTest {

	@Test
	public void testExceptions() throws Exception {
		assertThat(Exceptions.class, isUtilityClass());
	}

	@Test
	public void testCatchExceptionWithoutResult() throws Exception {
		boolean[] condition = new boolean[] { false };
		WithoutResult code = () -> {
			if (condition[0]) {
				return;
			}
			throw new RuntimeException();
		};
		condition[0] = true;
		assertThat(Exceptions.catchException(code), nullValue());
		condition[0] = false;
		assertThat(Exceptions.catchException(code), instanceOf(RuntimeException.class));

		assertThat(Exceptions.catchException(code, NullPointerException.class), nullValue());
		assertThat(Exceptions.catchException(code, Exception.class), instanceOf(RuntimeException.class));
	}

	@Test
	public void testCatchExceptionWithResult() throws Exception {
		boolean[] condition = new boolean[] { false };
		WithResult<?> code = () -> {
			if (condition[0]) {
				return "result";
			}
			throw new RuntimeException();
		};
		condition[0] = true;
		assertThat(Exceptions.catchException(code), nullValue());
		condition[0] = false;
		assertThat(Exceptions.catchException(code), instanceOf(RuntimeException.class));

		assertThat(Exceptions.catchException(code, NullPointerException.class), nullValue());
		assertThat(Exceptions.catchException(code, Exception.class), instanceOf(RuntimeException.class));
	}

}
