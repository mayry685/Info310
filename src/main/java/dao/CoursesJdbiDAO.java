package dao;

import domain.Course;
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

    @SqlUpdate("INSERT INTO Course (CourseName, CourseCode, CourseDescription) VALUES (:courseName, :courseCode, :courseDescription)")
    @GetGeneratedKeys
    @RegisterBeanMapper(Course.class)
    Course createCourse(@Bind("courseName") String courseName, @Bind("courseCode") String courseCode,
            @Bind("courseDescription") String courseDescription);

    @SqlUpdate("UPDATE Course SET CourseName=:courseName, CourseCode=:courseCode, CourseDescription=:courseDescription WHERE CourseID=:courseId")
    void updateCourse(@BindBean Course course);

    @SqlUpdate("DELETE FROM Course WHERE CourseID = :courseId")
    void deleteCourseById(@Bind("courseId") String courseId);

    @SqlQuery("SELECT * FROM course WHERE courseName = :courseName")
    @RegisterBeanMapper(Course.class)
    public Course searchByCourseName(@Bind("courseName") String courseName);
}
