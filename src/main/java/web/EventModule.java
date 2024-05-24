package web;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import dao.EventsJdbiDAO;
import domain.Event;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import io.jooby.gson.GsonModule;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Type;
import ch.qos.logback.core.status.Status;


public class EventModule extends Jooby{

    public EventModule(EventsJdbiDAO eventsDAO) {
        
        get("/api/events", ctx -> {
            return eventsDAO.getEvents();
        });

        get("/api/events/searchByEventName", ctx -> {
            String eventName = ctx.query("EventName").value();
            return eventsDAO.searchByEventName(eventName);
        });

        get("/api/events/searchByEventID", ctx -> {
            int eventID = ctx.query("EventID").intValue();
            return eventsDAO.getEventById(eventID);
        });
        
        get("/api/events/searchByCourseID", ctx -> {
            String courseID = ctx.query("CourseID").value();
            return eventsDAO.searchByCourseId(courseID);
        });
        
        get("/api/events/searchByAccountID", ctx -> {
            String accountID = ctx.query("AccountID").value();
            return eventsDAO.searchByAccountId(accountID);
        });
        
        get("/api/events/searchByEventID", ctx -> {
            int eventID = ctx.query("EventID").intValue();
            return eventsDAO.getEventById(eventID);
        });
        
        get ("/api/events/account", ctx -> {
           String accountID = ctx.query("AccountID").value();
           return eventsDAO.eventsByAccount(accountID);
        });

        install(new GsonModule(new GsonBuilder()
        .registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            DateFormat df = new SimpleDateFormat("mm dd, yyyy, h:mm:ss a");

            @Override
            public Date deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
                throws JsonParseException {
            try {
                return df.parse(json.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
            }
        })
        .registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            DateFormat df = new SimpleDateFormat("mm dd, yyyy, h:mm:ss a");

            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(df.format(src));
            }
        }).create()));

        post("/api/events/CreateEvent", ctx -> {
            Event event = ctx.body().to(Event.class);
            Event createdEvent = eventsDAO.createEvent(event);
            ctx.setResponseCode(StatusCode.OK);
            return createdEvent;
        });

        put("/api/events/UpdateEventStatus", ctx -> {
            boolean completed = ctx.query("Completed").booleanValue();
            int eventId = ctx.query("EventID").intValue(); 
            if (eventsDAO.getEventById(eventId) == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "Event with ID '" + eventId + "' does not exist";
            }
            eventsDAO.updateEventStatus(completed, eventId);
            ctx.setResponseCode(StatusCode.OK);
            return "Event status updated successfully";
        });

        put("/api/events/UpdateEventDetails", ctx -> {
            Event event = ctx.body().to(Event.class);
            if (eventsDAO.getEventById(event.getEventID()) == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "Event with ID '" + event.getEventID() + "' does not exist";
            }
            eventsDAO.updateEventDetails(event);
        
            return "Event updated successfully";
        });

        delete("/api/events/deleteEventByID", ctx -> {
            int eventID = ctx.query("EventID").intValue();
            if (eventsDAO.getEventById(eventID) == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "Event with ID '" + eventID + "' does not exist";
            }
            eventsDAO.deleteEventByEventID(eventID);
            ctx.setResponseCode(StatusCode.OK);
            return "Event deleted successfully";
        });
        
    }
    
}
