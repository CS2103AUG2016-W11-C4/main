//@@author A0148096W

package teamfour.tasc.model.task.comparators;

import java.util.Comparator;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * Compares the ReadOnlyTask in descending lexicographical order.
 */
public class ZToAComparator implements Comparator<ReadOnlyTask> {
    
    @Override
    public int compare(ReadOnlyTask a, ReadOnlyTask b) {
        return b.getName().getName().compareTo(a.getName().getName());
    }
}