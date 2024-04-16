package dao;

import domain.Event;

import java.util.Collection;
import java.time.LocalDateTime;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
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
    
    @SqlQuery("SELECT * FROM events where EventName=:EventName")
    @RegisterBeanMapper(Event.class)
    public Event searchByUsername(@Bind("EventName") String eventName);

    @SqlUpdate("INSERT INTO events (EventID, StartDate, EndDate, EventName, EventDescription, Location, Completed)\n" +
                "VALUES (:EventID, :StartDate, :EndDate, :EventName, :EventDescription, :Location, :Completed);")
    public void createEvent(@BindBean Event event);
    
    @SqlUpdate("UPDATE events SET Completed = :Completed WHERE EventID = :EventID")
    public void updateEventStatus(@BindBean Event event, Boolean Completed);

    @SqlUpdate("UPDATE events SET StartDate = :StartDate, EndDate = :EndDate, EventName = :EventName, EventDescription = :EventDescription, Location = :Location, Completed = :Completed WHERE EventID = :EventID;")
    public void updateEventDetails(@BindBean Event event);

    @SqlUpdate("DELETE FROM events WHERE EventID = :EventID")
    public void deleteEventByEventID(@Bind("EventID") int EventID);
}
