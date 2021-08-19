package org.eolang.util.ieee754;

import org.eolang.EOfloat;
import org.eolang.core.EOObject;

/**
 * Represents the IEEE754 double-precision floating-point arithmetic NaN special value (not a number).
 */
public class EOnan extends EOObject {
    @Override
    protected EOObject _decoratee() {
        return new EOfloat(Double.NaN);
    }
}
