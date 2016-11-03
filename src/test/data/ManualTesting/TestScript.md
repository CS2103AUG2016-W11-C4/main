# Test Script (for Manual Testing)

## Loading the sample data

1. Ensure that you put `SampleData.xml` inside the `data\` folder (if the `data` directory does not exist inside the directory where the app is located, create it).
2. Start `TaSc.jar` by double clicking it.
3. Type `switchlist SampleData`.

**Result:** The task list is loaded.

## Open help

1. Type `help`

**Result:** A new window appears with a "cheatsheet" style page, listing out all the command available.

## Add new floating task

1. Type `add "Read the newspaper"

**Result:** A new floating task for "Read the newspaper" is created at the bottom of the list.

## Add new task with deadline

1. Type `add "Finish movie editing" by 6pm`

**Result:** A new task "Finish movie editing" with deadline 6pm TODAY is created at the bottom of the list. The calendar is updated to highlight the new task in yellow.

## Add new event

1. Type `add "Visit friend's home" from tomorrow 6pm to 9pm`.

**Result:** A new task "Visit friend's home" with period of 6pm to 9pm tomorrow is created at the bottom of the list. The calendar is updated to highlight the new task in blue.

## Add recurring tasks

1. Type `add "Submit daily report" by 5pm repeat daily 5`.

**Result:** A new task "Submit daily report" with deadline 5pm everyday for 5 times is added (only once) to the task list. The calendar itself displays multiple instances of the same task, on the daily basis.

## Add tags to task

1. Type `add "Very Important Stuff" tag Important`.

**Result:** A new task "Very Important Stuff" with a tag "Important" is added to the task list.

## Add multiple tags to task

1. Type `add "Do Area Cleaning" tag "Important" tag "Must do properly".

**Result:** A new task "Do Area Cleaning" with 2 tags ("Important" and "Must do properly") added to the task list.

## Select a task in task list

1. Type `select 2`.

**Result:** Task 5 ("Think of new year's resolution") is selected in the list. It will be expanded to reveal its details, since by default the entire list is collapsed. The calendar will change the week view to be 8 Nov 2015 to 14 Nov 2015, so that the task will be visible even though it is outside 2016.

## Select the last task in task list

1. Type `select last`.

**Result:** Task list will scroll to last task and select it, expanded to reveal its details. The calendar may change view if it provides a deadline or period.

## List uncompleted tasks and upcoming events

1. Type `list`.

**Result:** The task list will only show uncompleted tasks and upcoming events (50 such tasks).

## List all tasks that the task list have

1. Type `list all`.

**Result:** The task list will show all tasks that ever existed in the `.xml` file (55 such tasks).

## List tasks during a certain period

1. Type `list from 1 Nov to 7 Nov`.

**Result:** Only tasks between the two periods are shown (14 such tasks).

## List tasks with a deadline

1. Type `list by 12 Dec 2016`.

**Result:** Only tasks with deadline 12th Dec OR EARILER are shown (28 such tasks).

## List tasks with a certain tag

1. Type `list tag "Important"`.

**Result:** Only tasks that are tagged Important are shown (6 such tasks).

## List only floating tasks

1. Type `list floating tasks`.

**Result:** Only floating tasks are shown (10 such tasks).

## List uncompleted tasks

1. Type `list uncompleted tasks`.

**Result:** Only tasks with completionStatus = false are shown (42 such tasks).

## List overdue tasks

1. Type `list overdue`.

**Result:** Only overdue tasks are shown (9 such tasks).

## List recurring tasks

1. Type `list recurring`.

**Result:** Only recurring tasks are shown (12 such tasks).

## Sort list by earliest first

1. Type `list sort earliest first`.

**Result:**  The floating task will be at the top, then followed by the earliest task first, order until the last task is the latest

## Sort list by latest first

1. Type `list sort latest first`.

**Result:** The latest task will be at the top, then ordered by decreasing latest (until the last one is the earliest). The floating tasks will be at the bottom.

## Sort list by name (ascending order)

1. Type `list sort a-z`.

**Result:** The list is sorted from a to z (a at the top, z at the bottom).

## Sort list by name (descending order)

1. Type `list sort z-a`.

**Result:** The list is sorted from z to a (z at the top, a at the bottom).

## Show uncompleted tasks with tags "Important"

1. Type `list tag "Important"`.
2. Type `show uncompleted tasks`.

**Result:** The list only shows uncompleted tasks with tags "Important" (4 such tasks).

## Show uncompleted tasks WITHOUT tags "Important"

1. Type `list uncompleted tasks`.
2. Type `hide tag "Important"`.

**Result:** The list only shows uncompleted tasks that does **not** have the tags "Important" (38 such tasks).

## Update the task name

1. Type `update 1 name "New task!"

**Result:** The first task is renamed to "New task!". The rename is reflected on the calendar.

## Update the task deadline

1. Type `update 1 by 20 Nov 5pm`

**Result:** The first task's deadline is updated to 20 Nov, 5pm. The task in the calendar is moved to 5pm.

## Update the task period

1. Type `update 1 from 20 Nov 12 pm to 3pm`

**Result:** The first task's period is updated to 20 Nov, 12pm to 3pm. The task in the calendar changes its period to the one given.

## Update the task recurrence to weekly

1. Type `update 1 repeat weekly 5`

**Result:** The first task's recurrence is set to repeat 5 times on a weekly basis.

## Update the task by adding new tags

1. Ensure that the first task does not have tag "Not Important".
2. Type `update 1 tag "Not Important"`.

**Result:** The first task gets a new tag called "Not Important".

## Remove the deadline of the task

1. Ensure that the first task has a deadline.
2. Type `update 1 removeby`.

**Result:** The first task's deadline is removed. It is also removed from the calendar.

## Remove the period of the task

1. Ensure that the first task has a period.
2. Type `update 1 removefrom`.

**Result:**  The first task's period is removed. It is also removed from the calendar (note: `removeto` also works).

## Remove the recurrence of the task

1. Ensure that the first task has a recurrence.
2. Type `update 1 removerepeat`.

**Result:**  The first task's recurrence is removed. The repeating period that the task has is also removed from the calendar.

## Remove the tag in a task

1. Ensure that the first task has a tag "Important".
2. Type `update 1 removetag "Important"`.

**Result:** The tag "Important" is removed for the first task.

## Find tasks by name

1. Type `find go`.

**Result:** Tasks that contains the word "go" or part of it (al*go*rithm) will be shown (5 such tasks).

## Find tasks by tags

1. Type `find important`.

**Result:** Tasks that are tagged with "important" are shown (6 such tasks). Note that find command does not discriminate tags and names.

## Mark an UNcompleted non-recurring task as completed

1. Type `add "Slack"`.
2. Type `complete **X**` (whereby X is the index of the task for "Slack").

**Result:** The task "Slack" is marked as completed.

## Mark an UNcompleted recurring task as completed

1. Type `add "Breathe air" by today 5pm repeat daily 5`.
2. Type `complete **X**` (whereby X is the index of the tasks for "Breathe air").

**Result:** The "Breathe Air" for today should be marked as completed, with the task name appended with '[Completed at XXYYZZ]'. A new task is created containing only **4** recurrence of the task "Breathe Air", to represent the remaining recurrence. The calendar is updated so that the first task becomes grey, the rest of the task becomes blue.

## Mark a completed task as completed

1. Type `find go lifting`
2. Type `complete **X**` (whereby X is the index of the task for "Go lifting").

**Result:** The task list is unaffected. A message "Task is already completed" is shown.

## Undo a previous action

1. Type `add "Wrong Task"`.
2. Type `undo`.

**Result:** The task list is restored back to the state before step 1 was executed (i.e. "Wrong Task" should not exist after step 2).

## Undo when we first start the program

1. Ensure that we just started the program and did not do anything yet.
2. Type `undo`.

**Result:** The task list is unaffected. A message "There is no past command to undo" is shown.

## Redo something that we previously undo

1. Type `add Some new task`.
2. Type `undo`.
3. Type `redo`.

**Result:** The "Some new task" is added back to the list even though we undo it in step 2.

## Redo when we did not undo anything

1. Ensure that we never execute any undo.
2. Type `redo`.

**Result:** The task list is unaffected. A message "There is no past command to redo" is shown.

## Delete a certain task

1. Type `delete 3`

**Result:** The third task is deleted.

## Clear the entire list

1. Type `clear`

**Result:** The entire task list is empty.

## Switch to an existing list

1. Ensure that `existinglist.xml` exists.
2. Type `switchlist existinglist`

**Result:** The task list switches content to whatever was inside the existinglist.xml. The status bar changes to `data\existinglist.xml`.

## Switch to a new list

1. Type `switchlist somenewlistthatdoesnotexist`

**Result:** A new file `somenewlistthatdoesnotexist.xml` will be created since it does not exist. The task list is empty. The status bar changes to `data\somenewlistthatdoesnotexist.xml`.

## Rename the name of the list

1. Type `renamelist awesomefile`

**Result:** The file `\data\SampleData.xml` will be renamed to `awesomefile.xml`. The status bar changes to `data\awesomefile.xml`.

## Relocate the directory of the task list files

1. Type `relocate C:\Dropbox\`

**Result:** The files inside `data\` should be relocated to `C:\Dropbox\`. The status bar changes to `C:\Dropbox\SampleData.xml`.

## See the details of all the tasks in the list

1. The list by default should be collapsed.
2. Type `expand`.

**Result:** The entire task list will be expanded, so you should now be able to see all the details of all the tasks.

## Hide the details of all the tasks in the list

1. Type `expand` (so that we can test out collapse).
2. Type `collapse`.

**Result:** The entire task list will be collapsed, so you should only see the names of the tasks only now (no details of the task). If your task list is selected, the task remains expanded.

## Switch calendar to day view

1. The calendar by default should be in day view.
2. Type `select 1`.
3. Type `calendar day`.

**Result:** The calendar should only show 1 column for the day that task 1 is in.

## Switch calendar to week view

1. Type `select 1`.
2. Type `calendar day` (only for the purpose of testing the week view).
3. Type `calendar week`.

**Result:** The calendar should show 7 columns for the week that task 1 is in.
