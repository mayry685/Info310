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

import dao.AccountJdbiDAO;
import dao.CourseListsJdbiDAO;
import dao.CoursesJdbiDAO;
import dao.JdbiDaoFactory;
import dao.SchemaDAO;
import domain.Account;
import domain.Course;
import domain.CourseList;

public class CourseListTest {

    private CourseListsJdbiDAO courseListDao;
    private AccountJdbiDAO accountDao;
    private CoursesJdbiDAO courseDao;
    private Map<String, Object> control;

    private Course course1;
    private Course course2;

    private Account account1;
    private Account account2;

    private CourseList courseList1;
    private CourseList courseList2;
    private CourseList courseList3;

    @BeforeAll
    public static void initialise(){
        JdbiDaoFactory.changeUserName("postgres.qrxbjspocpeixwpcamlx");
        JdbiDaoFactory.changeJdbcUri("jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres");
    }

    @BeforeEach
    public void setUp() throws IOException{
        courseListDao = JdbiDaoFactory.getCourseListDAO();
        accountDao = JdbiDaoFactory.getAccountDAO();
        courseDao = JdbiDaoFactory.getCourseDAO();
        control = new HashMap<>();

        SchemaDAO schemaDao = JdbiDaoFactory.getSchemaDAO();
        schemaDao.resetSchema();

        course1 = new Course("1", "Math", "MATH130", "Mathematics");
        course2 = new Course("2", "Chemistry", "CHEM191", "Chemistry");

        courseDao.createCourse(course1.getCourseName(), course1.getCourseCode(), course1.getCourseDescription());
        courseDao.createCourse(course2.getCourseName(), course2.getCourseCode(), course2.getCourseDescription());
        
        account1 = new Account();
        account1.setAccountCode("acc001");
        account1.setFirstName("John");
        account1.setLastName("Doe");
        account1.setUserName("johndoe");
        account1.setPassword("password");
        account1.setEmail("johnDoe@gmail.com");
        account1.setStatus("Student");

        account2 = new Account();
        account2.setAccountCode("acc002");
        account2.setFirstName("Jane");
        account2.setLastName("Doe");
        account2.setUserName("janedoe");
        account2.setPassword("password");
        account2.setEmail("janeDoe@gmail.com");
        account2.setStatus("Student");

        accountDao.createAccount(account1);
        accountDao.createAccount(account2);

        account1.setAccountId(accountDao.getAccountsByUsername("johndoe").getAccountId());
        account2.setAccountId(accountDao.getAccountsByUsername("janedoe").getAccountId());

        courseList1 = new CourseList();
        courseList1.setCourseID(courseDao.searchByCourseName("Math").getCourseId());
        courseList1.setAccountID(account1.getAccountId());

        courseList2 = new CourseList();
        courseList2.setCourseID(courseDao.searchByCourseName("Chemistry").getCourseId());
        courseList2.setAccountID(account1.getAccountId());

        courseList3 = new CourseList();
        courseList3.setCourseID(courseDao.searchByCourseName("Math").getCourseId());
        courseList3.setAccountID(account2.getAccountId());

        courseListDao.createCourseList(courseList1.getCourseID(), courseList1.getAccountID());
        courseListDao.createCourseList(courseList2.getCourseID(), courseList2.getAccountID());

        Collection<CourseList> courseLists = courseListDao.getCourseLists();
        Iterator<CourseList> iterator = courseLists.iterator();
        courseList1.setCourseListID(iterator.next().getCourseListID());
        courseList2.setCourseListID(iterator.next().getCourseListID());
        
        control.put("cl1", courseList1);
        control.put("cl2", courseList2);
    }

    @AfterEach
    public void tearDown() {
        Collection<CourseList> courseLists = courseListDao.getCourseLists();

        Iterator<CourseList> iterator = courseLists.iterator();
        while (iterator.hasNext()) {
            CourseList cl = iterator.next();
            int courseListId = Integer.parseInt(cl.getCourseListID());
            iterator.remove();
            courseListDao.deleteCourseListById(courseListId);
        }
    }

    @Test
    public void testGetCourseLists() {
        Collection<CourseList> courseLists = courseListDao.getCourseLists();
        assertThat(courseLists.toString(), is("[" + courseList1.toString() + ", " + courseList2.toString() + "]"));
    }

    @Test
    public void testCreateCourseList() {
        Collection<CourseList> courseLists = courseListDao.getCourseLists();
        assertThat(courseLists.toString(), is("[" + courseList1.toString() + ", " + courseList2.toString() + "]"));

        courseListDao.createCourseList(courseList3.getCourseID(), courseList3.getAccountID());
        courseList3.setCourseListID(courseListDao.getCourseLists().stream().filter(cl -> cl.getCourseID().equals(courseList3.getCourseID()) && cl.getAccountID().equals(courseList3.getAccountID())).findFirst().get().getCourseListID());
        courseLists = courseListDao.getCourseLists();
        assertThat(courseLists.toString(), is("[" + courseList1.toString() + ", " + courseList2.toString() + ", " + courseList3.toString() + "]"));
    }

    @Test
    public void testGetCourseListByAccount() {
        Collection<CourseList> courseLists = courseListDao.getCourseListsByAccount(account1.getAccountId());
        assertThat(courseLists.toString(), is("[" + courseList1.toString() + ", " + courseList2.toString() + "]"));
    }

    @Test
    public void testGetCourseListByCourseId() {
        Collection<CourseList> courseLists = courseListDao.getCourseListsByCourse(courseList1.getCourseID().toString());
        assertThat(courseLists.toString(), is("[" + courseList1.toString() +"]"));

        courseListDao.createCourseList(courseList3.getCourseID(), courseList3.getAccountID());
        courseList3.setCourseListID(courseListDao.getCourseLists().stream().filter(cl -> cl.getCourseID().equals(courseList3.getCourseID()) && cl.getAccountID().equals(courseList3.getAccountID())).findFirst().get().getCourseListID());
        courseLists = courseListDao.getCourseLists();

        courseLists = courseListDao.getCourseListsByCourse(courseList1.getCourseID().toString());
        assertThat(courseLists.toString(), is("[" + courseList1.toString() + ", " + courseList3.toString() + "]"));
    }

    @Test
    public void testDeleteCourseListById() {
        Collection<CourseList> courseLists = courseListDao.getCourseLists();
        assertThat(courseLists.toString(), is("[" + courseList1.toString() + ", " + courseList2.toString() + "]"));

        int courseListId = Integer.parseInt(courseList1.getCourseListID());
        courseListDao.deleteCourseListById(courseListId);
        courseLists = courseListDao.getCourseLists();
        assertThat(courseLists.toString(), is("[" + courseList2.toString() + "]"));
    }
}
