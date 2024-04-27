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

    @SqlUpdate("INSERT INTO events (StartDate, EndDate, EventName, EventDescription, Location, Completed)\n" +
            "VALUES (:event.startDate, :event.endDate, :event.eventName, :event.eventDescription, :event.location, :event.completed);")
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
