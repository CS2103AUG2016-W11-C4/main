package teamfour.tasc.model.task.qualifiers;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class QualifierTest {

    @Test
    public void qualifiers_toString_expectsQualifierAndArgument() {
        
        Date date1 = new Date(0);
        Date date2 = new Date(1);
        
        String string = "MyString";

        Set<String> stringSet = new HashSet<String>();
        stringSet.add("String1");
        stringSet.add("String2");

        assertEquals("deadline=" + date1.toString(), new DeadlineQualifier(date1).toString());
        assertEquals("endTime=" + date1.toString(), new EndTimeQualifier(date1).toString());
        assertEquals("startTime=" + date1.toString(), new StartTimeQualifier(date1).toString());
        assertEquals("startTime=" + date1.toString() + ",endTime=" + date2.toString(), 
                new StartToEndTimeQualifier(date1, date2).toString());
        
        assertEquals("type=" + string, new TypeQualifier(string).toString());
        assertEquals("tags=" + String.join(", ", stringSet), 
                new TagsQualifier(stringSet).toString());
        assertEquals("name or tags=" + String.join(", ", stringSet), 
                new NameOrTagsQualifier(stringSet).toString());
    }

}
