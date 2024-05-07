package dao;

import domain.Assignment;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
public interface AssignmentsJdbiDAO extends CredentialsValidator {
    
    @SqlQuery("SELECT * FROM assignment ORDER BY AssignmentID")
    @RegisterBeanMapper(Assignment.class)
    public Collection<Assignment> getAssignments();

    @SqlQuery("SELECT * FROM assignment where AssignmentName=:AssignmentName")
    @RegisterBeanMapper(Assignment.class)
    public Assignment searchByAssignmentName(@Bind("AssignmentName") String assignmentName);

    @SqlQuery("SELECT * FROM assignment WHERE AssignmentID = :AssignmentID")
    @RegisterBeanMapper(Assignment.class)
    public Assignment getByAssignmentID(@Bind("AssignmentID") int AssignmentID);
    
    @SqlQuery("SELECT * FROM assignment WHERE CourseID = :CourseID")
    @RegisterBeanMapper(Assignment.class)
    public Collection<Assignment> getByCourseID(@Bind("CourseID") int CourseID);
    
    @SqlQuery("SELECT DISTINCT a.*, c.coursename\n" +
"FROM assignment a\n" +
"left join courselist cl ON a.CourseID = cl.courseid\n" +
"LEFT JOIN course c ON cl.CourseID = c.CourseID\n" +
"WHERE cl.accountid = :AccountID;")
    @RegisterBeanMapper(Assignment.class)
    public Collection<Assignment> getByAccountID(@Bind("AccountID") String accountID);

    @SqlUpdate("INSERT INTO assignment (CourseID, AssignmentName, AssignmentDescription, DueDate, Weight)\n" +
                "VALUES (:assignment.courseID, :assignment.assignmentName, :assignment.assignmentDescription, :assignment.dueDate, :assignment.weight);")
    @GetGeneratedKeys
    @RegisterBeanMapper(Assignment.class)
    public Assignment createAssignment(@BindBean("assignment") Assignment assignment);

    @SqlUpdate("UPDATE assignment SET DueDate = :dueDate, AssignmentName = :assignmentName, AssignmentDescription = :assignmentDescription, Weight = :weight WHERE AssignmentID = :assignmentID;")
    public void updateAssignmentDetails(@BindBean Assignment assignment);

    @SqlUpdate("DELETE FROM assignment WHERE AssignmentID = :AssignmentID")
    public void deleteAssignmentByAssignmentID(@Bind("AssignmentID") int AssignmentID);
}
