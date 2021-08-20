package org.eolang;

import org.eolang.core.EOObject;
import org.eolang.core.data.EOData;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/***
 * Represents a string type
 * @version %I%, %G%
 */
public class EOstring extends EOObject {
    private final String stringValue;

    public EOstring() {
        stringValue = "";
    }

    public EOstring(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public EOData _getData() {
        return new EOData(stringValue);
    }

     /***
     * Appends strings to the end of string
      * @param rightStrings set of strings for concatenation
     * @return An object representing the concatenation of given string and {@code rightString}
     */
    public EOstring EOappend(EOObject... rightStrings) {
        StringBuilder sb = new StringBuilder(stringValue);
        try{
            for (EOObject str : rightStrings) {
                sb.append(str._getData().toString());
            }
        }catch (Exception e){
            throw new InvalidParameterException();
        }
        return new EOstring(sb.toString());
    }

    /***
     * Returns a new string that is a substring of this string
     * @param begin - the beginning index, inclusive.
     * @param end - the ending index, exclusive.
     * @return the specified substring.
     */
    public EOstring EOsubstring(EOObject begin, EOObject end) {
        try{
            return new EOstring(stringValue.substring(begin._getData().toInt().intValue(), end._getData().toInt().intValue()));
        }catch (IndexOutOfBoundsException  e){
            throw new IndexOutOfBoundsException();
        }catch (Exception e){
            throw new InvalidParameterException();
        }
    }

    /***
     * Trims this string on both sides
     * @return An object representing the trimmed value of this string
     */
    public EOstring EOtrim() {
        return new EOstring(stringValue.trim());
    }

    /***
     * Splits a string by a separator
     * @param separator - the boundary string object.
     * @return the array of string objects computed by splitting this string on boundaries formed by the separator.
     */
    public EOarray EOsplit(EOObject separator){
        List<EOstring> strList = new ArrayList<>();
        int i = 0;
        int j;
        while ((j = stringValue.indexOf(separator._getData().toString(), i)) >= 0) {
            if(j>0)
                strList.add(new EOstring(stringValue.substring(i, j)));
            i = j + 1;
        }
        String last = stringValue.substring(i);
        if(last.length() > 0)
            strList.add(new EOstring(last));
        return new EOarray(strList.toArray(EOstring[]::new));
    }

    /***
     * Makes an integer type of this string
     * @return An object representing the integer value of this string
     */
    public EOint EOtoInt() {
        return new EOint(Long.parseLong(stringValue));
    }

    /***
     * Parses a floating-point number from this string
     * @return An object representing the float value of this string
     */
    public EOfloat EOtoFloat() {
        return new EOfloat(Double.parseDouble(stringValue));
    }

    /***
     * Compares this string to the {@code rightString} free attribute
     * @param rightString a string to compare with
     * @return An object representing the truth value of the comparison of this string with the {@code rightString} free attribute
     */
    public EObool EOeq(EOObject rightString) {
        return new EObool(stringValue.equals(rightString._getData().toString()));
    }

    /**
     * !!!For testing purposes only!!!
     *
     * Determines if this object is equal to the {@code o} object.
     * To do it, this method checks that the {@code o} object is
     * of the {@code EOObject} type and its dataization result is the same
     * as the result of dataization of this object by delegating the check
     * to the standard {@code string.eq} attribute. This is a simplified
     * equality check sufficient for checking equality of runtime object
     * for testing purposes.
     *
     * This method can be called only in the testing environment
     * since all methods within the EO environment have the 'EO' prefix.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof EOObject)) return false;
        EOObject eoObject = (EOObject) o;
        return this.EOeq(eoObject)._getData().toBoolean();
    }

    /**
     * !!!For testing purposes only!!!
     *
     * Produces a string that represents this object.
     * The resulting string has the following form:
     * "value".
     *
     * Example:
     * "this is an example string".
     *
     * This method can be called only in the testing environment
     * since all methods within the EO environment have the 'EO' prefix.
     */
    @Override
    public String toString() {
        return "\""+stringValue+"\"";
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringValue);
    }
}
