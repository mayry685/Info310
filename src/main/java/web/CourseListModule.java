package web;

import dao.CourseListsJdbiDAO;
import java.util.Collection;
import domain.CourseList;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/**
 * 
 * @author Jarrad Marshall
 */

public class CourseListModule extends Jooby {
    public CourseListModule(CourseListsJdbiDAO dao) {

        get("/api/course", ctx -> {

            Collection<CourseList> courseLists = dao.getCourseLists();
            if (courseLists == null) {
                ctx.send(StatusCode.NOT_FOUND);
                return "CourseList not found";
            }
            System.err.println(courseLists);
            return courseLists;
        });

        //GET endpoint to retrieve a list of courseLists by accountID
        get("/api/course/accountId", ctx -> {
            //get accountId from path parameter
            String accountId = ctx.query("id").value();
            //retreieve courselist from DAO
            Collection<CourseList> courseList = dao.getCourseListsByAccount(accountId);
          
            if (courseList == null) {
                ctx.send(StatusCode.NOT_FOUND);
                return "Course not found";
            }
            //returns courseList
            return courseList;
        });

        
        //GET endpoint to retrieve a list of courseLists by courseID
        get("/api/course/courseId", ctx -> {
            //get accountId from path parameter
            String courseId = ctx.query("id").value();
            //retreieve courselist from DAO
            Collection<CourseList> courseList = dao.getCourseListsByCourse(courseId);
           
            if (courseList == null) {
                ctx.send(StatusCode.NOT_FOUND);
                return "Course not found";
            }
            //returns courseList
            return courseList;
        });

        //TODO: check for already existing account/course combo
        //POST endpoint to create a new courseList (enrol in paper)
        post("/api/course", ctx -> {
            //retrieve data from request body
            CourseList newCourseList = ctx.body().to(CourseList.class);
            //create courseList using dao
            CourseList createdCourseList = dao.createCourseList(newCourseList.getCourseID(), newCourseList.getAccountID());
            //return as JSON
            return createdCourseList;
        });

        //DELETE endpoint to remove a courseList by Id
        delete("/api/course/delete", ctx -> {
            //retrieve data from request body
            String courseListId = ctx.query("id").value();
            //remove courseList from dao
            dao.deleteCourseListById(courseListId);
            //return as JSON
            return "CourseList deleted successfully";
        });

        
    }
}
