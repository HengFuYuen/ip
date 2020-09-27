# Duke User Guide
Duke is a desktop application for managing tasks using **Command Line Interface**.

## Quick start
1. Ensure that your computer has java `11`installed.

1. Download the latest`ip.jar`.

1. Copy the jar file to an empty folder you want to use as the home folder for Duke.

1. Navigate to the folder containing the jar file on command prompt and run the command `java -jar ip.jar`. 

1. Type in a command and press Enter to execute it.

1. Refer to the Features below for details of each command.

## Features 
**Note**: 
* Words in `UPPER_CASE` are parameters that the user needs to supply.
  * e.g. `todo DESCRIPTION`, `DESCRIPTION` is a parameter which can be used as `todo buy bread`.
* Commands and parameter indicators are case-sensitive.
  * e.g. `list` will show a list of all tasks in Duke but `List` would throw an invalid command error.
  * e.g. `/at` indicates that whatever follows it will be event timing while `\At` will throw an error
   stating that the event time is not given.


### Adding a todo: `todo` 
Adds a todo, a task without any date/time attached, to Duke.

Format: `todo DESCRIPTION`

Example of usage: `todo buy bread`

Expected outcome:<br/>
`Got it. I've added this task:`<br/>
&nbsp;&nbsp;`[T][N] read book`<br/>
`Now you have 1 task(s) in the list.`

### Adding a deadline: `deadline` 
Adds a deadline, a task that needs to be done before a specific time/date, to Duke.

Format: `deadline DESCRIPTION /by TIME_IT_IS_DUE_BY`

Example of usage: `deadline return book /by Sunday 1pm`

Expected outcome:<br/>
`Got it. I've added this task:`<br/>
&nbsp;&nbsp;`[D][N] return book (by: Sunday 1pm)`<br/>
`Now you have 2 task(s) in the list.`

### Adding an event: `event` 
Adds an event, a task that starts at a specific time and ends at a specific time, to Duke.

Format: `event DESCRIPTION /at EVENT_TIMING`

Example of usage:`event meeting /at 30/10/2020 1300-1400`

Expected outcome:<br/>
`Got it. I've added this task:`<br/>
&nbsp;&nbsp;`[E][N] meeting (at: 30/10/2020 1300-1400)`<br/>
`Now you have 2 task(s) in the list.`

### Listing all tasks: `list` 
Shows a list of all tasks in Duke.

Format: `list`

Example of usage: `list`

Expected outcome:<br/>
`Here are the tasks in your list:`<br/>
&nbsp;&nbsp;`[T][N] read book`<br/>
&nbsp;&nbsp;`[D][N] return book (by: Sunday 1pm)`<br/>
&nbsp;&nbsp;`[E][N] meeting (at: 30/10/2020 1300-1400)`<br/>

### Deleting a task: `delete` 
Deletes the specified task from Duke.

Format: `delete TASK_INDEX`
* Deletes the task at the specified `TASK_INDEX`.
* The `TASK_INDEX` refers to the task number shown in the displayed task list.
* The `TASK_INDEX` **must be a positive integer** greater than 0.

Example of usage: `delete 2`

Expected outcome:<br/>
`Noted. I've removed this task:`<br/>
&nbsp;&nbsp;`[D][N] return book (by: Sunday 1pm)`<br/>
`Now you have 2 task(s) in the list.`

### Marking a task as done: `done` 
Marks the specified task in Duke as done.

Format: `done TASK_INDEX`
* Marks the task at the specified `TASK_INDEX` as done.
* The `TASK_INDEX` refers to the task number shown in the displayed task list.
* The `TASK_INDEX` **must be a positive integer** greater than 0.

Example of usage: `done 1`

Expected outcome:<br/>
`Nice! I've marked this task as done:`<br/>
&nbsp;&nbsp;`[T][Y] read book`<br/>

### Finding tasks using a keyword: `find` 
Finds all the tasks in Duke that contains the given keyword.

Format: `find KEYWORD`
* The search is case-sensitive. e.g. `Book` will not match `book`
* Both the task description and timing (if any) will be searched.
* The task number of the task shown corresponds to its task number in the full displayed task list.

Example of usage: `find meeting`

Expected outcome:<br/>
`Here is(are) the 1 matching task(s) in your list:`<br/>
&nbsp;&nbsp;`2.[E][N] meeting (at: 30/10/2020 1300-1400)`<br/>

### Clearing all tasks: `clear` 
Clears all tasks from Duke.

Format: `clear`

Example of usage: `clear`

Expected outcome:<br/>
`All previous data has been deleted...`<br/>
`The task list is now empty.`

### Exiting the program: `bye` 
Exits Duke.

Format: `bye`

Example of usage: `bye`

Expected outcome:<br/>
`Bye. Hope to see you again soon!`

### Saving the data
Duke data is automatically saved in the hard disk whenever any changes to the data is made. There is no
 need for manual saving. Previously saved data would also be loaded (if any) when Duke is booted up.

## Command Summary
Command | Format, Examples
--- | ---
todo | `todo DESCRIPTION`<br/> e.g., `todo buy bread`
deadline | `deadline DESCRIPTION by/ TIME_DEADLINE_IS_DUE`<br/> e.g., `deadline return book /by Sunday 1pm`
event | `event DESCRIPTION at/ EVENT_TIMING`<br/> e.g., `event meeting /at 30/10/2020 1300-1400`
list | `list`
delete | `delete TASK_INDEX`<br/> e.g., `delete 2`
done | `done TASK_INDEX`<br/> e.g., `done 1`
find | `find KEYWORD`<br/> e.g., `find meeting`
clear | `clear`
bye | `bye`
