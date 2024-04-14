package dao;

import domain.Assignment;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

public interface AssigmentJdbiDao extends CredentialsValidator{

    @SqlQuery("SELECT * FROM assignment ORDER BY AssignmentID")
    @RegisterBeanMapper(Assignment.class)
    public Collection<Assignment> getAssignments();

    @SqlQuery("SELECT * FROM assignment where AssignmentID=:AssignmentID")
    @RegisterBeanMapper(Assignment.class)
    public Assignment searchByAssignmentID(@Bind("AssignmentID") int AssignmentID);

    @SqlQuery("INSERT INTO Assignment (AssignmentID, CourseID, AssignmentName, AssignmentDescription, DueDate, Weight)\n" +
                "VALUES (:AssignmentID, :CourseID, :AssignmentName, :AssignmentDescription, :DueDate, :Weight);")
    public Assignment createAssignment(@BindBean Assignment assignment);

    @SqlQuery("SELECT * FROM assignment where CourseID=:CourseID")
    @RegisterBeanMapper(Assignment.class)
    public Assignment searchByCourseID(@Bind("CourseID") int CourseID);
}
