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

import dao.CoursesJdbiDAO;
import dao.EventsJdbiDAO;
import dao.JdbiDaoFactory;
import dao.SchemaDAO;
import domain.Course;
import domain.Event;

public class EventTest {
    private EventsJdbiDAO eventsDao;
    private CoursesJdbiDAO courseDao;
    private Map<String, Object> control;

    private Course course1;
    private Course course2;


    public Event event1;
    public Event event2;
    public Event event3;

    @BeforeAll
    public static void initialise(){
        JdbiDaoFactory.changeUserName("postgres.qrxbjspocpeixwpcamlx");
        JdbiDaoFactory.changeJdbcUri("jdbc:postgresql://aws-0-ap-southeast-2.pooler.supabase.com:5432/postgres");
    }

    @BeforeEach
    public void setUp() throws IOException {
        eventsDao = JdbiDaoFactory.getEventsDAO();
        courseDao = JdbiDaoFactory.getCourseDAO();
        control = new HashMap<>();

        SchemaDAO schemaDao = JdbiDaoFactory.getSchemaDAO();
        schemaDao.resetSchema();

        course1 = new Course("1", "Math", "MATH130", "Mathematics");
        course2 = new Course("2", "Chemistry", "CHEM191", "Chemistry");

        courseDao.createCourse(course1.getCourseName(), course1.getCourseCode(), course1.getCourseDescription());
        courseDao.createCourse(course2.getCourseName(), course2.getCourseCode(), course2.getCourseDescription());
        


        event1 = new Event();
        event1.setEventID(1);
        Timestamp startDate1 = Timestamp.valueOf("2024-06-01 00:12:00");
        event1.setStartDate(startDate1);
        Timestamp endDate1 = Timestamp.valueOf("2024-06-01 00:13:00");
        event1.setEndDate(endDate1);
        event1.setEventName("Study");
        event1.setEventDescription("Learning time");
        event1.setLocation("Library");
        event1.setCompleted(false);

        event2 = new Event();
        event2.setEventID(2);
        Timestamp startDate2 = Timestamp.valueOf("2024-06-02 00:12:00");
        event2.setStartDate(startDate2);
        Timestamp endDate2 = Timestamp.valueOf("2024-06-02 00:13:00");
        event2.setEndDate(endDate2);
        event2.setEventName("Work");
        event2.setEventDescription("Working time");
        event2.setLocation("Office");
        event2.setCompleted(true);

        event3 = new Event();
        event3.setEventID(3);
        Timestamp startDate3 = Timestamp.valueOf("2024-06-03 00:12:00");
        event3.setStartDate(startDate3);
        Timestamp endDate3 = Timestamp.valueOf("2024-06-03 00:13:00");
        event3.setEndDate(endDate3);
        event3.setEventName("Exercise");
        event3.setEventDescription("Workout time");
        event3.setLocation("Gym");
        event3.setCompleted(false);

        eventsDao.createEvent(event1);
        eventsDao.createEvent(event2);

        control.put("e1", event1);
        control.put("e2", event2);
    }

    @AfterEach
    public void tearDown() {
        Collection<Event> events = eventsDao.getEvents();

        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event e = iterator.next();
            iterator.remove();
            eventsDao.deleteEventByEventID(e.getEventID());
        }
    }

    @Test
    public void testGetEvents(){
        Collection<Event> events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));
    }
    

    @Test
    public void testDeleteEvent() {
        Collection<Event> events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));

        eventsDao.deleteEventByEventID(event1.getEventID());
        events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event2.toString() + "]"));

    }

    @Test
    public void testAddEvent() {
        Collection<Event> events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));

        eventsDao.createEvent(event3);
        events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + ", " + event3.toString() + "]"));
    }

    @Test
    public void testUpdateEventStatus() {
        Collection<Event> events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));

        eventsDao.updateEventStatus(true, event1.getEventID());
        event1.setCompleted(true);

        events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));

        eventsDao.updateEventStatus(false, event2.getEventID());
        event2.setCompleted(false);

        events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));
    }

    @Test
    public void testUpdateEventDetails() {
        Collection<Event> events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));

        event1.setEventName("Study2");
        event1.setEventDescription("Learning time2");
        event1.setLocation("Library2");

        eventsDao.updateEventDetails(event1);

        events = eventsDao.getEvents();
        assertThat(events.toString() , is("[" + event1.toString() + ", " + event2.toString() + "]"));
    }
}
