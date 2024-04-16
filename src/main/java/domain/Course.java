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
    private String CourseDescription;

    public Course(String CourseId, String CourseName, String CourseDescription) {
        this.CourseId = CourseId;
        this.CourseName = CourseName;
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
