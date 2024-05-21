import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.validation.Schema;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import dao.CoursesJdbiDAO;
import dao.JdbiDaoFactory;
import dao.SchemaDAO;
import domain.Course;

public class CourseTest {

    private CoursesJdbiDAO courseDao;
    private Map<String, Object> control;

    private Course course1;
    private Course course2;
    private Course course3;

    @BeforeAll
    public static void initialise(){
        JdbiDaoFactory.changeUserName("postgres.qrxbjspocpeixwpcamlx");
        JdbiDaoFactory.changeJdbcUri("jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres");
    }

    @BeforeEach
    public void setUp() throws IOException{
        courseDao = JdbiDaoFactory.getCourseDAO();
        control = new HashMap<>();

        SchemaDAO schemaDAO = JdbiDaoFactory.getSchemaDAO();
        schemaDAO.resetSchema();

        course1 = new Course();
        course1.setCourseCode("COSC2531");
        course1.setCourseName("Advanced Programming");
        course1.setCourseDescription("This course covers advanced programming concepts");

        course2 = new Course();
        course2.setCourseCode("COSC2532");
        course2.setCourseName("Database Systems");
        course2.setCourseDescription("This course covers database systems");

        course3 = new Course();
        course3.setCourseCode("COSC2533");
        course3.setCourseName("Web Development");
        course3.setCourseDescription("This course covers web development");
        
        courseDao.createCourse(course1.getCourseName(), course1.getCourseCode(), course1.getCourseDescription());
        courseDao.createCourse(course2.getCourseName(), course2.getCourseCode(), course2.getCourseDescription());
    
        String course1ID = courseDao.searchByCourseName("Advanced Programming").getCourseId();
        String course2ID = courseDao.searchByCourseName("Database Systems").getCourseId();

        course1.setCourseId(course1ID);
        course2.setCourseId(course2ID);

        control.put(course1.getCourseCode(), course1);
        control.put(course2.getCourseCode(), course2);
    }

    @AfterEach
    public void tearDown() {
        Collection<Course> courses = courseDao.getCourses();

        Iterator<Course> iterator = courses.iterator();
        while (iterator.hasNext()) {
            Course course = iterator.next();
            iterator.remove();
            courseDao.deleteCourseById(course.getCourseId());
        }
    }

    @Test
    public void testGetCourses() {
        Collection<Course> courses = courseDao.getCourses();
        assertThat(courses.toString(), is("[" + course1.toString() + ", " + course2.toString() + "]"));
    }

    @Test
    public void testSearchByCourseId() {
        Course course = courseDao.getCourseById(course1.getCourseId());
        assertEquals(course.toString(), course1.toString());
    }

    @Test
    public void testCreateCourse() {
        Collection<Course> courses = courseDao.getCourses();
        assertThat(courses.toString(), is("[" + course1.toString() + ", " + course2.toString() + "]"));
        
        courseDao.createCourse(course3.getCourseName(), course3.getCourseCode(), course3.getCourseDescription());
        String course3ID = courseDao.searchByCourseName("Web Development").getCourseId();
        course3.setCourseId(course3ID);
        control.put(course3.getCourseCode(), course3);

        courses = courseDao.getCourses();
        assertThat(courses.toString(), is("[" + course1.toString() + ", " + course2.toString() + ", " + course3.toString() + "]"));
    }

    @Test
    public void testUpdateCourse() {
        Collection<Course> courses = courseDao.getCourses();
        assertThat(courses.toString(), is("[" + course1.toString() + ", " + course2.toString() + "]"));

        course1.setCourseName("Advanced Programming 2");
        course1.setCourseDescription("This course covers advanced programming concepts 2");
        courseDao.updateCourse(course1);

        // Will move the updated course to the end of the list
        courses = courseDao.getCourses();
        assertThat(courses.toString(), is("[" + course2.toString() + ", " + course1.toString() + "]"));
    }

    @Test
    public void testDeleteCourse() {
        Collection<Course> courses = courseDao.getCourses();
        assertThat(courses.toString(), is("[" + course1.toString() + ", " + course2.toString() + "]"));

        courseDao.deleteCourseById(course1.getCourseId());
        control.remove(course1.getCourseCode());

        courses = courseDao.getCourses();
        assertThat(courses.toString(), is("[" + course2.toString() + "]"));
    }

}
