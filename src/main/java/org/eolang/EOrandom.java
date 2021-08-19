package org.eolang;

import org.eolang.core.EOObject;

import java.util.concurrent.ThreadLocalRandom;

public class EOrandom extends EOObject {
    /***
     * Generates a random integer object from {@code minValue} to {@code maxValue} exclusive
     * @param minValue a minimum result value
     * @param maxValue a maximum result value
     * @return A random int object from {@code minValue} to {@code maxValue} exclusive
     */
    public EOint EOint(EOObject minValue, EOObject maxValue) {
        Long min = minValue._getData().toInt();
        Long max = maxValue._getData().toInt();
        if(max <= min) throw new IllegalArgumentException("maxValue must be greater than than minValue");
        return new EOint(ThreadLocalRandom.current().nextLong(min, max));
    }
}
