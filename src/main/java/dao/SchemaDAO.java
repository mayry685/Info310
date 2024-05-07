package dao;

import org.jdbi.v3.sqlobject.statement.SqlUpdate;

public interface SchemaDAO extends CredentialsValidator {

    @SqlUpdate("DROP TABLE IF EXISTS CourseList")
    void dropCourseListTable();

    @SqlUpdate("DROP TABLE IF EXISTS Events")
    void dropEventsTable();

    @SqlUpdate("DROP TABLE IF EXISTS Assignment")
    void dropAssignmentTable();

    @SqlUpdate("DROP TABLE IF EXISTS Course")
    void dropCourseTable();

    @SqlUpdate("DROP TABLE IF EXISTS Account")
    void dropAccountTable();

    @SqlUpdate("DROP SEQUENCE IF EXISTS Account_Id_Sequence")
    void dropAccountSequence();

    @SqlUpdate("DROP SEQUENCE IF EXISTS Course_Id_Sequence")
    void dropCourseSequence();

    @SqlUpdate("CREATE SEQUENCE Account_Id_Sequence " +
               "START WITH 100000 " +
               "INCREMENT BY 1 " +
               "NO MINVALUE " +
               "NO MAXVALUE " +
               "CACHE 1")
    void createAccountSequence();

    @SqlUpdate("CREATE SEQUENCE Course_Id_Sequence " +
               "START WITH 1000 " +
               "INCREMENT BY 1 " +
               "NO MINVALUE " +
               "NO MAXVALUE " +
               "CACHE 1")
    void createCourseSequence();

    @SqlUpdate("CREATE TABLE Account (" +
               "AccountID VARCHAR(255) PRIMARY KEY, " +
               "AccountCode VARCHAR(255) NOT NULL, " +
               "FirstName VARCHAR(255) NOT NULL, " +
               "LastName VARCHAR(255) NOT NULL, " +
               "Username VARCHAR(255) NOT NULL UNIQUE, " +
               "Password VARCHAR(255) NOT NULL, " +
               "Email VARCHAR(255) NOT NULL, " +
               "Status VARCHAR(255) NOT NULL)")
    void createAccountTable();

    @SqlUpdate("CREATE TABLE Course (" +
               "CourseID VARCHAR(255) PRIMARY KEY, " +
               "CourseName VARCHAR(255) NOT NULL, " +
               "CourseCode VARCHAR(255) NOT NULL, " +
               "CourseDescription TEXT)")
    void createCourseTable();

    @SqlUpdate("CREATE TABLE CourseList (" +
               "CourseListID SERIAL PRIMARY KEY, " +
               "CourseID VARCHAR(255) NOT NULL, " +
               "AccountID VARCHAR(255) NOT NULL, " +
               "FOREIGN KEY (CourseID) REFERENCES Course(CourseID), " +
               "FOREIGN KEY (AccountID) REFERENCES Account(AccountID), " +
               "CONSTRAINT unique_combination UNIQUE (CourseID, AccountID))")
    void createCourseListTable();

    @SqlUpdate("CREATE TABLE Assignment (" +
               "AssignmentID SERIAL PRIMARY KEY, " +
               "CourseID VARCHAR(255) NOT NULL, " +
               "AssignmentName VARCHAR(255) NOT NULL, " +
               "AssignmentDescription TEXT, " +
               "DueDate TIMESTAMP NOT NULL, " +
               "Weight INT NOT NULL, " +
               "FOREIGN KEY (CourseID) REFERENCES Course(CourseID))")
    void createAssignmentTable();

    @SqlUpdate("CREATE TABLE Events (" +
               "EventID SERIAL PRIMARY KEY, " +
               "StartDate TIMESTAMP NOT NULL, " +
               "EndDate TIMESTAMP NOT NULL, " +
               "EventName VARCHAR(255) NOT NULL, " +
               "EventDescription TEXT, " +
               "Location VARCHAR(255), " +
               "CourseID VARCHAR(10), " +
               "AccountID VARCHAR(10), " +
               "Completed BOOLEAN NOT NULL, " +
               "FOREIGN KEY (CourseID) REFERENCES Course(CourseID), " +
               "FOREIGN KEY (AccountID) REFERENCES Account(AccountID))")
    void createEventsTable();

    @SqlUpdate("ALTER TABLE Account ALTER COLUMN AccountID SET DEFAULT ('AC' || nextval('Account_Id_Sequence')::text)")
    void setAccountDefault();

    @SqlUpdate("ALTER TABLE Course ALTER COLUMN CourseID SET DEFAULT ('C' || nextval('Course_Id_Sequence')::text)")
    void setCourseDefault();

    default void resetSchema() {
        dropCourseListTable();
        dropEventsTable();
        dropAssignmentTable();
        dropCourseTable();
        dropAccountTable();
        dropAccountSequence();
        dropCourseSequence();
        createAccountSequence();
        createCourseSequence();
        createAccountTable();
        createCourseTable();
        createCourseListTable();
        createAssignmentTable();
        createEventsTable();
        setAccountDefault();
        setCourseDefault();
    }
}
