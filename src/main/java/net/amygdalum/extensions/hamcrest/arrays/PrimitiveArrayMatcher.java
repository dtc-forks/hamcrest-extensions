package net.amygdalum.extensions.hamcrest.arrays;

import java.util.Arrays;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import net.amygdalum.extensions.hamcrest.util.SimpleClass;

public class PrimitiveArrayMatcher<T> extends TypeSafeMatcher<T> {

	private T array;
	private Mode<T> match;

	private PrimitiveArrayMatcher(T array) {
		this.array = array;
		this.match = new Exact();
	}

	public static PrimitiveArrayMatcher<boolean[]> booleanArrayContaining(boolean... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public static PrimitiveArrayMatcher<char[]> charArrayContaining(char... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public static PrimitiveArrayMatcher<byte[]> byteArrayContaining(byte... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public static PrimitiveArrayMatcher<short[]> shortArrayContaining(short... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public static PrimitiveArrayMatcher<int[]> intArrayContaining(int... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public static PrimitiveArrayMatcher<float[]> floatArrayContaining(float... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public static PrimitiveArrayMatcher<long[]> longArrayContaining(long... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public static PrimitiveArrayMatcher<double[]> doubleArrayContaining(double... items) {
		return new PrimitiveArrayMatcher<>(items);
	}

	public PrimitiveArrayMatcher<T> inAnyOrder() {
		this.match = new AnyOrder();
		return this;
	}

	public PrimitiveArrayMatcher<T> atLeast() {
		this.match = new AtLeast();
		return this;
	}

	private void sort(boolean[] array) {
		int trueValues = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i]) {
				trueValues++;
			}
		}
		for (int i = 0; i < trueValues; i++) {
			array[i] = true;
		}
		for (int i = trueValues; i < array.length; i++) {
			array[i] = false;
		}
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("an array containing values of type ")
			.appendValue(array.getClass().getComponentType())
			.appendText(": ")
			.appendValue(array);
	}

	@Override
	protected void describeMismatchSafely(T item, Description mismatchDescription) {
		if (!item.getClass().isArray()) {
			mismatchDescription.appendText("not an array");
		} else if (!item.getClass().getComponentType().isPrimitive()) {
			mismatchDescription.appendText("not a primitive array");
		} else if (item.getClass() != array.getClass()) {
			mismatchDescription.appendText("of type ").appendValue(new SimpleClass(item.getClass()));
		} else {
			mismatchDescription.appendText("with items ").appendValue(item);
		}
	}

	@Override
	protected boolean matchesSafely(T item) {
		if (!item.getClass().isArray()) {
			return false;
		}
		if (!item.getClass().getComponentType().isPrimitive()) {
			return false;
		}
		if (item.getClass() != array.getClass()) {
			return false;
		}
		Class<?> type = array.getClass().getComponentType();
		if (type == boolean.class) {
			return match.match((boolean[]) array, (boolean[]) item);
		} else if (type == char.class) {
			return match.match((char[]) array, (char[]) item);
		} else if (type == byte.class) {
			return match.match((byte[]) array, (byte[]) item);
		} else if (type == short.class) {
			return match.match((short[]) array, (short[]) item);
		} else if (type == int.class) {
			return match.match((int[]) array, (int[]) item);
		} else if (type == float.class) {
			return match.match((float[]) array, (float[]) item);
		} else if (type == long.class) {
			return match.match((long[]) array, (long[]) item);
		} else if (type == double.class) {
			return match.match((double[]) array, (double[]) item);
		} else {
			return false;
		}
	}

	private boolean[] sorted(boolean[] item) {
		item = Arrays.copyOf(item, item.length);
		sort(item);
		return item;
	}

	private char[] sorted(char[] item) {
		item = Arrays.copyOf(item, item.length);
		Arrays.sort(item);
		return item;
	}

	private byte[] sorted(byte[] item) {
		item = Arrays.copyOf(item, item.length);
		Arrays.sort(item);
		return item;
	}

	private short[] sorted(short[] item) {
		item = Arrays.copyOf(item, item.length);
		Arrays.sort(item);
		return item;
	}

	private int[] sorted(int[] item) {
		item = Arrays.copyOf(item, item.length);
		Arrays.sort(item);
		return item;
	}

	private float[] sorted(float[] item) {
		item = Arrays.copyOf(item, item.length);
		Arrays.sort(item);
		return item;
	}

	private long[] sorted(long[] item) {
		item = Arrays.copyOf(item, item.length);
		Arrays.sort(item);
		return item;
	}

	private double[] sorted(double[] item) {
		item = Arrays.copyOf(item, item.length);
		Arrays.sort(item);
		return item;
	}

	interface Mode<T> {

		boolean match(boolean[] array, boolean[] item);

		boolean match(double[] array, double[] item);

		boolean match(long[] array, long[] item);

		boolean match(float[] array, float[] item);

		boolean match(int[] array, int[] item);

		boolean match(short[] array, short[] item);

		boolean match(byte[] array, byte[] item);

		boolean match(char[] array, char[] item);

	}

	private class Exact implements Mode<T> {

		@Override
		public boolean match(boolean[] array, boolean[] item) {
			return Arrays.equals(array, item);
		}

		@Override
		public boolean match(double[] array, double[] item) {
			return Arrays.equals(array, item);
		}

		@Override
		public boolean match(long[] array, long[] item) {
			return Arrays.equals(array, item);
		}

		@Override
		public boolean match(float[] array, float[] item) {
			return Arrays.equals(array, item);
		}

		@Override
		public boolean match(int[] array, int[] item) {
			return Arrays.equals(array, item);
		}

		@Override
		public boolean match(short[] array, short[] item) {
			return Arrays.equals(array, item);
		}

		@Override
		public boolean match(byte[] array, byte[] item) {
			return Arrays.equals(array, item);
		}

		@Override
		public boolean match(char[] array, char[] item) {
			return Arrays.equals(array, item);
		}

	}

	private class AnyOrder implements Mode<T> {

		@Override
		public boolean match(boolean[] array, boolean[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}

		@Override
		public boolean match(double[] array, double[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}

		@Override
		public boolean match(long[] array, long[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}

		@Override
		public boolean match(float[] array, float[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}

		@Override
		public boolean match(int[] array, int[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}

		@Override
		public boolean match(short[] array, short[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}

		@Override
		public boolean match(byte[] array, byte[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}

		@Override
		public boolean match(char[] array, char[] item) {
			return Arrays.equals(sorted(array), sorted(item));
		}
	}

	private class AtLeast implements Mode<T> {

		@Override
		public boolean match(boolean[] array, boolean[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

		@Override
		public boolean match(double[] array, double[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

		@Override
		public boolean match(long[] array, long[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

		@Override
		public boolean match(float[] array, float[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

		@Override
		public boolean match(int[] array, int[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

		@Override
		public boolean match(short[] array, short[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

		@Override
		public boolean match(byte[] array, byte[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

		@Override
		public boolean match(char[] array, char[] item) {
			array = sorted(array);
			item = sorted(item);
			int i = 0;
			for (int j = 0; i < array.length && j < item.length; i++) {
				while (j < item.length) {
					if (array[i] == item[j]) {
						j++;
						break;
					} else {
						j++;
					}
				}
			}
			return i == array.length;
		}

	}
}
