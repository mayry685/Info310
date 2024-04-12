/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.time.LocalDateTime;

/**
 *
 * @author chsur
 */
public class Event {
    
    //datafields
    private int EventID;
    private LocalDateTime StartDate;
    private LocalDateTime EndDate;
    private String EventName;
    private String EventDescription;
    private String Location;
    private boolean Completed;

    public Event(int EventID, LocalDateTime StartDate, LocalDateTime EndDate, String EventName, String EventDescription, String Location, boolean Completed) {
        this.EventID = EventID;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.EventName = EventName;
        this.EventDescription = EventDescription;
        this.Location = Location;
        this.Completed = Completed;
    }

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int EventID) {
        this.EventID = EventID;
    }

    public LocalDateTime getStartDate() {
        return StartDate;
    }

    public void setStartDate(LocalDateTime StartDate) {
        this.StartDate = StartDate;
    }

    public LocalDateTime getEndDate() {
        return EndDate;
    }

    public void setEndDate(LocalDateTime EndDate) {
        this.EndDate = EndDate;
    }

    public String getEventName() {
        return EventName;
    }

    public void setEventName(String EventName) {
        this.EventName = EventName;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String EventDescription) {
        this.EventDescription = EventDescription;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String Location) {
        this.Location = Location;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean Completed) {
        this.Completed = Completed;
    }

    @Override
    public String toString() {
        return "Event{" + "EventID=" + EventID + ", StartDate=" + StartDate + ", EndDate=" + EndDate + ", EventName=" + EventName + ", EventDescription=" + EventDescription + ", Location=" + Location + ", Completed=" + Completed + '}';
    }
    
}
