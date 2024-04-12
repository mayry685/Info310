CREATE TABLE Student (
    StudentID SERIAL PRIMARY KEY,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL
);

CREATE TABLE Course (
    CourseID SERIAL PRIMARY KEY,
    CourseName VARCHAR(255) NOT NULL,
    CourseDescription TEXT
);

CREATE TABLE CourseList_Student (
    CourseListID SERIAL PRIMARY KEY,
    CourseID INT NOT NULL,
    StudentID INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
);

CREATE TABLE Lecturer (
    LecturerID SERIAL PRIMARY KEY,
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    Username VARCHAR(255) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Email VARCHAR(255) NOT NULL
);

CREATE TABLE CourseList_Lecturer (
    CourseListID SERIAL PRIMARY KEY,
    CourseID INT NOT NULL,
    LecturerID INT NOT NULL,
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (LecturerID) REFERENCES Lecturer(LecturerID)
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
