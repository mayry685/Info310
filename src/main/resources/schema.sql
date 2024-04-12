DROP TABLE IF EXISTS CourseList;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS Assignment;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Account;


CREATE TABLE Account(
    AccountID SERIAL PRIMARY KEY,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    Status VARCHAR(255) NOT NULL
);

CREATE TABLE Course (
    CourseID SERIAL PRIMARY KEY,
    CourseName VARCHAR(255) NOT NULL,
    CourseDescription TEXT
);

CREATE TABLE CourseList(
    CourseListID SERIAL PRIMARY KEY,
    CourseID INT NOT NULL,
    AccountID INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (AccountID) REFERENCES Account(AccountID)
);

CREATE TABLE Assignment (
    AssignmentID SERIAL PRIMARY KEY,
    CourseID INT NOT NULL,
    AssignmentName VARCHAR(255) NOT NULL,
    AssignmentDescription TEXT,
    DueDate DATE NOT NULL,
    Weight INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

CREATE TABLE Events (
    EventID SERIAL PRIMARY KEY,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    EventName VARCHAR(255) NOT NULL,
    EventDescription TEXT,
    Location VARCHAR(255),
    Completed BOOLEAN NOT NULL
);