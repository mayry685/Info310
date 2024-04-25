package web;

import ch.qos.logback.core.status.Status;
import dao.EventsJdbiDAO;
import domain.Event;
import io.jooby.Jooby;
import io.jooby.StatusCode;


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

        post("/api/events/CreateEvent", ctx -> {
            Event event = ctx.body().to(Event.class);
            eventsDAO.createEvent(event);
            return ctx.send(StatusCode.OK);
        });

        put("/api/events/UpdateEventStatus", ctx -> {
            boolean completed = ctx.query("Completed").booleanValue();
            int eventId = ctx.query("EventID").intValue(); 
            eventsDAO.updateEventStatus(completed, eventId);
            return ctx.send(StatusCode.OK);
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
            eventsDAO.deleteEventByEventID(eventID);
            return ctx.send(StatusCode.OK);
        });
        
    }
    
}
