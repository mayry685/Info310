import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import dao.AssignmentsJdbiDAO;
import dao.CoursesJdbiDAO;
import dao.JdbiDaoFactory;
import domain.Assignment;
import domain.Course;
import dao.SchemaDAO;

public class AssignmentTest {

    private AssignmentsJdbiDAO assignmentsDao;
    private CoursesJdbiDAO courseDao;
    private Map<String, Object> control;

    private Course course1;
    private Course course2;

    public Assignment assignment1;
    public Assignment assignment2;
    public Assignment assignment3;

    @BeforeAll
    public static void initialise() {
        JdbiDaoFactory.changeUserName("postgres.qrxbjspocpeixwpcamlx");
        JdbiDaoFactory.changeJdbcUri("jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres");
    }

    @BeforeEach
    public void setUp() throws IOException {
        assignmentsDao = JdbiDaoFactory.getAssignmentsDAO();
        courseDao = JdbiDaoFactory.getCourseDAO();
        control = new HashMap<>();

        SchemaDAO schemaDAO = JdbiDaoFactory.getSchemaDAO();
        schemaDAO.resetSchema();

        courseDao.createCourse("Math", "MATH130", "Mathematics");
        courseDao.createCourse("Chemistry", "CHEM191", "Chemistry");

        course1 = courseDao.searchByCourseName("Math");
        course2 = courseDao.searchByCourseName("Chemistry");

        assert course1 != null : "Course 'Math' should be created.";
        assert course2 != null : "Course 'Chemistry' should be created.";

        assignment1 = new Assignment();
        assignment1.setAssignmentID(1);
        assignment1.setCourseID(course1.getCourseId());
        assignment1.setAssignmentName("Assignment 1");
        assignment1.setAssignmentDescription("Assignment 1 Description");
        Timestamp dueDate1 = Timestamp.valueOf("2024-06-01 00:13:00");
        assignment1.setDueDate(dueDate1);
        assignment1.setWeight(10);

        assignment2 = new Assignment();
        assignment2.setAssignmentID(2);
        assignment2.setCourseID(course2.getCourseId());
        assignment2.setAssignmentName("Assignment 2");
        assignment2.setAssignmentDescription("Assignment 2 Description");
        Timestamp dueDate2 = Timestamp.valueOf("2024-06-01 00:13:00");
        assignment2.setDueDate(dueDate2);
        assignment2.setWeight(20);

        assignment3 = new Assignment();
        assignment3.setAssignmentID(3);
        assignment3.setCourseID(course1.getCourseId());
        assignment3.setAssignmentName("Assignment 3");
        assignment3.setAssignmentDescription("Assignment 3 Description");
        Timestamp dueDate3 = Timestamp.valueOf("2024-06-01 00:13:00");
        assignment3.setDueDate(dueDate3);
        assignment3.setWeight(30);

        assignmentsDao.createAssignment(assignment1);
        assignmentsDao.createAssignment(assignment2);

        control.put("A1", assignment1);
        control.put("A2", assignment2);
    }

    @AfterEach
    public void tearDown() {
        Collection<Assignment> assignments = assignmentsDao.getAssignments();

        Iterator<Assignment> iterator = assignments.iterator();
        while (iterator.hasNext()) {
            Assignment assignment = iterator.next();
            iterator.remove();
            assignmentsDao.deleteAssignmentByAssignmentID(assignment.getAssignmentID());
        }
    }

    @Test
    public void testGetAssignments() {
        Collection<Assignment> assignments = assignmentsDao.getAssignments();
        assertThat(assignments.toString(), is("[" + assignment1.toString() + ", " + assignment2.toString() + "]"));
    }

    @Test
    public void testSearchByAssignmentName() {
        Assignment assignment = assignmentsDao.searchByAssignmentName("Assignment 1");
        assertEquals(assignment.toString(), assignment1.toString());
    }

    @Test
    public void testSearchByAssignmentID() {
        Assignment assignment = assignmentsDao.getByAssignmentID(2);
        assertEquals(assignment.toString(), assignment2.toString());
    }

    @Test
    public void testCreateAssignment() {
        Collection<Assignment> assignments = assignmentsDao.getAssignments();
        assertThat(assignments.toString(), is("[" + assignment1.toString() + ", " + assignment2.toString() + "]"));
        
        assignmentsDao.createAssignment(assignment3);
        assignments = assignmentsDao.getAssignments();
        assertThat(assignments.toString(), is("[" + assignment1.toString() + ", " + assignment2.toString() + ", " + assignment3.toString() + "]"));
    }

    @Test
    public void testUpdateAssignmentDetails() {
        Collection<Assignment> assignments = assignmentsDao.getAssignments();
        assertThat(assignments.toString(), is("[" + assignment1.toString() + ", " + assignment2.toString() + "]"));

        assignment1.setAssignmentName("Assignment 1 Updated");
        assignment1.setAssignmentDescription("Assignment 1 Description Updated");
        Timestamp dueDate1 = Timestamp.valueOf("2024-06-01 00:14:00");

        assignmentsDao.updateAssignmentDetails(assignment1);

        assignments = assignmentsDao.getAssignments();
        assertThat(assignments.toString(), is("[" + assignment1.toString() + ", " + assignment2.toString() + "]"));
    }

    @Test
    public void testDeleteAssignmentById() {
        Collection<Assignment> assignments = assignmentsDao.getAssignments();
        assertThat(assignments.toString(), is("[" + assignment1.toString() + ", " + assignment2.toString() + "]"));

        assignmentsDao.deleteAssignmentByAssignmentID(1);

        assignments = assignmentsDao.getAssignments();
        assertThat(assignments.toString(), is("[" + assignment2.toString() + "]"));
    }

}
