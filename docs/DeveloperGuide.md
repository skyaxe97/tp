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

## 2.0 Setting Up

## 3.0 Design
### 3.1 Architecture

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
 
 
 _Figure 3.2.4-1: The classes that inherit from the Command class_
 
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

### 4.7 Displaying Free Time and Sleep Time (Daniel)

##### Implementation

The freeTime command displays to the user their longest block of free time for that day, while the 
 command displays to the user how much time they have available to sleep based on that day’s and the
 next day’s schedule. Both commands are implemented similarly. They both find the longest uninterrupted block
 of free time within a certain time period by checking if individual hour-long time blocks in this time period
 are free. The commands then use the start and end time values found to calculate a duration, and pass all
 three values to the Ui to display to the user. Figure 4.8-1 shows the sequence diagram for the freeTimeCommand,
 and Figure 4.8-2 shows the sequence diagram for the sleepTimeCommand.
 
_Figure 4.8-1: Sequence diagram for freeTimeCommand execution_

_Figure 4.8-2: Sequence diagram for sleepTimeCommand execution_


##### Design Considerations

1. Because of the way that the TaskList stores Tasks in an unsorted way, the  freeTime and sleepTime commands
 must iterate through the entire list every time to check if a particular time slot has nothing scheduled.
 This corresponds to a time complexity of O(N). This was chosen as the way to implement this function as the
 size of TaskList can be said to be relatively small. As such, the repeated iteration would not result in 
 significant impacts on the timing performance.
 
1. The functions also only provide an accuracy resolution which is rounded to the hour. Similar to the displaySchedule
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