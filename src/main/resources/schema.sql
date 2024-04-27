DROP TABLE IF EXISTS CourseList;
DROP TABLE IF EXISTS Events;
DROP TABLE IF EXISTS Assignment;
DROP TABLE IF EXISTS Course;
DROP TABLE IF EXISTS Account;

DROP Sequence IF EXISTS Account_Id_Sequence;
DROP Sequence IF EXISTS Course_Id_Sequence;

CREATE SEQUENCE Account_Id_Sequence
    START WITH 100000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE Course_Id_Sequence
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE TABLE Account(
    AccountID VARCHAR(255) PRIMARY KEY,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL,
    Status VARCHAR(255) NOT NULL
);

CREATE TABLE Course (
    CourseID VARCHAR(255) PRIMARY KEY,
    CourseName VARCHAR(255) NOT NULL,
    CourseDescription TEXT
);

CREATE TABLE CourseList(
    CourseListID SERIAL PRIMARY KEY,
    CourseID VARCHAR(255) NOT NULL,
    AccountID VARCHAR(255) NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (AccountID) REFERENCES Account(AccountID)
);

CREATE TABLE Assignment (
    AssignmentID SERIAL PRIMARY KEY,
    CourseID VARCHAR(255) NOT NULL,
    AssignmentName VARCHAR(255) NOT NULL,
    AssignmentDescription TEXT,
    DueDate TIMESTAMP NOT NULL,
    Weight INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

CREATE TABLE Events (
    EventID SERIAL PRIMARY KEY,
    StartDate TIMESTAMP NOT NULL,
    EndDate TIMESTAMP NOT NULL,
    EventName VARCHAR(255) NOT NULL,
    EventDescription TEXT,
    Location VARCHAR(255),
    Completed BOOLEAN NOT NULL
);

-- Set default values for AccountID and CourseID
ALTER TABLE Account ALTER COLUMN AccountID SET DEFAULT ('AC' || nextval('Account_Id_Sequence')::text);
ALTER TABLE Course ALTER COLUMN CourseID SET DEFAULT ('C' || nextval('Course_Id_Sequence')::text);