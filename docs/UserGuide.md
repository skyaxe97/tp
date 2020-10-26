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
**LifEasier** helps Computer Engineering (CEG) students to manage their classes, social events, deadlines and school notes through the Command Line Interface (CLI). The CLI allows **LifEasier** to deliver this functionality through a lightweight and easy to use interface. If you can type fast, **LifEasier** can get your timetabling needs done faster than a traditional GUI app.

This User Guide serves as an all-in-one document for users to set up and use **LifEasier**. To use this User Guide, simply refer to the Table of Contents to find what you are looking for. The Quick Start section gives instructions on how to set up and start using **LifEasier**. The Features section gives in-depth instructions on how to best use every feature **LifEasier** has to offer. The FAQ section answers some common queries that you might have. Lastly, the Command Summary provides a convenient summary of the commands you have available to use.

In order to get the best experience when using this User Guide, please refer to the legend below in Table 1-1.

<insert table 1-1>

## 2.0 Quick Start
This section describes the process of setting up **LifEasier** for use.

1. Ensure you have the latest version of `Java 11` installed on your computer.
2. Download the latest version of  `LifEasier.jar` from here: https://github.com/AY2021S1-CS2113T-W13-4/tp/releases 
3. Copy the downloaded Jar file  to a suitable location in your computer. *(Note: **LifEasier** will create a folder for save files in the same folder you ran the jar file.)*
4. Open a new **terminal window**  and navigate to where your `LifEasier.jar` is located on your computer.
5. Enter the following command into the terminal window to launch the application:
 `java -jar LifEasier.jar`
On successful launch, you will be greeted with a welcome screen as shown below in Figure 2-1.
```
=========================================================================
=========================================================================

  _      _  __ ______          _
 | |    (_)/ _|  ____|        (_)
 | |     _| |_| |__   __ _ ___ _  ___ _ __
 | |    | |  _|  __| / _` / __| |/ _ \ '__|
 | |____| | | | |___| (_| \__ \ |  __/ |
 |______|_|_| |______\__,_|___/_|\___|_|


=========================================================================
=========================================================================
Hello [NAME], what can I do for you today?
 -Type 'help' for a list the list of available commands
```
*Figure 2-1: LifEasier’s welcome screen*

6. You are now ready to use **LifEasier.** Type commands into the terminal window and press enter to execute the command. E.g Type  help and press enter to bring up the list of  available commands.
7. Generally, green coloured text is displayed when commands have been completed successfully, or contains helpful information for you. Red coloured text is displayed when unexpected errors have occurred, or you have entered invalid information.
8. Refer to the Features section below to find more details on available commands.

## 3.0 User Features
The following section expands on the features available for you to use in LifEasier. The explanation for each feature includes the format to be followed, at least one example, and some further explanation and notes if necessary.

#####Notes on General Command Format:
* Words in `UPPER_CASE` are parameters to be supplied by the user.
* Items in square brackets are optional e.g `[TITLE]`
* Command parameters have to follow the order stated. E.g if the command states `addEvent NAME /time START /to END`,  `addEvent NAME /to END /time START` will **NOT** be accepted.
* However, parameters can be missing, and LifEasier will prompt you for the missing parameters.
* `DATE` parameters have the format of **DD-MM-YY**.
* `TIME` parameters follow a 24-hour clock, and have the format of **HH:mm**.

### 3.1 Viewing Help: `help`
### 3.2 Adding a Lesson: `addLesson`
### 3.3 Adding an Event: `addEvent`
### 3.4 Adding a Deadline: `addDeadline`
### 3.5 Editing a Lesson: `editLesson`
### 3.6 Editing an Event: `editEvent`
### 3.7 Editing a Deadline: `editDeadline`
### 3.8 Deleting a Task: `deleteTask`
### 3.9 Displaying Schedule: `display`
Displays your current schedule. Use this command to view what your schedule is like for today or for the whole week.
Format: `display [WEEK]`

#####Notes on display Command Format:
* If `WEEK` is specified, the schedule for the week will be shown. If not, the schedule for the day will be shown instead.
* The `display week` command displays the schedule of the upcoming week, in 1 hour intervals. 
* The entire row with all tasks that fall in the current hour will be coloured in cyan for your ease of reference. 

######Examples:
`display` displays the schedule for today. An example of how to use the display command is shown below in Figure 3.9-1.

```
display
Here is your schedule for today:
13:00-14:00  CS2113T
```
*Figure 3.9-1: An example of using the display command to view your daily schedule*

`display week` displays the schedule of the upcoming 7 days, including the current day. Another  example of how to use the edit lesson command is shown below in Figure 3.9-2.

```
display week
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|TIME       | WEDNESDAY       | THURSDAY        | FRIDAY          | SATURDAY        | SUNDAY          | MONDAY          | TUESDAY         |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|08:00-09:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|09:00-10:00|                 | CG1111          |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|10:00-11:00|                 | CG1111          |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|11:00-12:00|                 | CG1111          |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|12:00-13:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|13:00-14:00| CS2113T         |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|14:00-15:00|                 | CS2101          |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|15:00-16:00|                 | CS2101          |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|16:00-17:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|17:00-18:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|18:00-19:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|19:00-20:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|20:00-21:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
|21:00-22:00|                 |                 |                 |                 |                 |                 |                 |
+-----------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+-----------------+
Here are your upcoming deadlines this week:
```
*Figure 3.9-2: An example of using the display week command to view your weekly schedule*

### 3.10 Viewing Available Free Time: `freeTime`
### 3.11 Viewing Available Sleep Time: `sleepTime`
### 3.12 Adding a New Note: `addNotes`
### 3.13 Showing Notes: `showNotes`
### 3.14 Deleting a Note: `deleteNotes`
### 3.15 Editing a Note: `editNotes`
### 3.16 Archiving Notes: `archive`
### 3.17 Exiting: `exit`

## 4.0 Other Features

### 4.1 Storing Data
### 4.2 Recurring Tasks and Auto Deletion

## 5.0 FAQ
**The following FAQ section answers some common questions that you may have about the LifEasier application.**

**Q: Can I use my LifEasier saves on another computer?**
**A:** Yes you can. You can transfer your files from one computer to another, and place them in a folder called “LifEasierSaves”. Then, run `LifEasier.jar` from the same folder as “LifEasierSaves”. Your saves should be loaded into LifEasier. As long as the “LifEasierSaves” folder is in the same folder as where the LifEasier.jar is being run, the program will read your saved information as per normal.

**Q: Can I edit the information in the save files directly?**
**A:** Yes, it is possible but it is not recommended to do so. Directly modifying the save files may result in incorrectly formatted data to be passed into the **LifEasier** program on the next launch, resulting in missing data.


## 6.0 Command Summary
