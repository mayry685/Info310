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

        get("/api/events/:EventName", ctx -> {
            String eventName = ctx.path("EventName").value();
            return eventsDAO.searchByUsername(eventName);
        });

        post("/api/CreateEvent", ctx -> {
            Event event = ctx.body().to(Event.class);
            eventsDAO.createEvent(event);
            return ctx.send(StatusCode.OK);
        });

        put("/api/UpdateEventStatus", ctx -> {
            Event event = ctx.body().to(Event.class);
            eventsDAO.updateEventStatus(event, event.isCompleted());
            return ctx.send(StatusCode.OK);
        });

        put("/api/UpdateEventDetails", ctx -> {
            Event event = ctx.body().to(Event.class);
            eventsDAO.updateEventDetails(event);
            return ctx.send(StatusCode.OK);
        });

        delete("/api/DeleteEvent/:EventID", ctx -> {
            int eventID = ctx.path("EventID").intValue();
            eventsDAO.deleteEventByEventID(eventID);
            return ctx.send(StatusCode.OK);
        });
        
    }
    
}
