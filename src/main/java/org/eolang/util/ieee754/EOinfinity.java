package org.eolang.util.ieee754;

import org.eolang.EOfloat;
import org.eolang.core.EOObject;

/**
 * Represents the IEEE754 double-precision floating-point arithmetic positive infinity.
 * To get the negative infinity, one can negate the object through the float's neg attribute
 * or multiply it by a negative number.
 */
public class EOinfinity extends EOObject {
    @Override
    protected EOObject _decoratee() {
        return new EOfloat(Double.POSITIVE_INFINITY);
    }
}
