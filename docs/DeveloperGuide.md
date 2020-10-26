# LifEasier Developer Guide

## Table of Contents
* [1.0 Introduction](#10-introduction)
* [2.0 Setting Up](#20-setting-up)
* [3.0 Design](#30-design)
    * [3.1 Architecture](#31-architecture)
    * [3.2 Components](#32-components)
        * [3.2.1 LifEasier Component](#321-lifeasier-component)
        * [3.2.2 UI Component](#322-ui-component)
        * [3.2.3 Parser Component](#323-parser-component)
        * [3.2.4 Command Component](#324-command-component)
        * [3.2.5 TaskList Component](#325-tasklist-component)
        * [3.2.6 NoteList Component](#326-notelist-component)
        * [3.2.7 Storage Component](#327-storage-component)
* [4.0 Implementation](#40-implementation)
    * [4.1 Adding Lessons, Events, Deadlines (Fairuz)](#41-adding-lessons-events-deadlines-fairuz)
    * [4.2 Editing and Deleting Lessons, Events, Deadlines (Fairuz)](#42-editing-and-deleting-lessons-events-deadlines-fairuz)
    * [4.3 Adding Notes (Edmund)](#43-adding-notes-edmund)
    * [4.4 Editing and Deleting Notes (Edmund)](#44-editing-and-deleting-notes-edmund)
    * [4.5 Storing and Archiving Notes (Danzel)](#45-storing-and-archiving-notes-danzel)
    * [4.6 Displaying Schedule (Johannine)](#46-displaying-schedule-johannine)
    * [4.7 Displaying Free Time and Sleep Time (Daniel)](#47-displaying-free-time-and-sleep-time-daniel)
    * [4.8 Parsing Commands (Edmund / Daniel?)](#48-parsing-commands-edmund--daniel)
* [5.0 Product Scope](#50-product-scope)
    * [5.1 Target user profile](#51-target-user-profile)
    * [5.2 Value proposition](#52-value-proposition)
* [6.0 User Stories](#60-user-stories)
* [7.0 Non-Functional Requirements](#70-non-functional-requirements)
* [8.0 Documentation](#80-documentation)
* [9.0 Testing / Logging](#90-testing--logging)
* [10.0 Dev Ops](#100-dev-ops)
* [11.0 Glossary](#110-glossary)

## 1.0 Introduction
**LifEasier** helps Computer Engineering (CEG) students to manage their classes, social events, deadlines and school notes through the Command Line Interface (CLI). **LifEasier** is developed by a group of CEG students for their CS2101/CS2113T mods.

This developer guide documents the design, architecture and instructions for testing, as a reference for developers who will be maintaining or expanding **LifEasier** in the future. 

## 2.0 Setting Up
###2.1 Prerequisites

1. JDK 11
2. Intellij IDE

###2.2 Setting Up the Project

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first)

2. Set up the correct JDK version for Gradle.
Click `Configure` > `Project Defaults` > `Project Structure`
Click New... and find the directory of the JDK

3. Click Import Project

4. Locate the build.gradle file and select it. Click OK.

5. Click Open as Project

6. Click OK to accept the default settings.

###2.3 Verifying Setup

1. Run the seedu.LifEasier.Main and try a few commands.

2. Run the tests to ensure they all pass. This can be done by executing the command `gradlew build` in IntelliJ’s terminal.


## 3.0 Design
### 3.1 Architecture
This section elaborates on the high-level architecture of the **LifEasier** application. It provides a brief introduction to each component, and how these components interact with one another. 


*Fig. 1 Architecture diagram for LifEasier*
LifEasier is comprised of 7 components, which are listed below together with their functions (shown in Fig 1):

1. LifEasier: The main class of the application.
2. Ui: Displays messages to the user, and takes in the user’s commands
3. Parser: Understands the user’s commands, and creates the necessary Command objects.
4. Command: Carries out the user’s command
5. TaskList: Holds the task data of the app in memory.
6. NoteList: Holds the note data of the app in memory.
7. Storage: Reads and writes data from the hard disk.

Each of these components are expanded on in more detail in their respective sections.

Figure 2 illustrates the Sequence diagram for how each class interacts with one another through an example  “addDeadline” command.


*Fig 2. Sequence diagram of “addDeadline”.*

### 3.2 Components
This section expands on the various components that were first seen in the Architecture section.


#### 3.2.1 LifEasier Component
The LifEasier component is the main class of the application. It initialises all other components,
 and is used as the centre of all other components.


#### 3.2.2 UI Component
The UI component reads the user’s inputs and displays messages and content to the user. It consists of a main Ui,
 a ScheduleUi and a TimetableUi. The ScheduleUi handles all outputs to do with displaying a user’s schedule.
  To do this, it uses the TimetableUi to display a specialised timetable view of the weekly schedule. 
  The main Ui handles everything else, such as displaying general messages and reading the user’s inputs. 


#### 3.2.3 Parser Component
The Parser component takes the user’s inputs from the Ui component, and makes sense of these commands. If the
 commands are incomplete, it calls the Ui component to prompt the user for more input until the commands have 
 the parameters required to execute. Once this condition is fulfilled, it returns a Command object to LifEasier
  for the commands to be executed.
  
  
#### 3.2.4 Command Component
The Command component consists of an abstract Command class and the many different commands that inherit the 
Command class. What is important to note is that each type of command class (e.g. addLessonCommand, showNotesCommand)
 implements an abstract execute() method that carries out the command. Figure 3.2.4-1 below shows the classes that 
 inherit from the Command class.
 
 *Figure 3.2.4-1: The classes that inherit from the Command class*
 
#### 3.2.5 TaskList Component

The TaskList component handles the instantiation and modifications to the overall list of tasks. Specifically,
 it handles any addition, edition, deletion on the TaskList. This component will heavily interact with the Command 
 component as most commands will include modifications to the overall TaskList.
 
 
#### 3.2.6 NoteList Component
The NoteList component contains all the users notes. Similar to the TaskList component, it interacts heavily with 
the Command component to modify the user’s notes.


#### 3.2.7 Storage Component
The Storage component handles saving of the users’ notes and tasks to persistent storage.
 It does this after every addition, change, or deletion to the TaskList component or NoteList component. 
 It also handles the moving of the stored notes to a separate archive file if instructed.

## 4.0 Implementation
### 4.1 Adding Lessons, Events, Deadlines (Fairuz)
### 4.2 Editing and Deleting Lessons, Events, Deadlines (Fairuz)
### 4.3 Adding Notes (Edmund)
### 4.4 Editing and Deleting Notes (Edmund)
### 4.5 Storing and Archiving Notes (Danzel)
### 4.6 Displaying Schedule (Johannine)
The displaySchedule command presents the TaskList contents in a timetable format, given that it is specified to display the full week. Otherwise it displays the current day’s schedule in a list form, with the Task items sorted by date.

*Figure 4.7-1: Sequence diagram for displaying week or day schedule*

#####Implementation
The timetable is structured in such a way that the first column always starts with the schedule of the current day, followed by that of the next 6 days. This is so that the user always sees 7 days ahead, rather than a typical fixed format (e.g. from Monday to Sunday).

Changes to the timetable are updated at every call of the showTimetable() method, which first involves the generation of the timetable by loading the contents of the TaskList into it, then printing it row by row.

The timetable is modelled using an ArrayList, with each entry containing a row of the timetable as a string. The individual cell entries of the timetable are collected by iterating through each day, each time slot and then through the TaskList to see which tasks fall on that particular day and are held during that particular time slot.

The cell entries which fall on the same time slot and hence the same row, are collected into an array and formatted into a string, before it is finally added to the ArrayList of timetable rows.

#####Design Considerations
To ensure that the displayed timetable is easy to read and offers a quick view of the user’s schedule, especially that of the current day, the timetable is not made to be fixed. The display schedule commands must thus iterate through the entire TaskList every time it is called, in order to arrange the Tasks accordingly and update any changes.

Because of the way the timetable time slots increment on an hourly basis, functions were implemented to ensure the timings of Tasks were rounded to the hour. This was an intentional design choice to keep the timetable neat and not overloaded with too much details.

### 4.7 Displaying Free Time and Sleep Time (Daniel)
##### Implementation
The freeTime command displays to the user their longest block of free time for that day, while the 
 command displays to the user how much time they have available to sleep based on that day’s and the
 next day’s schedule. Both commands are implemented similarly. They both find the longest uninterrupted block
 of free time within a certain time period by checking if individual hour-long time blocks in this time period
 are free. The commands then use the start and end time values found to calculate a duration, and pass all
 three values to the Ui to display to the user. Figure 4.8-1 shows the sequence diagram for the freeTimeCommand,
 and Figure 4.8-2 shows the sequence diagram for the sleepTimeCommand.
 
*Figure 4.8-1: Sequence diagram for freeTimeCommand execution*

*Figure 4.8-2: Sequence diagram for sleepTimeCommand execution*
##### Design Considerations
1. Because of the way that the TaskList stores Tasks in an unsorted way, the  freeTime and sleepTime commands
 must iterate through the entire list every time to check if a particular time slot has nothing scheduled.
 This corresponds to a time complexity of O(N). This was chosen as the way to implement this function as the
 size of TaskList can be said to be relatively small. As such, the repeated iteration would not result in 
 significant impacts on the timing performance.
 
2. The functions also only provide an accuracy resolution which is rounded to the hour. Similar to the displaySchedule
 command, this was an intentional design choice to not overload the user with too much unnecessary details. 


### 4.8 Parsing Commands (Edmund / Daniel)

## 5.0 Product Scope

### 5.1 Target user profile

{Describe the target user profile}

### 5.2 Value proposition

{Describe the value proposition: what problem does it solve?}

## 6.0 User Stories

|Version| As a ... | I want to ... | So that I can ...|
|--------|----------|---------------|------------------|
|v1.0|new user|see usage instructions|refer to them when I forget how to use the application|
|v2.0|user|find a to-do item by name|locate a to-do without having to go through the entire list|

## 7.0 Non-Functional Requirements

{Give non-functional requirements}

## 8.0 Documentation
Apart from PDF versions of our User Guide and Developer Guide, separate versions are also managed under the
 `/docs` folder. The versions under these folders should be the most updated. GitHub Pages and Jekyll are used 
 to generate formatted static websites to host the User Guide and Developer Guide.


## 9.0 Testing / Logging
The project makes use of Gradle and JUnit5 for testing. There are two ways that tests can be run, which are elaborated
 below.

1. Using the IntelliJ JUnit test runner: To test the entire project, navigate to the `src/test/java/seedu.lifeasier` 
folder and right click on it, and select `Run Tests in ‘seedu.life…’`. The option to run tests with coverage is also 
listed.To run tests on a particular subset, you can right click on any test package, test class, or test method and
select `Run`.
2. Using Gradle: In the terminal, run the command `gradlew clean test` for Windows, or `./gradlew clean test` for Mac
 and Linux. This will run all tests.

The project uses the `java.util.logging` package for logging. Each class uses its own `Logger` object to log the 
necessary messages. Logging can be enabled or disabled through the use of the `logging boolean` when calling
the `LifEasier.run()` method. Setting `showLogging` to `true` will enable logging, and setting it to `false` will 
disable logging.

## 10.0 Dev Ops
The project uses Gradle for build automation and dependency management. More information on how to use
 Gradle can be found [here](https://se-education.org/guides/tutorials/gradle.html).

The project also uses Github Actions for Continuous Integration (CI). The configurations file can be found in
 the `.github/workflows` folder. No actions regarding configuration are currently required, but this file can be
  edited in future to change the configurations if necessary.

## 11.0 Glossary

* *glossary item* - Definition

## Appendix A: Project Requirements



## Appendix B: Guidelines on Manual Testing

Refer to the **LIfEasier User Guide** for the setting up/quick start guide and to view more detailed information of all usable commands. After launching the **LifEasier** app, the tester can run the `help` command to display the list of available commands.

The following are some sample commands to add new tasks and notes into **LifEasier**.
* `addLesson /code CS2113T /date 28-10-20 /time 14:00 /to 16:00`
* `addEvent CS2101 Presentation /date 30-10-20 /time 09:00 /to 12:00`
* `addDeadline Buy some Bread /by  31-01-20 22:00`
* `addNotes`
* `addNotes Cats are the best!`

Use the following commands to test the display schedule function:
* `display week`
* `display`

To view currently saved notes, you can use either of the following commands.
* `showNotes`
* `showNotes Cats`

All tasks and notes are editable. Use the following sample commands to test the implemented edit feature.
* `editLesson CS2113T`
* `editEvent CS2101 Presentation`
* `editDeadline Buy some Bread`
* `editNotes`
* `editNotes Cats`

Once again, use the `display` and `showNotes` commands to view the updated tasks and notes contents.

At any point, feel free to quit the app using `exit` and relaunch the app to view that **LifEasier** has saved all input data.

Test deletion of tasks and notes with the following commands.
* `deleteTask /type lesson /CS2113T`
* `deleteTask /type event`
* `deleteNotes`
* `deleteNotes Cats` 

Use the following command to archive all currently loaded notes.
* `archive`

Now, when `showNotes` is used, there should be no notes listed. Navigate to the “LifEaserSaves” directory created in the same folder as LifEasier.jar was run, to find the created “Archives” directory. Your archived notes will be found in the created text file named corresponding to the date and time the archive command was run.

To show free time and sleep time, use the following commands.
* `freeTime`
* `sleepTime`

Following the above path for manual testing will bring you through all the features implemented in the current version of **LifEasier**. Please feel free to try out other combinations of inputs to fully test the program.


## Appendix C: Effort

On average, the development team met up twice a week to merge finished work, bug test, and do minor bug fixes before continuing to discuss design moving forward, new features to be implemented and handing out new issues. 

Overall, the average individual effort was higher than that of the individual project. This is because we underestimated the difficulty of working in a team, and the amount of time needed to create the User Guide and Developer Guide. 

