
package teamfour.tasc.logic.commands;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import teamfour.tasc.commons.core.LogsCenter;
import teamfour.tasc.commons.exceptions.IllegalValueException;
import teamfour.tasc.logic.parser.KeywordParser;
import teamfour.tasc.model.task.Recurrence;

public class CommandHelper {
    private static final Logger logger = LogsCenter.getLogger(CommandHelper.class);

    private static final String MESSAGE_INVALID_DATES = "Invalid dates";
    public static String MESSAGE_REPEAT_PARAMETERS_INVALID = "Invalid repeat parameters";

    //@@author A0148096W
    /**
     * Converts a String to Date if possible, otherwise returns null.
     * @param dateString the String containing the Date which may be null
     * @return the Date from String, or null if not possible
     */
    public static Date tryConvertStringToDateOrReturnNull(String dateString) {
        if (dateString != null) {
            try {
                return CommandHelper.convertStringToDate(dateString);
            } catch (IllegalValueException e) {
                logger.warning("Invalid date string in method " +
                            "convertStringToDateIfPossible: " + dateString);
                return null;
            }
        }
        return null;
    }

    /**
     * Precondition: date argument is not null.
     * Returns a Date object using date with time set to start of day.
     * @param date the Date object
     * @return a Date object set to start of the date
     */
    public static Date getStartOfTheDate(Date date) {
        assert date != null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * Precondition: date argument is not null.
     * Returns a Date object using date with time set to end of day.
     * @param date the Date object
     * @return a Date object set to end of the date
     */
    public static Date getEndOfTheDate(Date date) {
        assert date != null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 999);
        return c.getTime();
    }

    //@@author A0127014W
    /**
     * Parses date(s) from an input String containing the dates
     * Dates parsed include time of day, day of week, day of month, month of year, and year
     * Time of day can be in numerical 12 hours or 24 hours or word form (7pm or 1900 hours or seven in the evening)
     * Day of week can be in short or full (fri or friday)
     * Day of month can be in numerical or word form (13 or thirteenth)
     * Month of year can only be in word form, and can be in short(january or jan, but not 1)
     * Year must be in numerical form, and put after month and day
     *
     * @param dateInString  Input String containing the date(s)
     * @return dates    List of dates parsed from dateInString
     */
    public static List<Date> convertStringToMultipleDates(String dateInString){
        if(dateInString == null){
            dateInString = "";
        }
        List<Date> dates = new PrettyTimeParser().parse(dateInString);
        return dates;
    }

    /**
     * Like convertStringToMultipleDates(String dateInString), but only returns one Date
     * Throws exception if multiple dates/no dates can be parsed from one dateInString
     * If date given is today, and no time is set/time set is in the past, then time will be set to 11.59pm
     * @param dateInString
     * @return Date parsed from dateInString
     * @throws IllegalValueException
     */
    public static Date convertStringToDate(String dateInString) throws IllegalValueException{
        dateInString = convertStringIfTodayAndNoValidTime(dateInString);
        List<Date> dates = new PrettyTimeParser().parse(dateInString);
        if(dates.size() != 1){
            throw new IllegalValueException(MESSAGE_INVALID_DATES);
        }
        return dates.get(0);
    }

    /**
     * Converts dateInString to "today 11.59pm" if it contains "today",
     * and no time is given or time given is in the past
     * @param dateInString  String containing the date
     * @return dateInStringConverted    String containing the date, but converted accordingly
     * @throws IllegalValueException
     */
    private static String convertStringIfTodayAndNoValidTime(String dateInString) throws IllegalValueException {
        String dateInStringConverted = dateInString;
        if(dateInString.toLowerCase().contains("today")){
            List<Date> dates = new PrettyTimeParser().parse(dateInString);
            if(dates.size() != 1){
                throw new IllegalValueException(MESSAGE_INVALID_DATES);
            }
            Date today = new Date();
            Date parsedDate = dates.get(0);
            boolean isParsedDateEqualToOrEarlierThanToday = (today.getDate() == parsedDate.getDate())
                                                            && (today.after(parsedDate) || today.equals(parsedDate));
            if (isParsedDateEqualToOrEarlierThanToday) {
                dateInStringConverted = "today 11.59pm";
            }
        }
        return dateInStringConverted;
    }

    /**
     * Gets a Recurrence object from a string containing repeat parameters for recurrence
     * Input parameter repeatString includes the pattern of recurrence, and frequency of recurrence
     * @param repeatString  String containing repeat parameters
     * @return Recurrence object from repeatString
     * @throws IllegalValueException
     */
    public static Recurrence getRecurrence(String repeatString) throws IllegalValueException{
        KeywordParser kp = new KeywordParser("daily","weekly","monthly","yearly","none");
        HashMap<String, String> repeatParameters = kp.parseKeywordsWithoutFixedOrder(repeatString.toLowerCase());
        try {
            return getRecurrenceWithPatternFromParameters(repeatParameters);
        }
        catch (IllegalValueException|NumberFormatException e) {
            throw new IllegalValueException(MESSAGE_REPEAT_PARAMETERS_INVALID);
        }

    }
    /**
     * Creates and returns Recurrence object with Pattern based on repeatParameter
     * @param repeatParameters  HashMap with parameters parsed from string using recurrence keywords
     * @return recurrence   Recurrence object with Pattern matching repeatParameters
     * @throws IllegalValueException
     */
    private static Recurrence getRecurrenceWithPatternFromParameters(HashMap<String, String> repeatParameters)
            throws IllegalValueException, NumberFormatException {
        if (repeatParameters.containsKey("daily")) {
            Recurrence recurrence = new Recurrence(Recurrence.Pattern.DAILY,
                    Integer.parseInt(repeatParameters.get("daily")));
            return recurrence;
        } else if (repeatParameters.containsKey("weekly")) {
            Recurrence recurrence = new Recurrence(Recurrence.Pattern.WEEKLY,
                    Integer.parseInt(repeatParameters.get("weekly")));
            return recurrence;
        } else if (repeatParameters.containsKey("monthly")) {
            Recurrence recurrence = new Recurrence(Recurrence.Pattern.MONTHLY,
                    Integer.parseInt(repeatParameters.get("monthly")));
            return recurrence;
        } else if (repeatParameters.containsKey("yearly")) {
            Recurrence recurrence = new Recurrence(Recurrence.Pattern.YEARLY,
                    Integer.parseInt(repeatParameters.get("yearly")));
            return recurrence;
        } else if(repeatParameters.containsKey("none")){
            return new Recurrence();
        } else {
            throw new IllegalValueException(MESSAGE_REPEAT_PARAMETERS_INVALID);
        }
    }

    //@@author A0140011L
    /**
     * Convert the date object into a string that the pretty time parser
     * can actually understand.
     *
     * @param date to convert to
     * @return string that can be parsed by pretty time library
     */
    public static String convertDateToPrettyTimeParserFriendlyString(Date date) {
        return String.format("%tb %td %tY %tT", date, date, date, date);
    }
}
