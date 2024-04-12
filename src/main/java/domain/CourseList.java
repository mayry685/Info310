/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author chsur
 */
class CourseList {
    
    //datafields
    private int CourseListID;
    private int CourseID;
    private int AccountID;

    public CourseList(int CourseListID, int CourseID, int AccountID) {
        this.CourseListID = CourseListID;
        this.CourseID = CourseID;
        this.AccountID = AccountID;
    }

    public int getCourseListID() {
        return CourseListID;
    }

    public void setCourseListID(int CourseListID) {
        this.CourseListID = CourseListID;
    }

    public int getCourseID() {
        return CourseID;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int AccountID) {
        this.AccountID = AccountID;
    }

    @Override
    public String toString() {
        return "CourseList{" + "CourseListID=" + CourseListID + ", CourseID=" + CourseID + ", AccountID=" + AccountID + '}';
    }
    
}
