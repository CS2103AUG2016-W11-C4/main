package teamfour.tasc.model.keyword;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class KeywordTest {

    @Test
    public void keyword_keywordObjects_allKeywordsMatchWhatUserWillUse() {
        assertEquals("add", AddCommandKeyword.keyword);
        assertEquals("by", ByKeyword.keyword);
        assertEquals("calendar", CalendarCommandKeyword.keyword);
        assertEquals("clear", ClearCommandKeyword.keyword);
        assertEquals("collapse", CollapseCommandKeyword.keyword);
        assertEquals("complete", CompleteCommandKeyword.keyword);
        assertEquals("day", DayKeyword.keyword);
        assertEquals("delete", DeleteCommandKeyword.keyword);
        assertEquals("exit", ExitCommandKeyword.keyword);
        assertEquals("expand", ExpandCommandKeyword.keyword);
        assertEquals("find", FindCommandKeyword.keyword);
        assertEquals("from", FromKeyword.keyword);
        assertEquals("help", HelpCommandKeyword.keyword);
        assertEquals("hide", HideCommandKeyword.keyword);
        assertEquals("list", ListCommandKeyword.keyword);
        assertEquals("on", OnKeyword.keyword);
        assertEquals("redo", RedoCommandKeyword.keyword);
        assertEquals("relocate", RelocateCommandKeyword.keyword);
        assertEquals("renamelist", RenameListCommandKeyword.keyword);
        assertEquals("repeat", RepeatKeyword.keyword);
        assertEquals("select", SelectCommandKeyword.keyword);
        assertEquals("show", ShowCommandKeyword.keyword);
        assertEquals("sort", SortKeyword.keyword);
        assertEquals("switchlist", SwitchListCommandKeyword.keyword);
        assertEquals("tag", TagKeyword.keyword);
        assertEquals("today", TodayKeyword.keyword);
        assertEquals("to", ToKeyword.keyword);
        assertEquals("undo", UndoCommandKeyword.keyword);
        assertEquals("update", UpdateCommandKeyword.keyword);
        assertEquals("week", WeekKeyword.keyword);
    }

}
