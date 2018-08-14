package net.amygdalum.extensions.hamcrest.arrays;

import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.booleanArrayContaining;
import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.byteArrayContaining;
import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.charArrayContaining;
import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.doubleArrayContaining;
import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.floatArrayContaining;
import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.intArrayContaining;
import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.longArrayContaining;
import static net.amygdalum.extensions.hamcrest.arrays.PrimitiveArrayMatcher.shortArrayContaining;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

public class PrimitiveArrayMatcherTest {

	@Test
	public void testMatchesSafelyOnTypeMismatch() throws Exception {
		assertThat(booleanArrayContaining().matches(new Object()), is(false));
		assertThat(booleanArrayContaining().matches(new Object[0]), is(false));
		assertThat(booleanArrayContaining().matches(new int[0]), is(false));
	}

	@Test
	public void testMatchesSafelyOnBoolean() throws Exception {
		assertThat(booleanArrayContaining(true, false).matches(new boolean[] { true, false }), is(true));
		assertThat(booleanArrayContaining(true, false).matches(new boolean[] { true }), is(false));
		assertThat(booleanArrayContaining(true, false).matches(new boolean[] { false, true }), is(false));
		assertThat(booleanArrayContaining(true, false)
			.inAnyOrder()
			.matches(new boolean[] { false, true }), is(true));
		assertThat(booleanArrayContaining(true, false, false)
			.inAnyOrder()
			.matches(new boolean[] { false, true }), is(false));
		assertThat(booleanArrayContaining(true).atLeast().matches(new boolean[] { true, false }), is(true));
		assertThat(booleanArrayContaining(false).atLeast().matches(new boolean[] { true, false }), is(true));
	}

	@Test
	public void testMatchesSafelyOnChar() throws Exception {
		assertThat(charArrayContaining('a', 'z').matches(new char[] { 'a', 'z' }), is(true));
		assertThat(charArrayContaining('a', 'z').matches(new char[] { 'a' }), is(false));
		assertThat(charArrayContaining('a', 'z').matches(new char[] { 'z', 'a' }), is(false));
		assertThat(charArrayContaining('a', 'z')
			.inAnyOrder()
			.matches(new char[] { 'z', 'a' }), is(true));
		assertThat(charArrayContaining('a', 'z', 'A')
			.inAnyOrder()
			.matches(new char[] { 'z', 'a' }), is(false));
		assertThat(charArrayContaining('a').atLeast().matches(new char[] { 'z', 'a' }), is(true));
		assertThat(charArrayContaining('z').atLeast().matches(new char[] { 'z', 'a' }), is(true));
	}

	@Test
	public void testMatchesSafelyOnByte() throws Exception {
		assertThat(byteArrayContaining(b(0), b(1)).matches(new byte[] { 0, 1 }), is(true));
		assertThat(byteArrayContaining(b(0), b(1)).matches(new byte[] { 0 }), is(false));
		assertThat(byteArrayContaining(b(0), b(1)).matches(new byte[] { 1, 0 }), is(false));
		assertThat(byteArrayContaining(b(0), b(1))
			.inAnyOrder()
			.matches(new byte[] { 1, 0 }), is(true));
		assertThat(byteArrayContaining(b(0), b(1), b(2))
			.inAnyOrder()
			.matches(new byte[] { 1, 0 }), is(false));
		assertThat(byteArrayContaining(b(0)).atLeast().matches(new byte[] { 1, 0 }), is(true));
		assertThat(byteArrayContaining(b(1)).atLeast().matches(new byte[] { 1, 0 }), is(true));
	}

	@Test
	public void testMatchesSafelyOnShort() throws Exception {
		assertThat(shortArrayContaining(s(1), s(0)).matches(new short[] { 1, 0 }), is(true));
		assertThat(shortArrayContaining(s(1), s(0)).matches(new short[] { 1 }), is(false));
		assertThat(shortArrayContaining(s(1), s(0)).matches(new short[] { 0, 1 }), is(false));
		assertThat(shortArrayContaining(s(1), s(0))
			.inAnyOrder()
			.matches(new short[] { 0, 1 }), is(true));
		assertThat(shortArrayContaining(s(1), s(0), s(2))
			.inAnyOrder()
			.matches(new short[] { 0, 1 }), is(false));
		assertThat(shortArrayContaining(s(1)).atLeast().matches(new short[] { 0, 1 }), is(true));
		assertThat(shortArrayContaining(s(0)).atLeast().matches(new short[] { 0, 1 }), is(true));
	}

	@Test
	public void testMatchesSafelyOnInt() throws Exception {
		assertThat(intArrayContaining(-9999999, 1).matches(new int[] { -9999999, 1 }), is(true));
		assertThat(intArrayContaining(-9999999, 1).matches(new int[] { -9999999 }), is(false));
		assertThat(intArrayContaining(-9999999, 1).matches(new int[] { 1, -9999999 }), is(false));
		assertThat(intArrayContaining(-9999999, 1)
			.inAnyOrder()
			.matches(new int[] { 1, -9999999 }), is(true));
		assertThat(intArrayContaining(-9999999, 1, 42)
			.inAnyOrder()
			.matches(new int[] { 1, -9999999 }), is(false));
		assertThat(intArrayContaining(-9999999).atLeast().matches(new int[] { -9999999, 1 }), is(true));
		assertThat(intArrayContaining(1).atLeast().matches(new int[] { -9999999, 1 }), is(true));
	}

	@Test
	public void testMatchesSafelyOnLong() throws Exception {
		assertThat(longArrayContaining(1, -9999999999l).matches(new long[] { 1, -9999999999l }), is(true));
		assertThat(longArrayContaining(1, -9999999999l).matches(new long[] { 1 }), is(false));
		assertThat(longArrayContaining(1, -9999999999l).matches(new long[] { -9999999999l, 1 }), is(false));
		assertThat(longArrayContaining(1, -9999999999l)
			.inAnyOrder()
			.matches(new long[] { -9999999999l, 1 }), is(true));
		assertThat(longArrayContaining(1, -9999999999l, 42)
			.inAnyOrder()
			.matches(new long[] { -9999999999l, 1 }), is(false));
		assertThat(longArrayContaining(1).atLeast().matches(new long[] { 1, -9999999999l }), is(true));
		assertThat(longArrayContaining(-9999999999l).atLeast().matches(new long[] { 1, -9999999999l }), is(true));
	}

	@Test
	public void testMatchesSafelyOnFloat() throws Exception {
		assertThat(floatArrayContaining(-0.1e-7f, 1.234f).matches(new float[] { -0.1e-7f, 1.234f }), is(true));
		assertThat(floatArrayContaining(-0.1e-7f, 1.234f).matches(new float[] { -0.1e-7f }), is(false));
		assertThat(floatArrayContaining(-0.1e-7f, 1.234f).matches(new float[] { 1.234f, -0.1e-7f }), is(false));
		assertThat(floatArrayContaining(-0.1e-7f, 1.234f)
			.inAnyOrder()
			.matches(new float[] { 1.234f, -0.1e-7f }), is(true));
		assertThat(floatArrayContaining(-0.1e-7f, 1.234f, 9.9e22f)
			.inAnyOrder()
			.matches(new float[] { 1.234f, -0.1e-7f }), is(false));
		assertThat(floatArrayContaining(-0.1e-7f).atLeast().matches(new float[] { -0.1e-7f, 1.234f }), is(true));
		assertThat(floatArrayContaining(1.234f).atLeast().matches(new float[] { -0.1e-7f, 1.234f }), is(true));
	}

	@Test
	public void testMatchesSafelyOnDouble() throws Exception {
		assertThat(doubleArrayContaining(0.3e-33, -46232.83345435234235234235235235).matches(new double[] { 0.3e-33, -46232.83345435234235234235235235 }), is(true));
		assertThat(doubleArrayContaining(0.3e-33, -46232.83345435234235234235235235).matches(new double[] { -46232.83345435234235234235235235, 3e-33 }), is(false));
		assertThat(doubleArrayContaining(0.3e-33, -46232.83345435234235234235235235).matches(new double[] { -46232.83345435234235234235235235, 0.3e-33 }), is(false));
		assertThat(doubleArrayContaining(0.3e-33, -46232.83345435234235234235235235)
			.inAnyOrder()
			.matches(new double[] { -46232.83345435234235234235235235, 0.3e-33 }), is(true));
		assertThat(doubleArrayContaining(0.3e-33, -46232.83345435234235234235235235, 42.0)
			.inAnyOrder()
			.matches(new double[] { -46232.83345435234235234235235235, 0.3e-33 }), is(false));
		assertThat(doubleArrayContaining(0.3e-33).atLeast().matches(new double[] { 0.3e-33, -46232.83345435234235234235235235 }), is(true));
		assertThat(doubleArrayContaining(-46232.83345435234235234235235235).atLeast().matches(new double[] { 0.3e-33, -46232.83345435234235234235235235 }), is(true));
	}

	@Test
	public void testDescribeTo() throws Exception {
		StringDescription description = new StringDescription();

		booleanArrayContaining(true, false).describeTo(description);

		assertThat(description.toString(), equalTo("an array containing values of type <boolean>: [<true>, <false>]"));
	}

	@Test
	public void testDescribeMismatchSafelyOnNonArray() throws Exception {
		StringDescription description = new StringDescription();

		booleanArrayContaining().describeMismatch(new Object(), description);

		assertThat(description.toString(), equalTo("not an array"));
	}

	@Test
	public void testDescribeMismatchSafelyOnNonPrimitiveArray() throws Exception {
		StringDescription description = new StringDescription();

		booleanArrayContaining().describeMismatch(new Object[0], description);

		assertThat(description.toString(), equalTo("not a primitive array"));
	}

	@Test
	public void testDescribeMismatchSafelyOnWronglyTypedArray() throws Exception {
		StringDescription description = new StringDescription();

		booleanArrayContaining().describeMismatch(new int[0], description);

		assertThat(description.toString(), equalTo("of type <int[]>"));
	}

	@Test
	public void testDescribeMismatchSafelyOnWrongValues() throws Exception {
		StringDescription description = new StringDescription();

		doubleArrayContaining(0.3e-33, -46232.83345435234235234235235235).describeMismatch(new double[] { 3e-33 }, description);

		assertThat(description.toString(), equalTo("with items [<3.0E-33>]"));
	}

	private byte b(int i) {
		return (byte) i;
	}

	private short s(int i) {
		return (short) i;
	}

}
