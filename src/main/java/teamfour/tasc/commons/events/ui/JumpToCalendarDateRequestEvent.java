//@@author A0148096W

package teamfour.tasc.commons.events.ui;

import java.util.Date;

import teamfour.tasc.commons.events.BaseEvent;

/**
 * An event requesting calendar to jump to date given.
 */
public class JumpToCalendarDateRequestEvent extends BaseEvent {

    private final Date date;
    
    public JumpToCalendarDateRequestEvent(Date date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public Date getDate() {
        return date;
    }
}
