package net.amygdalum.extensions.hamcrest.objects;

import static java.util.Arrays.asList;
import static java.util.Collections.singleton;
import static net.amygdalum.extensions.hamcrest.objects.ReflectiveEqualsMatcher.reflectiveEqualTo;
import static net.amygdalum.extensions.hamcrest.strings.WildcardStringMatcher.containsPattern;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;

import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

import net.amygdalum.extensions.hamcrest.strings.WildcardStringMatcher;

@SuppressWarnings("unused")
public class ReflectiveEqualsMatcherTest {

	@Test
	public void testDescribeTo() throws Exception {
		StringDescription description = new StringDescription();

		reflectiveEqualTo(new GenericTestObject("str")).describeTo(description);

		assertThat(description.toString(), containsString("should reflectively equal the given object:"));
		assertThat(description.toString(), WildcardStringMatcher.containsPattern("GenericTestObject*{*o: \"str\"*}"));
	}

	@Test
	public void testPrimitivesAndStrings() throws Exception {
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject()), is(true));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withBo(false)), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withB((byte) 0)), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withS((short) 0)), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withI(0)), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withL(0)), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withF(0)), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withD(0)), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withC('0')), is(false));
		assertThat(reflectiveEqualTo(new TestObject()).matches(new TestObject().withStr("null")), is(false));
	}

	@Test
	public void testClass() throws Exception {
		assertThat(reflectiveEqualTo(new TestObjectWithClass()).matches(new TestObjectWithClass()), is(true));
		assertThat(reflectiveEqualTo(new TestObjectWithClass()).matches(new TestObjectWithClass().withClazz(TestObject.class)), is(false));
	}

	@Test
	public void testCustomBaseTypes() throws Exception {
		assertThat(reflectiveEqualTo(new TestObjectWithCustomBaseTypes()).matches(new TestObjectWithCustomBaseTypes()), is(false));
		assertThat(reflectiveEqualTo(new TestObjectWithCustomBaseTypes()).withBaseTypes(CompareObject.class).matches(new TestObjectWithCustomBaseTypes()), is(true));
	}

	@Test
	public void testFieldWithNullValues() throws Exception {
		assertThat(reflectiveEqualTo(new GenericTestObject(null)).matches(new GenericTestObject(null)), is(true));
		assertThat(reflectiveEqualTo(new GenericTestObject(null)).matches(new GenericTestObject("str")), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject("str")).matches(new GenericTestObject(null)), is(false));
	}

	@Test
	public void testFieldWithArrays() throws Exception {
		assertThat(reflectiveEqualTo(new GenericTestObject(new int[] { 42, 43 })).matches(new GenericTestObject(new int[] { 42, 43 })), is(true));
		assertThat(reflectiveEqualTo(new GenericTestObject(new long[] { 43 })).matches(new GenericTestObject(new long[] { 42 })), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(new char[] { 'a' })).matches(new GenericTestObject(new char[] { 'x' })), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(new long[] { 42 })).matches(new GenericTestObject(new int[] { 42 })), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(new byte[] { 1, 2 })).matches(new GenericTestObject(new byte[] { 2 })), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(new Object[] { "str" })).matches(new GenericTestObject(new String[] { "str" })), is(true));
	}

	@Test
	public void testFieldWithCollections() throws Exception {
		assertThat(reflectiveEqualTo(new GenericTestObject(asList(42, 43))).matches(new GenericTestObject(asList(42, 43))), is(true));
		assertThat(reflectiveEqualTo(new GenericTestObject(asList(42))).matches(new GenericTestObject(asList(43))), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(asList(42, 43))).matches(new GenericTestObject(asList(42))), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(asList(42))).matches(new GenericTestObject(asList(42l))), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(asList("str"))).matches(new GenericTestObject(singleton("str"))), is(true));
	}

	@Test
	public void testFieldWithRecursion() throws Exception {
		assertThat(reflectiveEqualTo(new GenericTestObject(new GenericTestObject("str"))).matches(new GenericTestObject(new GenericTestObject("str"))), is(true));
		assertThat(reflectiveEqualTo(new GenericTestObject(new GenericTestObject("str"))).matches(new GenericTestObject(new OtherTestObject("str"))), is(false));
		assertThat(reflectiveEqualTo(new GenericTestObject(new GenericTestObject("str"))).matches(new GenericTestObject(new GenericTestObject(42))), is(false));
	}

	@Test
	public void testDifferentClasses() throws Exception {
		assertThat(reflectiveEqualTo(new GenericTestObject(null)).matches(new OtherTestObject(null)), is(false));
	}

	@Test
	public void testFieldExclusion() throws Exception {
		assertThat(reflectiveEqualTo(new BidimensionalTestObject("str", false)).matches(new BidimensionalTestObject("str", true)), is(false));
		assertThat(reflectiveEqualTo(new BidimensionalTestObject("str", false))
			.excluding("p")
			.matches(new BidimensionalTestObject("str", true)), is(true));
	}

	@Test
	public void testFieldWithDuplicateRecursion() throws Exception {
		GenericTestObject str = new GenericTestObject("str");
		assertThat(reflectiveEqualTo(new BidimensionalTestObject(str, str)).matches(new BidimensionalTestObject(str, str)), is(true));
	}

	@Test
	public void testFieldWithCyclicRecursion() throws Exception {
		GenericTestObject str1 = new GenericTestObject("str");
		GenericTestObject str2 = new GenericTestObject(str1);
		str1.setO(str2);
		assertThat(reflectiveEqualTo(new BidimensionalTestObject(str1, str2)).matches(new BidimensionalTestObject(str1, str2)), is(true));
	}

	@Test
	public void testErrorMessagesOnFlat() throws Exception {
		ReflectiveEqualsMatcher<? super CrypticTestObject> matcher = reflectiveEqualTo(new CrypticTestObject("str1"));
		

		StringDescription description = new StringDescription();
		matcher.describeTo(description);
		matcher.describeMismatch(new CrypticTestObject("str2"), description);

		assertThat(description.toString(), containsPattern("CrypticTestObject*{*o: \"str1\""));
		assertThat(description.toString(), containsPattern("CrypticTestObject*{*o: \"str2\""));
	}

	@Test
	public void testErrorMessagesOnNested() throws Exception {
		ReflectiveEqualsMatcher<? super CrypticTestObject> matcher = reflectiveEqualTo(new CrypticTestObject(new CrypticTestObject("str1")));
		
		StringDescription description = new StringDescription();
		matcher.describeTo(description);
		matcher.describeMismatch(new CrypticTestObject(new CrypticTestObject("str2")), description);
		
		assertThat(description.toString(), containsPattern("CrypticTestObject*{*CrypticTestObject*{*o: \"str1\""));
		assertThat(description.toString(), containsPattern("CrypticTestObject*{*CrypticTestObject*{*o: \"str2\""));
	}
	
	@Test
	public void testErrorMessagesOnDeeplyNested() throws Exception {
		ReflectiveEqualsMatcher<? super CrypticTestObject> matcher = reflectiveEqualTo(new CrypticTestObject(new CrypticTestObject(new CrypticTestObject("str1"))));
		
		StringDescription description = new StringDescription();
		matcher.describeTo(description);
		matcher.describeMismatch(new CrypticTestObject(new CrypticTestObject(new CrypticTestObject("str2"))), description);
		
		assertThat(description.toString(), containsPattern("CrypticTestObject*{*CrypticTestObject*{*CrypticTestObject*{*o: \"str1\""));
		assertThat(description.toString(), containsPattern("CrypticTestObject*{*CrypticTestObject*{*CrypticTestObject*{*o: \"str2\""));
	}
	
	private static class GenericTestObject {
		private Object o;

		public GenericTestObject(Object o) {
			this.o = o;
		}
		
		public void setO(Object o) {
			this.o = o;
		}

		@Override
		public String toString() {
			return "{o:" + o.toString() + "}";
		}
	}
	
	private static class CrypticTestObject {
		private Object o;
		
		public CrypticTestObject(Object o) {
			this.o = o;
		}
		
		public void setO(Object o) {
			this.o = o;
		}
	}

	private static class OtherTestObject {
		private Object o;

		public OtherTestObject(Object o) {
			this.o = o;
		}

		public void setO(Object o) {
			this.o = o;
		}

		@Override
		public String toString() {
			return "{o:" + o.toString() + "}";
		}
	}

	private static class BidimensionalTestObject {
		private Object o;
		private Object p;

		public BidimensionalTestObject(Object o, Object p) {
			this.o = o;
			this.p = p;
		}

		public void setO(Object o) {
			this.o = o;
		}

		public void setP(Object p) {
			this.p = p;
		}
		
		@Override
		public String toString() {
			return "{o:" + o.toString() + "}";
		}
	}

	private static class TestObjectWithCustomBaseTypes {
		private CompareObject o = new CompareObject();

		public TestObjectWithCustomBaseTypes withO(CompareObject o) {
			this.o = o;
			return this;
		}
	}

	private static class CompareObject {
		private static int COUNTER = 0;

		private int counter = COUNTER++;

		@Override
		public boolean equals(Object obj) {
			return true;
		}
	}

	private static class TestObjectWithClass {
		private Class<?> clazz = TestObjectWithClass.class;

		public TestObjectWithClass withClazz(Class<?> clazz) {
			this.clazz = clazz;
			return this;
		}
	}

	private static class TestObject {

		private boolean bo = true;
		private byte b = 1;
		private short s = 2;
		private int i = 3;
		private long l = 4;
		private float f = 5.0f;
		private double d = 6.0;
		private char c = '7';
		private String str = "eight";

		public TestObject withBo(boolean bo) {
			this.bo = bo;
			return this;
		}

		public TestObject withB(byte b) {
			this.b = b;
			return this;
		}

		public TestObject withS(short s) {
			this.s = s;
			return this;
		}

		public TestObject withI(int i) {
			this.i = i;
			return this;
		}

		public TestObject withL(long l) {
			this.l = l;
			return this;
		}

		public TestObject withF(float f) {
			this.f = f;
			return this;
		}

		public TestObject withD(double d) {
			this.d = d;
			return this;
		}

		public TestObject withC(char c) {
			this.c = c;
			return this;
		}

		public TestObject withStr(String str) {
			this.str = str;
			return this;
		}

	}

}
