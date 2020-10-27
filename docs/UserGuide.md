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
### 3.11 Viewing Available Sleep Time: `sleepTime`
### 3.12 Adding a New Note: `addNotes`
### 3.13 Showing Notes: `showNotes`
### 3.14 Deleting a Note: `deleteNotes`
### 3.15 Editing a Note: `editNotes`
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
