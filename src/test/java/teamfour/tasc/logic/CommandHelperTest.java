package teamfour.tasc.logic;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.commands.CommandHelper;
import teamfour.tasc.model.task.Recurrence;

import org.junit.Test;

public class CommandHelperTest {

    //@@author A0148096W
    /*
     * Equivalence partitions for date strings:
     *  - null
     *  - empty string
     *  - invalid date string
     *  - valid date string
     */

    @Test
    public void tryConvertStringToDateOrReturnNull_inputNull_returnsNull() {
        String string = null;
        Date date = CommandHelper.tryConvertStringToDateOrReturnNull(string);
        assertTrue(date == null);
    }

    @Test
    public void tryConvertStringToDateOrReturnNull_inputEmptyString_returnsNull() {
        String string = "";
        Date date = CommandHelper.tryConvertStringToDateOrReturnNull(string);
        assertTrue(date == null);
    }

    @Test
    public void tryConvertStringToDateOrReturnNull_inputInvalidDateString_returnsNull() {
        String string = "invaliddatestring";
        Date date = CommandHelper.tryConvertStringToDateOrReturnNull(string);
        assertTrue(date == null);
    }

    @Test
    public void tryConvertStringToDateOrReturnNull_inputValidDateString_returnsDate() {
        String string = "13 sep 2013";
        Date date = CommandHelper.tryConvertStringToDateOrReturnNull(string);
        assertTrue(date.getDate() == 13);
        assertTrue(date.getMonth() == 8);
        assertTrue(date.getYear() == 2013 - 1900);
    }

    //@@author A0127014W
    /*
     *Equivalence partitions for date strings:
     *-Dates in numbers
     *-Dates in words
     *-Strings which do not contain dates
     *-Empty strings
     *-null
     */

    @Test
    public void convertStringToMultipleDates_invalidInputs() {
        String date = "";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.isEmpty());

        String date2 = "notADate";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.isEmpty());

        String date3 = null;
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.isEmpty());
    }

    @Test
    public void convertStringToMultipleDates_shortName_validInput_validOrdering() {
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
    }

    @Test
    public void convertStringToMultipleDates_fullName_validInput_validOrdering() {
        String date = "thirteenth september";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);

        String date2 = "september thirteenth 2013";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDate() == 13);
        assertTrue(dates2.get(0).getMonth() == 8);
        assertTrue(dates2.get(0).getYear() == 2013 - 1900);

    }

    @Test
    public void convertStringToMultipleDates_invalid_ordering() {
        String date = "2013 september thirteenth";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDate() == 13);
        assertTrue(dates.get(0).getMonth() == 8);
        assertTrue(dates.get(0).getYear() != 2013); //wrong year

        String date2 = "sep 1900 13th";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDate() != 13); //wrong date
        assertTrue(dates2.get(0).getMonth() == 8);
        assertTrue(dates2.get(0).getYear() == 1900 - 1900);

        String date3 = "4th april 1600 hours";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDate() == 4);
        assertTrue(dates3.get(0).getHours() != 16); //wrong time
    }

    @Test
    public void convertStringToMultipleDates_dayOfWeek_valid() {
        String date = "friday";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getDay() == 5);

        String date2 = "next wed";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDay() == 3);

        String date3 = "this monday";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDay() == 1);
    }

    @Test
    public void convertStringToMultipleDates_timeOfDayInNumbers_valid() {
        String date = "7pm";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getHours() == 19);

        String date2 = "3 afternoon next wed";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDay() == 3);
        assertTrue(dates2.get(0).getHours() == 15);

        String date3 = "1600 hours 4th april";
        List<Date> dates3 = CommandHelper.convertStringToMultipleDates(date3);
        assertTrue(dates3.get(0).getDate() == 4);
        assertTrue(dates3.get(0).getHours() == 16);
    }

    @Test
    public void convertStringToMultipleDates_timeOfDayInWords_valid() {
        String date = "seven evening";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.get(0).getHours() == 19);

        String date2 = "3rd april five pm";
        List<Date> dates2 = CommandHelper.convertStringToMultipleDates(date2);
        assertTrue(dates2.get(0).getDate() == 3);
        assertTrue(dates2.get(0).getHours() == 17);

    }

    @Test
    public void convertStringToMultipleDates_getsMultipleDates() {
        String date = "20 aug and 17 april";
        List<Date> dates = CommandHelper.convertStringToMultipleDates(date);
        assertTrue(dates.size() == 2);
    }

    @Test
    public void convertStringToDate_shortName_validInput() {
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

    @Test(expected = IllegalValueException.class)
    public void convertStringToDate_multipleDates_exceptionThrown() throws IllegalValueException{
        String dateString = "13 sep and 14 oct";
        Date date = CommandHelper.convertStringToDate(dateString);
    }

    @Test(expected = IllegalValueException.class)
    public void convertStringToDate_multipleRelativeDates_exceptionThrown() throws IllegalValueException{
        String dateString2 = "today and tomorrow";
        Date date2 = CommandHelper.convertStringToDate(dateString2);
    }

    @Test
    public void convertStringToDate_today_noTimeGiven_setTimeTo1159() {
        String dateString = "today";
        Date date;
        try {
            date = CommandHelper.convertStringToDate(dateString);
            assertTrue(date.getHours() == 23);
            assertTrue(date.getMinutes() == 59);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void convertStringToDate_today_timeGiven_setTimeToGiven_IfGivenIsLaterThanPresent() {
        String dateString = "today 7pm";
        Date testDate = new Date();
        testDate.setHours(19);
        Date date;
        try {
            date = CommandHelper.convertStringToDate(dateString);
            if(new Date().after(testDate) || (new Date().equals(testDate))){
                assertTrue(date.getHours() == 23);
                assertTrue(date.getMinutes() == 59);
            }
            else{
                assertTrue(date.getHours() == 19);
                assertTrue(date.getMinutes() == 0);
            }
        } catch (Exception e) {
            fail();
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

        String repeatParameter2 = "none 0";
        Recurrence recurrence2;
        try {
            recurrence2 = CommandHelper.getRecurrence(repeatParameter2);
            assertEquals("No recurrence.", recurrence2.toString());
        } catch (IllegalValueException e) {
            fail();
        }
    }

    @Test(expected = IllegalValueException.class)
    public void getRecurrence_invalidRepeatCount_throwsIllegalValueException() throws IllegalValueException{
        String repeatParameter = "weekly 0";
        Recurrence recurrence = CommandHelper.getRecurrence(repeatParameter);

    }

    @Test(expected = IllegalValueException.class)
    public void getRecurrence_emptyString_throwsIllegalValueException() throws IllegalValueException{
        String repeatParameter = "";
        Recurrence recurrence = CommandHelper.getRecurrence(repeatParameter);
    }

    @Test(expected = IllegalValueException.class)
    public void getRecurrence_InvalidString_throwsIllegalValueException() throws IllegalValueException{
        String repeatParameter = "invalidInput";
        Recurrence recurrence = CommandHelper.getRecurrence(repeatParameter);
    }

    //@@author A0140011L
    @Test
    public void convertDateToPrettyTimeParserFriendlyString_validInput() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2016, 0, 1, 0, 0, 0);
        Date input = calendar.getTime();
        String expectedOutput = "Jan 01 2016 00:00:00";

        assertEquals(expectedOutput,
                CommandHelper.convertDateToPrettyTimeParserFriendlyString(input));
    }
}
