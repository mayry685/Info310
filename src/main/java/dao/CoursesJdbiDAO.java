package dao;

import domain.Course;
import domain.CourseList;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author [Corban Surtees
 */
public interface CoursesJdbiDAO extends CredentialsValidator {
    
    @SqlQuery("SELECT * FROM Course")
    @RegisterBeanMapper(Course.class)
    public Collection<Course> getCourses();
    
    @SqlQuery("SELECT * FROM Course WHERE CourseID = :courseId")
    @RegisterBeanMapper(Course.class)
    public Course getCourseById(@Bind("courseId") String courseId);
    
    @SqlUpdate("INSERT INTO Course (CourseName, CourseDescription) VALUES (:courseName, :courseDescription)")
    @GetGeneratedKeys
    @RegisterBeanMapper(Course.class)
    Course createCourse(@Bind("courseName") String courseName, @Bind("courseDescription") String courseDescription);

    @SqlUpdate("UPDATE Course SET CourseName=:courseName, CourseDescription=:courseDescription WHERE CourseID=:courseId")
    void updateCourse(@BindBean Course course);
    
    @SqlUpdate("DELETE FROM Course WHERE CourseID = :courseId")
    void deleteCourseById(@Bind("courseId") String courseId);

    @SqlUpdate("INSERT INTO CourseList (CourseID, AccountId) VALUES (:courseID, :accountID)")
    @RegisterBeanMapper(CourseList.class)
    CourseList createCourseList(@Bind("courseID") String courseID, @Bind("accountID") String accountID);

    @SqlUpdate("DELETE FROM CourseList WHERE CourseListID=:courseListId")
    void deleteCourseListById(@Bind("courseListId") String courseListId);
}
