package net.amygdalum.extensions.hamcrest.adaptors;

import static net.amygdalum.extensions.hamcrest.adaptors.WideningMatcher.widening;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.hamcrest.StringDescription;
import org.junit.jupiter.api.Test;

public class WideningMatcherTest {

    @Test
    public void testDescribeTo() throws Exception {
        StringDescription description = new StringDescription();
        
        widening(equalTo("x")).describeTo(description);
        
        assertThat(description.toString(), equalTo("\"x\""));
    }

    @Test
    public void testMatches() throws Exception {
        assertThat(widening(equalTo("x")).matches("x"), is(true));
        assertThat(widening(equalTo("x")).matches("y"), is(false));
    }

}
