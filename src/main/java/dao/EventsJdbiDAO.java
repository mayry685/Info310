package dao;

import domain.Event;

import java.util.Collection;
import java.time.LocalDateTime;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/**
 * 
 * @Author Kevin Albert
 */
public interface EventsJdbiDAO extends CredentialsValidator {

    @SqlQuery("SELECT * FROM events ORDER BY EventID")
    @RegisterBeanMapper(Event.class)
    public Collection<Event> getEvents();

    @SqlQuery("SELECT * FROM events WHERE EventID = :EventID")
    @RegisterBeanMapper(Event.class)
    public Event getEventById(@Bind("EventID") int EventID);

    @SqlQuery("SELECT * FROM events WHERE EventName = :EventName")
    @RegisterBeanMapper(Event.class)
    public Collection<Event> searchByEventName(@Bind("EventName") String eventName);
    
    @SqlQuery("SELECT * FROM events WHERE CourseID = :courseID")
    @RegisterBeanMapper(Event.class)
    public Collection<Event> searchByCourseId(@Bind("CourseID") String courseID);
    
    @SqlQuery("SELECT * FROM events WHERE AccountID = :AccountID")
    @RegisterBeanMapper(Event.class)
    public Collection<Event> searchByAccountId(@Bind("AccountID") String accountID);
    
    @SqlQuery("SELECT DISTINCT e.*\n" +
"FROM events e\n" +
"left join courselist cl ON e.CourseID = cl.courseid\n" +
"WHERE e.accountid = :AccountID OR cl.accountid = :AccountID;")
    @RegisterBeanMapper(Event.class)
    public Collection<Event> eventsByAccount(@Bind("AccountID") String accountID);
    

    @SqlUpdate("INSERT INTO events (StartDate, EndDate, EventName, EventDescription, Location, AccountID, CourseID, Completed)\n" +
            "VALUES (:event.startDate, :event.endDate, :event.eventName, :event.eventDescription, :event.location, :event.accountID, :event.courseID, :event.completed);")
    @GetGeneratedKeys
    @RegisterBeanMapper(Event.class)
    public Event createEvent(@BindBean("event") Event event);

    @SqlUpdate("UPDATE events SET Completed = :completed WHERE EventID = :eventId")
    public void updateEventStatus(@Bind("completed") boolean completed, @Bind("eventId") int eventId);

    @SqlUpdate("UPDATE events SET StartDate = :startDate, EndDate = :endDate, EventName = :eventName, EventDescription = :eventDescription, Location = :location, Completed = :completed WHERE EventID = :eventID")
    public void updateEventDetails(@BindBean Event event);

    @SqlUpdate("DELETE FROM events WHERE EventID = :EventID")
    public void deleteEventByEventID(@Bind("EventID") int EventID);
}
