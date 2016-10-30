package teamfour.tasc.logic.keyword;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KeywordTest {

    @Test
    public void keyword_keywordObjects_allKeywordsMatchWhatUserWillUse() {
        
        // If we don't create Keyword objects, (even though
        // they aren't necessary due to the statics), Coveralls will
        // complain that we never test the classes :(
        
        assertEquals("add", new AddCommandKeyword().keyword);
        assertEquals("by", new ByKeyword().keyword);
        assertEquals("calendar", new CalendarCommandKeyword().keyword);
        assertEquals("clear", new ClearCommandKeyword().keyword);
        assertEquals("collapse", new CollapseCommandKeyword().keyword);
        assertEquals("complete", new CompleteCommandKeyword().keyword);
        assertEquals("day", new DayKeyword().keyword);
        assertEquals("delete", new DeleteCommandKeyword().keyword);
        assertEquals("exit", new ExitCommandKeyword().keyword);
        assertEquals("expand", new ExpandCommandKeyword().keyword);
        assertEquals("find", new FindCommandKeyword().keyword);
        assertEquals("from", new FromKeyword().keyword);
        assertEquals("help", new HelpCommandKeyword().keyword);
        assertEquals("hide", new HideCommandKeyword().keyword);
        assertEquals("list", new ListCommandKeyword().keyword);
        assertEquals("on", new OnKeyword().keyword);
        assertEquals("redo", new RedoCommandKeyword().keyword);
        assertEquals("relocate", new RelocateCommandKeyword().keyword);
        assertEquals("renamelist", new RenameListCommandKeyword().keyword);
        assertEquals("repeat", new RepeatKeyword().keyword);
        assertEquals("select", new SelectCommandKeyword().keyword);
        assertEquals("show", new ShowCommandKeyword().keyword);
        assertEquals("sort", new SortKeyword().keyword);
        assertEquals("switchlist", new SwitchListCommandKeyword().keyword);
        assertEquals("tag", new TagKeyword().keyword);
        assertEquals("today", new TodayKeyword().keyword);
        assertEquals("to", new ToKeyword().keyword);
        assertEquals("undo", new UndoCommandKeyword().keyword);
        assertEquals("update", new UpdateCommandKeyword().keyword);
        assertEquals("week", new WeekKeyword().keyword);
    }

}
