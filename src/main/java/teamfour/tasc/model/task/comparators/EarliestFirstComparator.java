//@@author A0148096W

package teamfour.tasc.model.task.comparators;

import java.util.Comparator;

import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * Compares the ReadOnlyTask in ascending dates.
 */
public class EarliestFirstComparator implements Comparator<ReadOnlyTask> {
    
    @Override
    public int compare(ReadOnlyTask a, ReadOnlyTask b) {
        int timeA = 0;
        if (a.getDeadline().hasDeadline()) {
            timeA = (int)(a.getDeadline().getDeadline().getTime() / 1000);
        } else if (a.getPeriod().hasPeriod()) {
            timeA = (int)(a.getPeriod().getStartTime().getTime() / 1000);
        }

        int timeB = 0;
        if (b.getDeadline().hasDeadline()) {
            timeB = (int)(b.getDeadline().getDeadline().getTime() / 1000);
        } else if (b.getPeriod().hasPeriod()) {
            timeB = (int)(b.getPeriod().getStartTime().getTime() / 1000);
        }

        return timeA - timeB;
    }
}