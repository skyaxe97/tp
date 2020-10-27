# LifEasier User Guide

##Table of Contents
* [1.0 Introduction](#10-introduction)
* [2.0 Quick Start](#20-quick-start)
* [3.0 User Features](#30-user-features)
    * [3.1 Viewing Help: `help`](#31-viewing-help-help)
    * [3.2 Adding a Lesson: `addLesson`](#32-adding-a-lesson-addlesson)
    * [3.3 Adding an Event: `addEvent`](#33-adding-an-event-addevent)
    * [3.4 Adding a Deadline: `addDeadline`](#34-adding-a-deadline-adddeadline)
    * [3.5 Editing a Lesson: `editLesson`](#35-editing-a-lesson-editlesson)
    * [3.6 Editing an Event: `editEvent`](#36-editing-an-event-editevent)
    * [3.7 Editing a Deadline: `editDeadline`](#37-editing-a-deadline-editdeadline)
    * [3.8 Deleting a Task: `deleteTask`](#38-deleting-a-task-deletetask)
    * [3.9 Displaying Schedule: `display`](#39-displaying-schedule-display)
    * [3.10 Viewing Available Free Time: `freeTime`](#310-viewing-available-free-time-freetime)
    * [3.11 Viewing Available Sleep Time: `sleepTime`](#311-viewing-available-sleep-time-sleeptime)
    * [3.12 Adding a New Note: `addNotes`](#312-adding-a-new-note-addnotes)
    * [3.13 Showing Notes: `showNotes`](#313-showing-notes-shownotes)
    * [3.14 Deleting a Note: `deleteNotes`](#314-deleting-a-note-deletenotes)
    * [3.15 Editing a Note: `editNotes`](#315-editing-a-note-editnotes)
    * [3.16 Archiving Notes: `archive`](#316-archiving-notes-archive)
    * [3.17 Exiting: `exit`](#317-exiting-exit)
* [4. Other Features](#40-other-features)
    * [4.1 Storing Data](#41-storing-data)
    * [4.2 Recurring Tasks and Auto Deletion](#42-recurring-tasks-and-auto-deletion)
* [5. FAQ](#50-faq)
* [6. Command Summary](#60-command-summary)

## 1.0 Introduction

## 2.0 Quick Start

## 3.0 User Features

### 3.1 Viewing Help: `help`
This command shows you the available commands and their formats.

Format: `help`

###### Example:
An example of how to use the help command is shown below in Figure 3.1-1.
````
help
=========================================================================
These are the commands that are available:
Notes about the command format:
 * Words in UPPER_CASE are the parameters to be supplied by the user
 * Items in square brackets are optional, e.g [DATE]

COMMANDS
*************************************************************************
help ---------------------------------------- Displays available commands
addLesson /code MODULE_CODE /date DATE /time START /to END -- Adds lesson
addEvent EVENT_NAME /date DATE /time START /to END -------- Adds an event
addDeadline DEADLINE_NAME /by DATETIME ------------------ Adds a deadline
editLesson MODULE_CODE ----------------------------------- Edits a lesson
editEvent EVENT_NAME ------------------------------------- Edits an event
editDeadline DEADLINE_NAME ----------------------------- Edits a deadline
deleteTask /type TYPE /name NAME ------------------------- Deletes a task
addNotes TITLE ------------------------------------------ Adds a new note
showNotes TITLE ------------------------------------- Shows selected note
archive ------------------------------------- Archives all existing notes
editNotes TITLE ----------------------------------- Edits a selected note
deleteNotes TITLE ------------------------------- Deletes a selected note
display WEEK/DAY --------------- Displays either weekly or daily schedule
freeTime ------------------------ Tells you when you have free time today
sleepTime --------------------- Tells you how much time you have to sleep
exit --------------------------------------- Closes the LifEasier program
*************************************************************************
For more detailed information, please visit the online user guide at:

=========================================================================
````
_Figure 3.1-1: An example of using the help command_

### 3.2 Adding a Lesson: `addLesson`
### 3.3 Adding an Event: `addEvent`
### 3.4 Adding a Deadline: `addDeadline`
### 3.5 Editing a Lesson: `editLesson`
### 3.6 Editing an Event: `editEvent`
### 3.7 Editing a Deadline: `editDeadline`
### 3.8 Deleting a Task: `deleteTask`
### 3.9 Displaying Schedule: `display`

### 3.10 Viewing Available Free Time: `freeTime`
Displays your longest block of free time for that day based on the schedule.
Use this command to find out when is a good time to schedule something.

##### Notes on freeTime Command:
- The system only searches for free time between 7:00 and 24:00. This is to promote a healthy sleep cycle.
Format: `freeTime`

###### Example:
An example of how to use the freeTime command is shown in Figure 3.10-1.
```
freeTime
You have 10 hours of free time between 14:00 and 24:00!
You can try scheduling something in this time!
=========================================================================
```
*Figure 3.10-1: An example of using the freeTime command.*


### 3.11 Viewing Available Sleep Time: `sleepTime`
Displays how much time you have available to sleep based on your schedule for that day and the day after.
Use this command to find out if you can have a good rest after a long day.

##### Notes on sleepTime Command:
- The system recommends 8 hours of sleep a day. Anything more, and the extra duration will not be displayed.
- The system will also count the hour before your first activity in the day as busy,
to give you time to wake up and get ready. E.g. if your first activity is at 9:00, 
the system will tell you that you have nothing on only until 8:00.

Format: sleepTime

###### Example:
An example of how to use the sleepTime command is shown in Figure 3.11-1.
```
sleepTime
You have nothing on from 14:00 today to 8:00 tomorrow!
You can sleep for the recommended 8 hours or longer!
=========================================================================
```
*Figure 3.11-1: An example of using sleepTime command*


### 3.12 Adding a New Note: `addNotes`
Adds and stores a note. Use this command to take notes from your classes or events.
Format: addNotes [TITLE]

##### Notes on addNotes Command Format:
- Adds and stores a note tagged with  TITLE.
- If TITLE is not provided, LifEasier will prompt you for a notes title.

###### Example:
An example of how to use the addNotes command is shown in Figure 3.12-1.
```
addNotes Cats are the best!
=========================================================================
Alright! Please fill in your notes.

Cats are the cutest in the world :D
Ok! I've taken note of this note!
=========================================================================
```
*Figure 3.12-1: An example of using the addNotes command

### 3.13 Showing Notes: `showNotes`
Displays a note/list of notes. Use this command to view the notes you have taken before.
Format: showNotes [TITLE] 

##### Notes on showNotes Command Format:
- If TITLE is specified, the specific note is displayed. If not, a numbered list of all notes 
will be displayed.
- TITLE can be a partial title. If there is one match, that note will be shown.
- If multiple notes with the same TITLE are found, all note titles containing the input will be 
displayed in a list. 

###### Example:
An example of how to use the showNotes command is shown in Figure 3.13-1.
```
showNotes Cats
=========================================================================
Title: Cats are the best!

Cats are the cutest in the world :D

=========================================================================
```
*Figure 3.13-1: An example of using the showNotes command

### 3.14 Deleting a Note: `deleteNotes`
Deletes a note from the list. Use this command to remove the notes you no longer need.
Format: deleteNotes [TITLE] 

##### Notes on deleteNotes Command Format:
- If TITLE is specified, the specific note is displayed and confirmation of delete will be prompted. 
If no TITLE is inputed, a numbered list of all notes will be displayed.
- TITLE can be a partial title. If there is one match, that note will be shown.
- If multiple notes with the same TITLE are found, all note titles containing the input will be 
displayed in a list.
- Entering “Y” would delete the note from the list. Entering “N” would exit the command without deletion. 

###### Example:
An example of how to use the deleteNotes command is shown in Figure 3.14-1.
```
deleteNotes
=========================================================================
Please select the notes you want to delete:

1. Cats are the best!

1
Title: Cats are the best!

Cats are the cutest in the world :D

Is this the note you want to delete? (Y/N)

Y
OK! Note deleted!
=========================================================================
```
*Figure 3.14-1: An example of using the deleteNotes command

### 3.15 Editing a Note: `editNotes`
Edits a note from the list. Use this command to make changes to notes you have taken before.
Format: editNotes [TITLE] 

##### Notes on deleteNotes Command Format:
- If TITLE is specified, the specific note is displayed and confirmation of edit will be prompted. 
If no TITLE is inputed, a numbered list of all notes will be displayed.
- TITLE can be a partial title. If there is one match, that note will be shown.
- If multiple notes with the same TITLE are found, all note titles containing the input will be 
displayed in a list.
- Entering “Y” would further prompt for a change in title or description. Entering “N” would 
exit the command without deletion. 
- Entering “T” would show the current title and prompt for a new title. Entering “D” would 
show the current description and prompt for a new description.

###### Example:
An example of how to use the editNotes command is shown in Figure 3.15-1.
```
editNotes Cats
=========================================================================
Title: Cats are the best!

Cats are the cutest in the world :D

Is this the note you want to edit? (Y/N)

Y
Do you want to change the title or description? (T/D)

T for title and D for Description

D
Current description:
Cats are the cutest in the world :D

Please input the description you want to change to:

I really love cats!
OK! Your description is now: I really love cats!
=========================================================================
```
*Figure 3.15-1: An example of using the editNotes command

### 3.16 Archiving Notes: `archive`
Archives all existing notes. Use this command to store all your notes in a separate save file. These notes will no 
longer be displayed anywhere.

Format: `archive`

##### Notes on archive Command Format:
* All current notes existing in **LifEasier** will be archived into the “Archives” folder found inside the 
“LifEasierSaves” save folder.
* Archive files are automatically named in the format of **DD-MM-YY HH:MM.txt** format.
* Upon archiving, all saved notes will be removed and placed in the archive instead.
* There is no way to undo this action, so use the `archive` command with care.

###### Example:
An example of how to use the archive command is shown in Figure 3.16-1.
````
archive
=========================================================================
Starting archiving...
Archiving successful!
=========================================================================
````
_Figure 3.16-1: An example of using the archive command_

### 3.17 Exiting: `exit`
Exits the programme. Use this command when you are done using LifEasier.

Format: `exit`

###### Example:
An example of how to use the exit command is shown in Figure 3.17-1.
````
exit
Goodbye, hope to see you again soon!
````
_Figure 3.17-1: An example of using the exit command_

## 4.0 Other Features
This section covers some of the features which do not rely on your input to be executed. Rather, they happen in the 
background without any input from you necessary.

### 4.1 Storing Data
By default, **LifEasier** creates a save folder named “LifEasierSaves” in the same folder `LifEasier.jar` is run. 
**LifEasier** data is saved automatically to the hard disk when a new event, deadline, lesson or notes is added, 
deleted or edited. Data is stored in text files, in plaintext. This allows you to edit your data directly through 
the save files if necessary.

### 4.2 Recurring Tasks and Auto Deletion
**LifEasier** automatically shifts the date for any tasks 7 days forward if their original date is past, and they 
are set to repeat. **LifEasier** does this for every task in your schedule every time it starts up. E.g. If you start 
up **LifEasier** on the 24th of October, and you had a repeating class that last occurred on the 23rd of October, the 
lesson will be moved 7 days forward to the 30th of October. This ensures your repeating lessons, events and deadlines 
will always be moved forward as long as they are meant to repeat.

If it is past the date of your task, and if the task does not repeat, it will automatically be removed instead. 
By automatically deleting those tasks that have already been completed, **LifEasier** ensures your save files do not 
become unnecessarily large.

 **LifEasier** automatically shifts the date for any tasks 7 days forward if their original date is past, 
 and they are set to repeat. **LifEasier** does this for every task in your schedule every time it starts up.
 E.g. If you start up **LifEasier** on the 24th of October, and you had a repeating class that last occurred 
 on the 23rd of October, the lesson will be moved 7 days forward to the 30th of October. This ensures your
 repeating lessons, events and deadlines will always be moved forward as long as they are meant to repeat.

 If it is past the date of your task, and if the task does not repeat, it will automatically be removed instead.
 By automatically deleting those tasks that have already been completed, **LifEasier** ensures your save files do 
 not become unnecessarily large.


## 5.0 FAQ

## 6.0 Command Summary
The below table summarises the commands available to the user, and how they can be used. Examples are also provided 
for reference.

Action | Format | Example
--------|-----------------|----------------------------------------------------------------------------------
addLesson| `addLesson /code MODULE_CODE /date DATE /time START /to END /repeats TIMES` | `addLesson /code cg1111 /date 04-10-20 /time 09:00 /to 12:00 /repeats 10`
addEvent| `addEvent EVENT_NAME /date DATE /time START /to END /repeats TIMES` | `addEvent HappyTime /date 04-10-20 /time 09:00 /to 12:00 /repeats 0`
addDeadline| `addDeadline DEADLINE_NAME /by DATETIME /repeats TIMES` | `addDeadline CryTime /by 04-10-20 09:00 /repeats 0`
editLesson| `editLesson [CODE]` | `editLesson CS2101`
editEvent| `editEvent [NAME]` | `editEvent BlackPink Concert`
editDeadline| `editDeadline [NAME]` | `editDeadline Finish Work`
deleteTask| `deleteTask /type TYPE [/name NAME]` | `deleteTask /type event`
addNotes| `addNotes [TITLE]` | `addNotes AngryTime`
showNotes| `showNotes [TITLE]` | `showNotes LaughTime`
deleteNotes| `deleteNotes [TITLE]` | `deleteNotes SadTime`
editNotes| `editNotes [TITLE]` | `editNotes CryingTime`
undo| `undo TYPE` | `undo note OR undo task`
archive| `archive` | `archive`
display| `display [WEEK]` | `display day`
freeTime| `freeTime` | `freeTime`
sleepTime| `sleepTime` | `sleepTime`
help| `help` | `help`
exit| `exit` | `exit`
