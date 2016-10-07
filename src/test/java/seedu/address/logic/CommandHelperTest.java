package seedu.address.logic;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandHelper;
import seedu.address.model.task.Recurrence;

import org.junit.Test;

public class CommandHelperTest {

    @Test
    public void convertStringToMultipleDates_shortName_correct_date_month_year() {
        String date = "13 sep 2013";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);
        assertTrue(dates.get(0).getYear() == 2013 - 1900);

        String date2 = "13th sept 2000";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDate() == 13);
        assertTrue(dates2.get(0).getMonth() == 8);
        assertTrue(dates2.get(0).getYear() == 2000 - 1900);

        String date3 = "sept 13 1900";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDate() == 13);
        assertTrue(dates3.get(0).getMonth() == 8);
        assertTrue(dates3.get(0).getYear() == 1900 - 1900);
    }

    @Test
    public void convertStringToMultipleDates_fullName_correct_date_month() {
        String date = "thirteenth september";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);
    }

    @Test
    public void convertStringToMultipleDates_correct_dayOfWeek_timeOfDayInNumbers() {
        String date = "friday 7pm";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDay() == 5);
        assertTrue(dates.get(0).getHours() == 19);

        String date2 = "next wed 3 afternoon";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDay() == 3);
        assertTrue(dates2.get(0).getHours() == 15);

        String date3 = "this monday 1600 hours";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDay() == 1);
        assertTrue(dates3.get(0).getHours() == 16);
    }

    @Test
    public void convertStringToMultipleDates_timeOfDayInWords() {
        String date = "next friday seven evening";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDay() == 5);
        assertTrue(dates.get(0).getHours() == 19);

        String date2 = "3rd april five in the morning";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDate() == 3);
        assertTrue(dates2.get(0).getHours() == 5);

        String date3 = "6 april three pm";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDate() == 6);
        assertTrue(dates3.get(0).getHours() == 15);
    }

    @Test
    public void convertStringToMultipleDates_yearWithTime() {
        String date = "6 april 2004 3 pm";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 6);
        assertTrue(dates.get(0).getHours() == 15);
        assertTrue(dates.get(0).getYear() == 2004 - 1900);
    }

    @Test
    public void convertStringToMultipleDates_getsMultipleDates() {
        String date = "20 aug and 17 april";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.size() == 2);
    }

    @Test
    public void convertStringToDate_shortName_correct_date_month() {
        String dateString = "13 sep";
        Date date;
        try {
            date = CommandHelper.convertStringToDate(dateString);
            assertTrue(date.getDate() == 13);
            assertTrue(date.getMonth() == 8);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertStringToDate_multipleDates_exceptionThrown(){
        String dateString = "13 sep and 14 oct";
        Date date;
        try {
            date = CommandHelper.convertStringToDate(dateString);
            fail("Exception expected");
        } catch (Exception e) {

        }
    }

    @Test
    public void getRecurrence_validInput(){
        String repeatParameter = "weekly 3";
        Recurrence recurrence;
        try {
            recurrence = CommandHelper.getRecurrence(repeatParameter);
            assertEquals("WEEKLY [3 time(s)]", recurrence.toString());
        } catch (IllegalValueException e) {
            fail();
        }

    }

}
