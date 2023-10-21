package utils;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;



/* 
 * Generic class to see if some value T is bounded by any range in a list
 * given by ArrayList<Entry<T, T>> where the key and values of each entry
 * are the lower and upper bounds respectively
 */
public class RangeChecker<T extends Comparable<T>> {
    public boolean isValueInRange(T value, ArrayList<SimpleEntry<T, T>> rangeList) {
        for (SimpleEntry<T, T> range : rangeList) {
            T lowerBound = range.getKey();
            T upperBound = range.getValue();

            if (value.compareTo(lowerBound) > 0 && value.compareTo(upperBound) < 0) {
                return true;
            }
        }
        return false;
    }
}