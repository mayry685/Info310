package web;

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
            return eventsDAO.searchByUsername(eventName);
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
            Event event = ctx.body().to(Event.class);
            eventsDAO.updateEventStatus(event, event.isCompleted());
            return ctx.send(StatusCode.OK);
        });

        put("/api/events/UpdateEventDetails", ctx -> {
            Event event = ctx.body().to(Event.class);
            eventsDAO.updateEventDetails(event);
            return ctx.send(StatusCode.OK);
        });

        delete("/api/events/deleteEventByID", ctx -> {
            int eventID = ctx.query("EventID").intValue();
            eventsDAO.deleteEventByEventID(eventID);
            return ctx.send(StatusCode.OK);
        });
        
    }
    
}
