package org.eolang;

import org.eolang.core.EOObject;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for {@link EOrandom}
 */
public class EOrandomTest {
    /***
     * Test for {@code EOint}
     */
    @Test
    void EOint() {
        final EOint min = new EOint(0L);
        final EOint max = new EOint(10L);

        EOrandom random = new EOrandom();
        for(int i = 0; i < 10; i++ ){
            EOObject res = random.EOint(min, max);
            MatcherAssert.assertThat(
                    res._getData().toInt(),
                    Matchers.allOf(
                            Matchers.greaterThanOrEqualTo(min._getData().toInt()),
                            Matchers.lessThan(max._getData().toInt())

                    )
            );
        }

        final EOint min2 = new EOint(10L);
        assertThrows(IllegalArgumentException.class, () -> {
            final EOObject res2 = random.EOint(min2, min2);
            res2._getData().toInt();
        });

        final EOint min3 = new EOint(10L);
        final EOint max3 = new EOint(0L);
        assertThrows(IllegalArgumentException.class, () -> {
            final EOObject res3 = random.EOint(min3, max3);
            res3._getData().toInt();
        });
    }
}
