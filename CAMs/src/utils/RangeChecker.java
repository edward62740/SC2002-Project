package utils;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * The {@link RangeChecker} class is a generic class used to determine if a value of type {@code T} is
 * bounded by any range in a list. The ranges are represented by {@link SimpleEntry} instances in an
 * {@link ArrayList}, where the key and values of each entry are the lower and upper bounds respectively.
 *
 * @param <T> The type of values being checked against the ranges.
 */
public class RangeChecker<T> {
    private Comparator<T> comparator;

    /**
     * Constructs a {@link RangeChecker} with the specified comparator for comparing values of type {@code T}.
     * The comparator is expected to provide method compare(T o1, T o2) and return positive, negative or zero integer.
     *
     * @param comparator The comparator for comparing values of type {@code T}.
     */
    public RangeChecker(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    /**
     * Checks if a given value is within any of the specified ranges.
     * 
     * ranges --------   ---------            -------------
     * 
     * value to check           .
     *                          ^ return true
     *
     * @param value      The value to check against the ranges
     * @param rangeList  The list of ranges represented by {@link SimpleEntry} instances
     * @return true if the value is within any of the ranges, false  otherwise
     */
    public boolean isValueInRange(T value, ArrayList<SimpleEntry<T, T>> rangeList) {
        for (SimpleEntry<T, T> range : rangeList) {
            T lowerBound = range.getKey();
            T upperBound = range.getValue();

            if (comparator.compare(value, lowerBound) >= 0 && comparator.compare(value, upperBound) <= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if there is an intersection between two lists of ranges.
     * 
     * ranges1 --------   ---------            -------------
     * 
     * ranges2                  ---      ---------  ---
     *                          ^return true
     * 
     * @param rangeList1 The first list of ranges
     * @param rangeList2 The second list of ranges
     * @return true if there is an intersection between the two lists of ranges, false otherwise
     */
    public boolean isIntersect(ArrayList<SimpleEntry<T, T>> rangeList1, ArrayList<SimpleEntry<T, T>> rangeList2) {
        for (SimpleEntry<T, T> range1 : rangeList1) {
            for (SimpleEntry<T, T> range2 : rangeList2) {
                T lowerBound1 = range1.getKey();
                T upperBound1 = range1.getValue();
                T lowerBound2 = range2.getKey();
                T upperBound2 = range2.getValue();

                if (comparator.compare(upperBound1, lowerBound2) <= 0 || comparator.compare(lowerBound1, upperBound2) >= 0) {
                    // No intersection
                } else {
                    return true; // Ranges intersect
                }
            }
        }
        return false; // No intersection found
    }
}
