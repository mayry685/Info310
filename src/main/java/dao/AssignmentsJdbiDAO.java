package dao;

import domain.Assignment;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
public interface AssignmentsJdbiDAO extends CredentialsValidator {
    
    @SqlQuery("SELECT * FROM assignments ORDER BY DueDate")
    @RegisterBeanMapper(Assignment.class)
    public Collection<Assignment> getAssignments();

    @SqlQuery("SELECT * FROM assignments where AssignmentName=:AssignmentName")
    @RegisterBeanMapper(Assignment.class)
    public Assignment searchByUsername(@Bind("AssignmentName") String assignmentName);

    @SqlQuery("INSERT INTO assignments (AssignmentID, CourseID, AssignmentName, AssignmentDescription, DueDate, Weight)\n" +
                "VALUES (:AssignmentID, :CourseID, :AssignmentName, :AssignmentDescription, :DueDate, :Weight);")
    public void createAssignment(@BindBean Assignment assignment);

    @SqlUpdate("UPDATE assignments SET DueDate = :DueDate, AssignmentName = :AssignmentName, AssignmentDescription = :AssignmentDescription, Weight = :Weight WHERE AssignmentID = :AssignmentID;")
    public void updateAssignmentDetails(@BindBean Assignment assignment);

    @SqlUpdate("DELETE FROM assignments WHERE AssignmentID = :AssignmentID")
    public void deleteAssignmentByAssignmentID(@Bind("AssignmentID") int AssignmentID);
}
