package utils;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Comparator;

/* 
 * Generic class to see if some value T is bounded by any range in a list
 * given by ArrayList<Entry<T, T>> where the key and values of each entry
 * are the lower and upper bounds respectively
 */
public class RangeChecker<T> {
	private Comparator<T> comparator;

	public RangeChecker(Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public boolean isValueInRange(T value, ArrayList<SimpleEntry<T, T>> rangeList) {
		for (SimpleEntry<T, T> range : rangeList) {
			T lowerBound = range.getKey();
			T upperBound = range.getValue();

			if (comparator.compare(value, lowerBound) > 0 && comparator.compare(value, upperBound) < 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isIntersect(ArrayList<SimpleEntry<T, T>> rangeList1, ArrayList<SimpleEntry<T, T>> rangeList2) {
        for (SimpleEntry<T, T> range1 : rangeList1) {
            for (SimpleEntry<T, T> range2 : rangeList2) {
                T lowerBound1 = range1.getKey();
                T upperBound1 = range1.getValue();
                T lowerBound2 = range2.getKey();
                T upperBound2 = range2.getValue();

                if (comparator.compare(upperBound1, lowerBound2) <= 0 || comparator.compare(lowerBound1, upperBound2) >= 0) {

                } else {
                    return true; // Ranges intersect
                }
            }
        }
        return false; // No intersection found
    }
}