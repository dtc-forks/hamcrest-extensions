package net.amygdalum.extensions.hamcrest.conventions;

import static net.amygdalum.extensions.hamcrest.conventions.OrdinaryExceptionMatcher.matchesOrdinaryException;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

@SuppressWarnings("unused")
public class OrdinaryExceptionMatcherTest {

	@Test
	public void testDescribeTo() throws Exception {
		StringDescription description = new StringDescription();

		matchesOrdinaryException().describeTo(description);

		assertThat(description.toString(), equalTo("is exception with standard constructors (empty, cause, message)"));
	}

	@Test
	public void testMatchesNullFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = matchesOrdinaryException().matchesSafely(null, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("is null"));
	}

	@Test
	public void testMatchesNoConstructorExceptionFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionNoConstructor> matchesOrdinaryException().matchesSafely(ExceptionNoConstructor.class, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionNoConstructor() is not defined or not accessible "));
	}

	@Test
	public void testMatchesDefaultConstructorExceptionFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionDefaultConstructor> matchesOrdinaryException().matchesSafely(ExceptionDefaultConstructor.class, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionDefaultConstructor(String, Throwable) is not defined or not accessible "));
	}

	@Test
	public void testMatchesDefaultConstructor1ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionDefaultConstructorSettingCause> matchesOrdinaryException().matchesSafely(ExceptionDefaultConstructorSettingCause.class, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionDefaultConstructorSettingCause() has cause that is not null: RuntimeException"));
	}

	@Test
	public void testMatchesDefaultConstructor2ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionDefaultConstructorSettingMsg> matchesOrdinaryException().matchesSafely(ExceptionDefaultConstructorSettingMsg.class, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionDefaultConstructorSettingMsg() has message that is not null: msg"));
	}

	@Test
	public void testMatchesCauseMsgConstructorExceptionFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionCauseMsgConstructor> matchesOrdinaryException().matchesSafely(ExceptionCauseMsgConstructor.class, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionCauseMsgConstructor(String) is not defined or not accessible "));
	}

	@Test
	public void testMatchesCauseMsgConstructor1ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = OrdinaryExceptionMatcher.<ExceptionCauseMsgConstructorNotSettingCause> matchesOrdinaryException().matchesSafely(ExceptionCauseMsgConstructorNotSettingCause.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionCauseMsgConstructorNotSettingCause(String, Throwable) does not preserve cause: null"));
	}
	
	@Test
	public void testMatchesCauseMsgConstructor2ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = OrdinaryExceptionMatcher.<ExceptionCauseMsgConstructorNotSettingMsg> matchesOrdinaryException().matchesSafely(ExceptionCauseMsgConstructorNotSettingMsg.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionCauseMsgConstructorNotSettingMsg(String, Throwable) does not preserve message: null"));
	}
	
	@Test
	public void testMatchesMsgConstructorExceptionFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionMsgConstructor> matchesOrdinaryException().matchesSafely(ExceptionMsgConstructor.class, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionMsgConstructor(Throwable) is not defined or not accessible "));
	}

	@Test
	public void testMatchesMsgConstructor1ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionMsgConstructorSettingCause> matchesOrdinaryException().matchesSafely(ExceptionMsgConstructorSettingCause.class, description);

		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionMsgConstructorSettingCause(String) has cause that is not null: RuntimeException"));
	}

	@Test
	public void testMatchesMsgConstructor2ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = OrdinaryExceptionMatcher.<ExceptionMsgConstructorNotSettingMsg> matchesOrdinaryException().matchesSafely(ExceptionMsgConstructorNotSettingMsg.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionMsgConstructorNotSettingMsg(String) does not preserve message: null"));
	}
	
	@Test
	public void testMatchesAllConstructorsException() throws Exception {
		StringDescription description = new StringDescription();

		boolean matches = OrdinaryExceptionMatcher.<ExceptionAllConstructors> matchesOrdinaryException().matchesSafely(ExceptionAllConstructors.class, description);

		assertThat(matches, is(true));
	}

	@Test
	public void testMatchesCauseConstructor1ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = OrdinaryExceptionMatcher.<ExceptionCauseConstructorNotSettingCause> matchesOrdinaryException().matchesSafely(ExceptionCauseConstructorNotSettingCause.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionCauseConstructorNotSettingCause(Throwable) does not preserve cause: null"));
	}
	
	@Test
	public void testMatchesCauseConstructor2ExceptionFails() throws Exception {
		StringDescription description = new StringDescription();
		
		boolean matches = OrdinaryExceptionMatcher.<ExceptionCauseConstructorSettingMsg> matchesOrdinaryException().matchesSafely(ExceptionCauseConstructorSettingMsg.class, description);
		
		assertThat(matches, is(false));
		assertThat(description.toString(), equalTo("ExceptionCauseConstructorSettingMsg(Throwable) has message that is not the name of the cause exception: msg"));
	}
	
	private static class ExceptionNoConstructor extends Exception {

	}

	private static class ExceptionDefaultConstructor extends Exception {
		public ExceptionDefaultConstructor() {
		}
	}

	private static class ExceptionDefaultConstructorSettingCause extends Exception {
		public ExceptionDefaultConstructorSettingCause() {
			super(new RuntimeException());
		}
	}

	private static class ExceptionDefaultConstructorSettingMsg extends Exception {
		public ExceptionDefaultConstructorSettingMsg() {
			super("msg");
		}
	}

	private static class ExceptionCauseMsgConstructor extends Exception {
		public ExceptionCauseMsgConstructor() {
		}

		public ExceptionCauseMsgConstructor(String msg, Throwable cause) {
			super(msg, cause);
		}
	}

	private static class ExceptionCauseMsgConstructorNotSettingMsg extends Exception {
		public ExceptionCauseMsgConstructorNotSettingMsg() {
		}

		public ExceptionCauseMsgConstructorNotSettingMsg(String msg, Throwable cause) {
			super(null, cause);
		}
	}

	private static class ExceptionCauseMsgConstructorNotSettingCause extends Exception {
		public ExceptionCauseMsgConstructorNotSettingCause() {
		}
		
		public ExceptionCauseMsgConstructorNotSettingCause(String msg, Throwable cause) {
			super(msg, null);
		}
	}
	
	private static class ExceptionMsgConstructor extends Exception {
		public ExceptionMsgConstructor() {
		}

		public ExceptionMsgConstructor(String msg, Throwable cause) {
			super(msg, cause);
		}

		public ExceptionMsgConstructor(String msg) {
			super(msg);
		}
	}

	private static class ExceptionMsgConstructorNotSettingMsg extends Exception {
		public ExceptionMsgConstructorNotSettingMsg() {
		}
		
		public ExceptionMsgConstructorNotSettingMsg(String msg, Throwable cause) {
			super(msg, cause);
		}
		
		public ExceptionMsgConstructorNotSettingMsg(String msg) {
		}
	}
	
	private static class ExceptionMsgConstructorSettingCause extends Exception {
		public ExceptionMsgConstructorSettingCause() {
		}
		
		public ExceptionMsgConstructorSettingCause(String msg, Throwable cause) {
			super(msg, cause);
		}
		
		public ExceptionMsgConstructorSettingCause(String msg) {
			super(msg, new RuntimeException());
		}
	}
	
	private static class ExceptionAllConstructors extends Exception {
		public ExceptionAllConstructors() {
		}

		public ExceptionAllConstructors(String msg, Throwable cause) {
			super(msg, cause);
		}

		public ExceptionAllConstructors(String msg) {
			super(msg);
		}

		public ExceptionAllConstructors(Throwable cause) {
			super(cause);
		}
	}

	private static class ExceptionCauseConstructorNotSettingCause extends Exception {
		public ExceptionCauseConstructorNotSettingCause() {
		}
		
		public ExceptionCauseConstructorNotSettingCause(String msg, Throwable cause) {
			super(msg, cause);
		}
		
		public ExceptionCauseConstructorNotSettingCause(String msg) {
			super(msg);
		}
		
		public ExceptionCauseConstructorNotSettingCause(Throwable cause) {
		}
	}
	
	private static class ExceptionCauseConstructorSettingMsg extends Exception {
		public ExceptionCauseConstructorSettingMsg() {
		}
		
		public ExceptionCauseConstructorSettingMsg(String msg, Throwable cause) {
			super(msg, cause);
		}
		
		public ExceptionCauseConstructorSettingMsg(String msg) {
			super(msg);
		}
		
		public ExceptionCauseConstructorSettingMsg(Throwable cause) {
			super("msg", cause);
		}
	}
	
}
