/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

/**
 *
 * @author chsur
 */
public class Course {
    
    //datafields
    private String CourseId;
    private String CourseName;
    private String CourseCode;
    private String CourseDescription;

    public Course(String CourseId, String CourseName, String CourseCode, String CourseDescription) {
        this.CourseId = CourseId;
        this.CourseName = CourseName;
        this.CourseCode = CourseCode;
        this.CourseDescription = CourseDescription;
    }
    
    public Course(){
        
    }

    public String getCourseId() {
        return CourseId;
    }

    public void setCourseId(String CourseId) {
        this.CourseId = CourseId;
    }

    public String getCourseName() {
        return CourseName;
    }

    public void setCourseName(String CourseName) {
        this.CourseName = CourseName;
    }

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String CourseCode) {
        this.CourseCode = CourseCode;
    }

    public String getCourseDescription() {
        return CourseDescription;
    }

    public void setCourseDescription(String CourseDescription) {
        this.CourseDescription = CourseDescription;
    }

    @Override
    public String toString() {
        return "Course{" + "CourseId=" + CourseId + ", CourseName=" + CourseName + ", CourseDescription=" + CourseDescription + '}';
    }
    
}
