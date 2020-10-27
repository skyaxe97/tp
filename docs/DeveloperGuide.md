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
The addLesson/addEvent/addDeadline command adds the specific task into the TaskList.
##### Implementation
Due to the different parameters required by the addition of different types of tasks, each command has
 a specific intended parameter to be input by the user.

For example, if the user uses the addEvent command, the user will have to input the EVENT_NAME,
 START_TIME and END_TIME, whereas if the user uses the addDeadline command, the user will have input DEADLINE_NAME and BY.

When the command is called, the class invokes the TaskList class method to add the specific task to
 the TaskList, after which Storage is called to save the updated TaskList to the local save file. Figure 4.1-1 shows the 
 sequence diagram for the addDeadlineCommand. This sequence diagram is applicable to the other two addEventCommand and 
 addLessonCommand, with the only difference being in their parameters.
 
 ![Figure 4.1-1](images/DeveloperGuide/Figure 4.1-1.png)  
 _Figure 4.1-1: Sequence diagram for addDeadlineCommand execution_

##### Design Considerations
The success and accuracy of the command is heavily dependent on the values passed to the command by the Parser class.
 Thus, checks have to be thoroughly conducted by the Parser class before the Command class is invoked.
### 4.2 Editing Lessons, Events, Deadlines (Fairuz)
The editEventCommand, editDeadlineCommand and editLessonCommand allows the user to edit existing
 tasks in the TaskList according to the type of task.
##### Implementation
These command classes will be invoked when the user inputs the editTask command, followed by
 the type of task to be edited. 

The user also has an option to input the task’s name, or part of it, after which LifEasier
 will print out all tasks matching the type, and the user query. If the user chooses to leave
 that search parameter blank, LifEasier will print out all the tasks of the intended type.
 The program will then prompt User to input the index of the task to be edited.

Upon receiving a valid index of a task to be edited, LifEasier will prompt the User for the
 parameter to be edited - Name or Time. 

Upon receiving a valid option of parameter to edit, LifEasier will prompt the User to input
 the new descriptions. If the User input is valid according to the required format, LifEasier will print a confirmation
 of the edit. The edited task will then be saved by Storage. Figure 4.2-1 illustrates the flow of editDeadlineCommand 
 through a sequence diagram. The logic of this class remains the same among the different types of Tasks.

![Figure 4.2-1](images/DeveloperGuide/Figure 4.2-1.png)  
![Figure 4.2-1.1](images/DeveloperGuide/Figure 4.2-1.1.png)  
_Figure 4.2-1: Sequence diagram for editDeadlineCommand execution_

##### Design Considerations
Due to the difference in the types of tasks and their parameters, this functionality was designed to guide the User
 explicitly to ensure accurate inputs and thus efficiency.
 
### 4.3 Deleting of Lessons, Events, Deadlines (Fairuz)
The deleteTaskCommand allows the user to delete any task (lesson, event or deadline) from the TaskList.
##### Implementation
The User first enters the deleteTask command and appends the type of task to be deleted. LifEasier will then print
 out the list of tasks of the intended type. For example, when User inputs “deleteTask /type event”, LifEasier will 
 print out all Events in the TaskList.

LifEasier will then prompt the user to select the Task to be deleted by inputting the index of the Task as seen from
 the terminal. Before the Task is deleted, a temporary copy of the current state of the Task will be saved by 
 TaskHistory. The Task is then deleted and LifEasier will print the confirmation of the deletion. The old copy of the 
 Task will then be pushed to TaskHistory, and the updated TaskList will be saved by Storage. Figure 4.3 will illustrate 
 the flow of the deleteTaskCommand through a sequence diagram.

##### Design Considerations
The command has to handle separate types of tasks as printing all tasks and forcing the User to look up the whole table
 would be impractical in the long run. The enhanced capability with TaskHistory to allow the User to undo any action is 
 crucial as its initial functionality did not have the ability to restore any accidentally deleted Tasks.

![Figure 4.3-1](images/DeveloperGuide/Figure%204.3-1.png)    
_Figure 4.3-1: Sequence diagram for deleteTaskCommand execution_
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