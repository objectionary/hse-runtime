package org.eolang;

import org.eolang.core.EOObject;
import org.eolang.core.data.EODataObject;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for {@link EOstring}
 */
class EOstringTest {

    /***
     * Test for datization
     * Checks if the data is returned
     */
    @Test
    void _getData() {
        EOstring string = new EOstring("Hello");
        MatcherAssert.assertThat(string._getData().toString(), Matchers.equalTo("Hello"));
    }

    /***
     * Test for {@code EOtrim}
     * checks if a string value with spaces on sides gets trimmed
     */
    @Test
    void EOtrim() {
        EOstring string = new EOstring(" Hello ");
        MatcherAssert.assertThat(
                string.EOtrim()._getData().toString(),
                Matchers.equalTo("Hello")
        );
    }

    /***
     * Test for {@code EOsubstring}
     */
    @Test
    void EOsubstring() {
        EOstring string = new EOstring("one two three");
        MatcherAssert.assertThat(
                string.EOsubstring(new EOint(0L), new EOint(3L))._getData().toString(),
                Matchers.equalTo("one")
        );
        MatcherAssert.assertThat(
                string.EOsubstring(new EOint(4L), new EOint(7L))._getData().toString(),
                Matchers.equalTo("two")
        );
        MatcherAssert.assertThat(
                string.EOsubstring(new EOint(8L), new EOint(13L))._getData().toString(),
                Matchers.equalTo("three")
        );
        assertThrows(InvalidParameterException.class, () -> string.EOsubstring(new EOObject() {}, new EOObject() {}));
        assertThrows(IndexOutOfBoundsException.class, () -> string.EOsubstring(new EOint(4L), new EOint(25L)));
        assertThrows(IndexOutOfBoundsException.class, () -> string.EOsubstring(new EOint(-4L), new EOint(13L)));
    }

    /***
     * Test for {@code EOsplit}
     */
    @Test
    void EOsplit() {
        EOstring string = new EOstring("|one|two|three|");
        EOarray strArray = string.EOsplit(new EOstring("|"));
        MatcherAssert.assertThat(
                strArray.EOlength()._getData().toInt(),
                Matchers.equalTo(3L)
        );
        MatcherAssert.assertThat(
                strArray.EOget(new EOint(0L))._getData().toString(),
                Matchers.equalTo("one")
        );
        MatcherAssert.assertThat(
                strArray.EOget(new EOint(1L))._getData().toString(),
                Matchers.equalTo("two")
        );
        MatcherAssert.assertThat(
                strArray.EOget(new EOint(2L))._getData().toString(),
                Matchers.equalTo("three")
        );
    }

    /***
     * Test for {@code EOappend}
     */
    @Test
    void EOappend() {
        String one = "one";
        String two = "two";
        String three = "three";
        String res = "onetwothree";
        EOstring string1 = new EOstring(one);
        EOstring string2 = new EOstring(two);
        EOstring string3 = new EOstring(three);
        MatcherAssert.assertThat(
                string1.EOappend(string2, string3)._getData().toString(),
                Matchers.equalTo(res)
        );
    }

    /***
     *Test for {@code EOtoInt}
     * checks if the int value of a string number is returned
     */
    @Test
    void toInt() {
        EOstring string = new EOstring("12");
        MatcherAssert.assertThat(string.EOtoInt()._getData().toInt(), Matchers.equalTo(12L));
    }

    /***
     *Test for {@code EOeq}
     * checks if the base string value is equal to another string
     */
    @Test
    void EOeq() {
        EOstring string = new EOstring("Hello");
        MatcherAssert.assertThat(
                string.EOeq(
                        new EODataObject("Hello")
                )._getData().toBoolean(),
                Matchers.equalTo(true)
        );
    }
}