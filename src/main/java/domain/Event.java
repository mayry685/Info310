/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *
 * @author chsur
 */
public class Event {
    
    //datafields
    private int EventID;
    private Timestamp StartDate;
    private Timestamp EndDate;
    private String EventName;
    private String EventDescription;
    private String Location;
    private String AccountID;
    private String CourseID;
    private boolean Completed;

    public Event() {
    }

    public Event(int EventID, Timestamp StartDate, Timestamp EndDate, String EventName, String EventDescription, String Location, String AccountID, String CourseID, boolean Completed) {
        this.EventID = EventID;
        this.StartDate = StartDate;
        this.EndDate = EndDate;
        this.EventName = EventName;
        this.EventDescription = EventDescription;
        this.Location = Location;
        this.AccountID = AccountID;
        this.CourseID = CourseID;
        this.Completed = Completed;
    }

    public int getEventID() {
        return EventID;
    }

    public void setEventID(int EventID) {
        this.EventID = EventID;
    }

    public Timestamp getStartDate() {
        return StartDate;
    }

    public void setStartDate(Timestamp StartDate) {
        this.StartDate = StartDate;
    }

    public Timestamp getEndDate() {
        return EndDate;
    }

    public void setEndDate(Timestamp EndDate) {
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

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String AccountID) {
        this.AccountID = AccountID;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String CourseID) {
        this.CourseID = CourseID;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean Completed) {
        this.Completed = Completed;
    }

    @Override
    public String toString() {
        return "Event{" + "EventID=" + EventID + ", StartDate=" + StartDate + ", EndDate=" + EndDate + ", EventName=" + EventName + ", EventDescription=" + EventDescription + ", Location=" + Location + ", AccountID=" + AccountID + ", CourseID=" + CourseID + ", Completed=" + Completed + '}';
    }
    
}
