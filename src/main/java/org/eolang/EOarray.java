package org.eolang;

import org.eolang.core.EOObject;
import org.paukov.combinatorics3.Generator;

import java.util.List;
import java.util.Objects;

/**
 * Represents an array data structure.
 */
public class EOarray extends EOObject {

    /**
     * The underlying data structure behind this array is a Java List.
     * Effectively, the {@code _array} is an unmodifiable list since both
     * constructors of this class rely on the {@code List.of()} method.
     */
    private final List<EOObject> _array;

    /**
     * Instantiates an empty array.
     */
    public EOarray() {
        _array = List.of();
    }

    /**
     * Instantiates a non-empty array.
     *
     * @param objects contents of the array being instantiated.
     */
    public EOarray(EOObject... objects) {
        _array = List.of(objects);
    }

    /**
     * Appends {@code obj} to the end of this array.
     * <p>
     * This operation does not mutate the original array.
     * Instead, it produces a copy of this array and appends {@code obj} to the end of it.
     *
     * @return a copy of this array with {@code obj} appended as its last element.
     */
    public EOarray EOappend(EOObject obj) {
        EOObject[] newArray = new EOObject[_array.size() + 1];
        System.arraycopy(_array.toArray(), 0, newArray, 0, _array.size());
        newArray[_array.size()] = obj;
        return new EOarray(newArray);
    }

    /**
     * If {@code obj} is an EOarray appends item of {@code obj} to to the end of this array.
     * Otherwise appends {@code obj} to the end of this array.
     * <p>
     * This operation does not mutate the original array.
     * Instead, it produces a copy of this array and appends {@code obj} to the end of it.
     *
     * @return a copy of this array with {@code obj} appended as its last element.
     */
    public EOarray EOappendAll(EOObject obj) {
        try {
            int array2Size = obj._getAttribute("EOlength")._getData().toInt().intValue();
            if (array2Size > 0) {
                EOObject[] newArray;
                newArray = new EOObject[_array.size() + array2Size];
                System.arraycopy(_array.toArray(), 0, newArray, 0, _array.size());
                for (int i = _array.size(); i < newArray.length; ++i) {
                    newArray[i] = obj._getAttribute("EOget", new EOint(i - _array.size()));
                }
                return new EOarray(newArray);
            }
            return this;
        } catch (Exception e) {
            return EOappend(obj);
        }
    }

    /**
     * Evaluates {@code evaluatorObject} against each element of this array. Results of evaluations are not considered.
     * This method always returns {@code true}. Basically, this method is useful to dataize (in other words, execute or
     * evaluate) some routine against each element of an array when results are not needed.
     *
     * @param evaluatorObject an EO object that must have an {@code each} attribute which must have a free attribute
     *                        that receives the current element being utilized by {@code evaluatorObject}.
     *                        The name of the free attribute does not matter and may be chosen freely.
     *                        The {@code each} attribute must bind an expression to be evaluated to {@code @}.
     * @return {@code true}.
     */
    public EObool EOeach(EOObject evaluatorObject) {
        for (EOObject current : _array) {
            evaluatorObject._getAttribute("EOeach", current)._getData();
        }
        return new EObool(true);
    }

    /**
     * Retrieves the element at the position {@code i} of this array.
     *
     * @param i an index of the element to be fetched.
     * @return an element at the position {@code i}.
     * @throws IndexOutOfBoundsException if {@code i} is out of bounds of this array
     *                                   (i.e., {@code array.length <= i < 0}).
     */
    public EOObject EOget(EOObject i) {
        int position = i._getData().toInt().intValue();
        if (position >= _array.size() || position < 0) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Cannot retrieve the element at the position %d of the following array: %s. The index is out of bounds.",
                            position,
                            this
                    )
            );
        }
        return _array.get(position);
    }

    /**
     * Determines if this array is empty.
     *
     * @return {@code true} if this array is empty, otherwise {@code false}.
     */
    public EObool EOisEmpty() {
        return new EObool(_array.isEmpty());
    }

    /**
     * Retrieves the length of this array.
     *
     * @return an {@code int} representing the length of this array.
     */
    public EOint EOlength() {
        return new EOint(_array.size());
    }

    /**
     * Transforms this array in accordance with {@code mapperObject}.
     * <p>
     * This operation does not mutate the original array.
     * Instead, it produces a copy of this array where each element is transformed with {@code mapperObject}.
     *
     * @param mapperObject an EO object that must have a {@code map} attribute which must have a free attribute
     *                     that receives the current element being transformed.
     *                     The name of the free attribute does not matter and may be chosen freely.
     *                     The {@code map} attribute must bind a transformation
     *                     technique (function) to its {@code @} attribute.
     * @return an {@code array} object containing mapped elements.
     */
    public EOarray EOmap(EOObject mapperObject) {
        int length = _array.size();
        EOObject[] mappedArray = new EOObject[length];
        for (int i = 0; i < length; i++) {
            mappedArray[i] = mapperObject._getAttribute("EOmap", _array.get(i))._getDecoratedObject();
        }
        return new EOarray(mappedArray);
    }

    /**
     * Transforms this array in accordance with {@code mapperObject}.
     * This variant of mapping considers indices while transforming elements.
     * <p>
     * This operation does not mutate the original array.
     * Instead, it produces a copy of this array where each element is transformed with {@code mapperObject}.
     *
     * @param mapperObject an EO object that must have a {@code mapi} attribute which must have two free attributes:
     *                     1. The first free attribute receives the current element being transformed.
     *                     2. The second free attribute receives the index of the current element being transformed.
     *                     The order of the free attributes matters, and their names do not.
     *                     The {@code mapi} attribute must bind a transformation
     *                     technique (function) to its {@code @} attribute.
     * @return an {@code array} object containing mapped elements.
     */
    public EOarray EOmapi(EOObject mapperObject) {
        int length = _array.size();
        EOObject[] mappedArray = new EOObject[length];
        for (int i = 0; i < length; i++) {
            mappedArray[i] = mapperObject._getAttribute("EOmapi", _array.get(i), new EOint(i))._getDecoratedObject();
        }
        return new EOarray(mappedArray);
    }

    /**
     * Finds the position of the first minimum element in this array thanks to the {@code comparator} object.
     *
     * This implementation assumes that:
     *   1. Empty arrays have no minimum element (thus, this object evaluates to -1).
     *   2. One-element arrays have their minimums at the 0th position, meaning the first element is the minimum.
     *
     * @param comparator an EO object that must have a {@code comparator} attribute which must have two free attributes:
     *                      1. The first free attribute receives the A element to be compared with B.
     *                      2. The second attribute receives the index of the A element.
     *                         The index is provided in case if it is needed for comparison.
     *                      3. The third free attribute receives the B element to be compared with A.
     *                      4. The fourth free attribute receives the index of the B element.
     *                         The index is provided in case if it is needed for comparison.
     *                      The order of the free attributes matters, and their names do not.
     *                      The {@code comparator} attribute must bind a comparison technique (function) to {@code @}.
     *                      The comparison technique must:
     *                          1. Evaluate to 0 iff A = B (objects A and B are considered equal).
     *                          2. Evaluate to -1 iff A < B (object A is considered less than object B).
     *                          3. Evaluate to 1 iff A > B (object A is considered greater than object B).
     * @return the position of the first minimum element or -1 if all elements are equal.
     */
    public EOint EOmin(EOObject comparator) {
        // empty arrays have no minimums
        if (_array.isEmpty()) {
            return new EOint(-1);
        }
        // one-element arrays have their minimums at the 0th position
        if (_array.size() == 1) {
            return new EOint(0);
        }
        // consider the first element as the minimum
        int currentMinIndex = 0;
        EOObject currentMin = _array.get(currentMinIndex);
        // try to find an element that would be less that the first
        boolean allEqual = true;
        for (int i = 1; i < _array.size(); i++) {
            EOObject comparison = comparator
                    ._getAttribute
                            (
                              "EOcomparator",
                                    currentMin,
                                    new EOint(currentMinIndex),
                                    _array.get(i),
                                    new EOint(i)
                            )._getDecoratedObject();
            long comparisonResult = comparison._getData().toInt();
            if (comparisonResult != 0) {
                allEqual = false;
            }
            if (comparisonResult == 1) {
                currentMinIndex = i;
                currentMin = _array.get(currentMinIndex);
            }
        }

        if (allEqual) {
            return new EOint(-1);
        }
        else {
            return new EOint(currentMinIndex);
        }
    }

    /**
     * Retrieves all pairs of the elements of this array.
     * Resulting pairs are essentially 2-combinations with no repetitions (order is not taken into account).
     * Uniqueness of elements within pairs is not guaranteed (this method, however, guarantees that resulting pairs
     * themselves are unique regarding positions of included elements), so users of this method should consider
     * eliminating duplicates before retrieving pairs if unique elements within pairs are required (see examples below).
     * <p>
     * Example #1:
     * array([1, 2, 3]).pairs -> array([tuple(1, 2), tuple(1, 3), tuple(2, 3)])
     * Example #2:
     * array([1, 2, 2]).pairs -> array([tuple(1, 2), tuple(1, 2), tuple(2, 2)])
     *
     * @return an {@code array} of {@code tuple} objects with pairs of the elements of this array.
     */
    public EOarray EOpairs() {
        return new EOarray(
                Generator.combination(this._array.toArray(EOObject[]::new))
                        .simple(2)
                        .stream()
                        .map(pair -> new EOtuple(pair.get(0), pair.get(1)))
                        .toArray(EOObject[]::new)
        );
    }

    /**
     * Performs the operation of reduction of this array
     * (i.e., this method transforms this array into a single value in accordance with {@code reducerObject}).
     *
     * @param accumulator   an initial value of the accumulator.
     * @param reducerObject an EO object that must have a {@code reduce} attribute which must have two free attributes:
     *                      1. The first free attribute receives the current value of the accumulator.
     *                      2. The second free attribute receives the current element being operated over.
     *                      The order of the free attributes matters, and their names do not.
     *                      The {@code reduce} attribute must bind a reduction technique (function) to {@code @}.
     * @return the value of the accumulator after operating over the last element of this array (i.e., the result of reduction).
     */
    public EOObject EOreduce(EOObject accumulator, EOObject reducerObject) {
        EOObject out = accumulator;
        for (EOObject eoObject : _array) {
            out = reducerObject._getAttribute("EOreduce", out, eoObject)._getDecoratedObject();
        }
        return out;
    }

    /**
     * Performs the operation of reduction of this array
     * (i.e., this method transforms this array into a single value in accordance with {@code reducerObject}).
     * This variant of reduction considers indices while operating over elements.
     *
     * @param accumulator   an initial value of the accumulator.
     * @param reducerObject an EO object that must have a {@code reducei} attribute which must have three free attributes:
     *                      1. The first free attribute receives the current value of the accumulator.
     *                      2. The second free attribute receives the current element being operated over.
     *                      3. The third free attribute receives the index of the current element.
     *                      The order of the free attributes matters, and their names do not.
     *                      The {@code reducei} attribute must bind a reduction technique (function) to {@code @}.
     * @return the value of the accumulator after operating over the last element of this array (i.e., the result of reduction).
     */
    public EOObject EOreducei(EOObject accumulator, EOObject reducerObject) {
        EOObject out = accumulator;
        int length = _array.size();
        for (int i = 0; i < length; i++) {
            out = reducerObject._getAttribute("EOreducei", out, _array.get(i), new EOint(i))._getDecoratedObject();
        }
        return out;
    }

    /**
     * Removes the element at the position {@code i} of this array.
     * @param i the position to change the element at.
     * @return a copy of this list with the ith element removed.
     * @throws IndexOutOfBoundsException if {@code i} is out of bounds of this array
     *                                   (i.e., {@code array.length <= i < 0}).
     */
    public EOarray EOremove(EOObject i) {
        // retrieve the position to change the value at
        int position = i._getData().toInt().intValue();
        // check if the position is correct
        if (position >= _array.size() || position < 0) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Cannot remove the element at the position %d of the following array: %s. The index is out of bounds.",
                            position,
                            this
                    )
            );
        }
        // copy the array removing the specified element
        EOObject[] newArray = new EOObject[_array.size()-1];
        int k = 0;
        for (int j = 0; j < _array.size(); j++) {
            if (j != position) {
                newArray[k] = _array.get(j);
                k += 1;
            }
        }
        return new EOarray(newArray);
    }

    /**
     * Replaces the element at the position {@code i} of this array with the new value {@code newValue}.
     * @param i the position to change the element at.
     * @param newValue the new value for the element.
     * @return a copy of this list with the ith element replaced to the new value.
     * @throws IndexOutOfBoundsException if {@code i} is out of bounds of this array
     *                                   (i.e., {@code array.length <= i < 0}).
     */
    public EOarray EOreplace(EOObject i, EOObject newValue) {
        // retrieve the position to change the value at
        int position = i._getData().toInt().intValue();
        // check if the position is correct
        if (position >= _array.size() || position < 0) {
            throw new IndexOutOfBoundsException(
                    String.format(
                            "Cannot replace the element at the position %d of the following array: %s with the new value %s. The index is out of bounds.",
                            position,
                            this,
                            newValue
                    )
            );
        }
        // copy the array replacing the specified element with the new value
        EOObject[] newArray = new EOObject[_array.size()];
        System.arraycopy(_array.toArray(), 0, newArray, 0, _array.size());
        newArray[position] = newValue;
        return new EOarray(newArray);
    }

    /**
     * !!!For testing purposes only!!!
     * <p>
     * Determines if this array is equal to the {@code o} object.
     * To do it, this method checks that the {@code o} object is an array
     * and it contains similar elements by delegating equality checks to
     * the elements themselves.
     * <p>
     * This method can be called only in the testing environment
     * since all methods within the EO environment have the 'EO' prefix.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EOarray eOarray = (EOarray) o;
        return _array.equals(eOarray._array);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_array);
    }

    /**
     * !!!For testing purposes only!!!
     * <p>
     * Produces a string that represents this array.
     * The resulting string has the following form:
     * array([elem1, elem2, elem3, elem4]),
     * where each elemN is converted to a string, too.
     * <p>
     * Example:
     * Say, an array has three int elements: 1, 2, 3.
     * Then, the string representation of the array is:
     * array([int(1), int(2), int(3)]).
     * <p>
     * This method can be called only in the testing environment
     * since all methods within the EO environment have the 'EO' prefix.
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("array([");
        for (EOObject o : _array) {
            sb.append(o.toString()).append(", ");
        }
        if (_array.size() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }
        sb.append("])");
        return sb.toString();
    }
}