package dao;

import domain.CourseList;
import java.util.Collection;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.AllowUnusedBindings;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 *
 * @author Jarrad Marshall
 */
public interface CourseListsJdbiDAO {

    @SqlQuery("SELECT * FROM CourseList")
    @RegisterBeanMapper(CourseList.class)
    public Collection<CourseList> getCourseLists();
    
    @SqlQuery("SELECT * FROM CourseList WHERE CourseListID = :CourseListID")
    @RegisterBeanMapper(CourseList.class)
    public CourseList getCourseListByID(@Bind("CourseListID") String CourseListID);
    
    @SqlQuery("SELECT CourseList.*, Course.CourseName, Course.CourseCode FROM CourseList JOIN Course ON CourseList.CourseID = Course.CourseID WHERE AccountID = :AccountID")
    @RegisterBeanMapper(CourseList.class)
    public Collection<CourseList> getCourseListsByAccount(@Bind("AccountID") String AccountID);

    @SqlQuery("SELECT * FROM CourseList WHERE CourseID = :CourseID")
    @RegisterBeanMapper(CourseList.class)
    public Collection<CourseList> getCourseListsByCourse(@Bind("CourseID") String CourseID);

    @SqlUpdate("INSERT INTO CourseList (CourseID, AccountID) VALUES (:CourseID, :AccountID)")
    @GetGeneratedKeys
    @RegisterBeanMapper(CourseList.class)
    CourseList createCourseList(@Bind("CourseID") String CourseID, @Bind("AccountID") String AccountID);
    
    @SqlUpdate("DELETE FROM CourseList WHERE CourseListID = :CourseListID")
    void deleteCourseListById(@Bind("CourseListID") String CourseListID);

    @SqlUpdate("DELETE FROM CourseList WHERE CourseListID = :CourseListID")
    void deleteCourseListById(@Bind("CourseListID") int CourseListID);

    @SqlUpdate("DELETE * FROM CourseList WHERE AccountID = :AccountID")
    void deleteCourseListsByAccount(@Bind("AccountID") String AccountID);

    @SqlUpdate("DELETE * FROM CourseList WHERE CourseID = :CourseID")
    void deleteCourseListsByCourse(@Bind("CourseID") String CourseID);
}
