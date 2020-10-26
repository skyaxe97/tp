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
### 3.13 Showing Notes: `showNotes`
### 3.14 Deleting a Note: `deleteNotes`
### 3.15 Editing a Note: `editNotes`
### 3.16 Archiving Notes: `archive`
### 3.17 Exiting: `exit`

## 4.0 Other Features

### 4.1 Storing Data
### 4.2 Recurring Tasks and Auto Deletion

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
