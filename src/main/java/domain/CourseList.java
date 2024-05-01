/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author chsur
 */
public class CourseList {
    
    //datafields
    private String CourseListID;
    private String CourseID;
    private String AccountID;
    private String CourseName;

    public CourseList(String CourseListID, String CourseID, String AccountID) {
        this.CourseListID = CourseListID;
        this.CourseID = CourseID;
        this.AccountID = AccountID;
    }

    public CourseList(String CourseListID, String CourseID, String AccountID, String CourseName) {
        this.CourseListID = CourseListID;
        this.CourseID = CourseID;
        this.AccountID = AccountID;
        this.CourseName = CourseName;
    }
    
    public CourseList() {
        
    }

    public String getCourseListID() {
        return CourseListID;
    }

    public void setCourseListID(String CourseListID) {
        this.CourseListID = CourseListID;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setCourseID(String CourseID) {
        this.CourseID = CourseID;
    }

    public String getAccountID() {
        return AccountID;
    }

    public void setAccountID(String AccountID) {
        this.AccountID = AccountID;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    @Override
    public String toString() {
        return "CourseList{" + "CourseListID=" + CourseListID + ", CourseID=" + CourseID + ", AccountID=" + AccountID + '}';
    }
    
}
