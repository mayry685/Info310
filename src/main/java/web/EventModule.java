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
