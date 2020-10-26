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
#### 3.2.1 LifEasier Component
#### 3.2.2 UI Component
#### 3.2.3 Parser Component
#### 3.2.4 Command Component
#### 3.2.5 TaskList Component
#### 3.2.6 NoteList Component
#### 3.2.7 Storage Component

## 4.0 Implementation
### 4.1 Adding Lessons, Events, Deadlines (Fairuz)
### 4.2 Editing and Deleting Lessons, Events, Deadlines (Fairuz)
### 4.3 Adding Notes (Edmund)
### 4.4 Editing and Deleting Notes (Edmund)
### 4.5 Storing and Archiving Notes (Danzel)
The storing and saving of data in the **LifEasier** app is done automatically after every change such as adding, editing, 
deleting a component such as a lesson, deadline, event or note. The following section documents how the data storing 
and archiving system of **LifEasier** was implemented, followed by the considerations taken during the design of the storage components.

##### Implementation - Data saving and storing
Figure 4.6-1 shows the simplified class diagram of all the components in the storage package. There are far more methods 
that exist then as shown in the class diagram. These have been omitted for simplicity.

*Figure 4.6-1: Class Diagram for all storage components*

Figure 4.6-2 shows the sequence diagram of the save data reading process which runs whenever **LifEasier** is run. Upon app startup, 
the main `LifEasier` class creates a new `FileStorage` object, which starts the save reading process to load in all the previously stored 
data of the user, if available. Else, new save directories and save files are created in the same directory which the `LifEasier.jar` was run. 
Tasks and notes data read from the save file are used to create new `Task` and `Note` objects respectively, and added into `TaskList` and `NoteList`.

*Figure 4.6-2: Sequence diagram for save data reading on startup*

By default, the save directory is set as *LifEasierSaves* under the `DIRECTORY_PATH` constant found in the `FileStorage` class. 
The names of the tasks and notes save files are passed in as arguments from the main method in the `LifEasier` class, where the first 
argument dictates the resulting name of the tasks save file, while the second determines the name of the notes save file. Save directory 
names and paths are **editable**, along with the save file names by changing the values in the locations as stated.

Whenever a new task or note is added, edited or deleted, the `saveTask()` or `saveNote()` methods in the `FileStorage` class is called depending 
on whether the changed item was a task or a note, to begin the data saving process. Figure 4.6-3 shows the sequence diagram taken by the program 
to save the user’s notes data. The saving process for tasks and notes are implemented in similar ways, with the saving process for tasks 
requiring a few more additional steps to correctly convert the tasks’ `LocalDateTime` information into formatted Strings to allow for more 
readable save files. The format in which the `LocalDateTime` objects are converted to can be found in the `DateTimeFormatter` object in the 
`FileCommand` class.

*Figure 4.6-3: Sequence diagram for saving of user note data*

##### Implementation - Note Archiving
The `archive` command immediately moves all currently loaded notes into a newly generated text file in the `Archives` directory found within the 
*LifEasierSaves* directory. If no `Archives` directory is found, it is automatically created. Archive save files are automatically named as the 
current date in the **DD-MM-YY** format, and the time the archive command was run in the **HH:MM** format, separated by a **T**. The current save 
file for notes will be automatically cleared with the `clearSaveFile()` command found in the `FileCommand` class, and the current `noteList` is 
cleared. Archived notes will not be read by the program anymore and any changes can be made to the created archive save file.

The `archive` command checks for the size of the current `noteList` before execution, and as such, when an empty `noteList` is detected, 
the archiving process will not be started.

##### Design Considerations
In order to ensure users get the best hassle free and user-friendly experience while using **LifEasier**, saves are automatically done after any change 
that affects any user added tasks and notes. While the constant clearing and rewriting of the save data whenever a change occurs may affect performance 
when the save files get larger, it was decided that the convenience of an automatic saving system outweighs the performance costs, and the assurance 
granted to users that their data is constantly saved without needing their manual intervention.

Saves were also designed to be stored in simple plain text and easily accessible to users to allow experienced users to modify 
the save files directly and easily, if required. 

In the event of corrupted or missing data, the `storage` component defends and protects the app from potential issues that might arise from 
reading in this data by throwing exceptions to stop any further data reading. Any data read up to that point is untouched, and the app will 
continue to run as per normal. **Manual intervention from the user** is required to remove improperly formatted and/or missing data.  

### 4.6 Displaying Schedule (Johannine)
### 4.7 Displaying Free Time and Sleep Time (Daniel)
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

## 9.0 Testing / Logging

## 10.0 Dev Ops

## 11.0 Glossary

* *glossary item* - Definition