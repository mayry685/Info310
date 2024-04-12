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
public class Assignment {
    
    //datafields
    private int AssignmentID;
    private int CourseID;
    private String AssignmentName;
    private String AssignmentDescription;
    private LocalDateTime DueDate;
    private int Weight;

    public Assignment(int AssignmentID, int CourseID, String AssignmentName, String AssignmentDescription, LocalDateTime DueDate, int Weight) {
        this.AssignmentID = AssignmentID;
        this.CourseID = CourseID;
        this.AssignmentName = AssignmentName;
        this.AssignmentDescription = AssignmentDescription;
        this.DueDate = DueDate;
        this.Weight = Weight;
    }

    public int getAssignmentID() {
        return AssignmentID;
    }

    public void setAssignmentID(int AssignmentID) {
        this.AssignmentID = AssignmentID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public void setAssignmentName(String AssignmentName) {
        this.AssignmentName = AssignmentName;
    }

    public String getAssignmentDescription() {
        return AssignmentDescription;
    }

    public void setAssignmentDescription(String AssignmentDescription) {
        this.AssignmentDescription = AssignmentDescription;
    }

    public LocalDateTime getDueDate() {
        return DueDate;
    }

    public void setDueDate(LocalDateTime DueDate) {
        this.DueDate = DueDate;
    }

    public int getWeight() {
        return Weight;
    }

    public void setWeight(int Weight) {
        this.Weight = Weight;
    }

    @Override
    public String toString() {
        return "Assignment{" + "AssignmentID=" + AssignmentID + ", CourseID=" + CourseID + ", AssignmentName=" + AssignmentName + ", AssignmentDescription=" + AssignmentDescription + ", DueDate=" + DueDate + ", Weight=" + Weight + '}';
    }
    
}
