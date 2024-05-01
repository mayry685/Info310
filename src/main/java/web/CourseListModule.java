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

        get("/api/courseList", ctx -> {

            Collection<CourseList> courseLists = dao.getCourseLists();
            if (courseLists == null) {
                ctx.send(StatusCode.NOT_FOUND);
                return "CourseList not found";
            }
            return courseLists;
        });

        //TODO: check for already existing account/course combo
        //POST endpoint to create a new courseList (enrol in paper)
        post("/api/courseList", ctx -> {
            //retrieve data from request body
            CourseList newCourseList = ctx.body().to(CourseList.class);
            //create courseList using dao
            CourseList createdCourseList = dao.createCourseList(newCourseList.getCourseID(), newCourseList.getAccountID());
            //return as JSON
            return createdCourseList;
        });

        //GET endpoint to retrieve a list of courseLists by accountID
        get("/api/courseList/{accountId}", ctx -> {
            //get accountId from path parameter
            String accountId = ctx.path("accountId").toString();
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
        get("/api/courseList/courseId", ctx -> {
            //get accountId from path parameter
            String courseId = ctx.query("courseId").value();
            //retreieve courselist from DAO
            Collection<CourseList> courseList = dao.getCourseListsByCourse(courseId);
           
            if (courseList == null) {
                ctx.send(StatusCode.NOT_FOUND);
                return "Course not found";
            }
            //returns courseList
            return courseList;
        });

        //DELETE endpoint to remove a courseList by Id
        delete("/api/courseList/delete/{courseListId}", ctx -> {
            //retrieve data from request body
            int courseListId = Integer.parseInt(ctx.path("courseListId").toString());
            System.out.println(courseListId);
            //remove courseList from dao
            dao.deleteCourseListById(courseListId);
            //return as JSON
            return "CourseList deleted successfully";
        });

        
    }
}
