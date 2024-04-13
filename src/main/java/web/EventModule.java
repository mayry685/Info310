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

        post("/api/CreateEvent", ctx -> {
            Event event = ctx.body().to(Event.class);
            eventsDAO.createEvent(event);
            return ctx.send(StatusCode.CREATED);
        });

        post("/api/UpdateEvent", ctx -> {
            Event event = ctx.body().to(Event.class);
            Boolean completed = ctx.query("Completed").booleanValue();
            eventsDAO.updateEvent(event, completed);
            return ctx.send(StatusCode.OK);
        });
        
    }
    
}
