//@@author A0148096W

package teamfour.tasc.model.task.comparators;

import java.util.Comparator;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * Compares the ReadOnlyTask in ascending lexicographical order.
 */
public class AToZComparator implements Comparator<ReadOnlyTask> {

    @Override
    public int compare(ReadOnlyTask a, ReadOnlyTask b) {
        return a.getName().getName().compareTo(b.getName().getName());
    }
}