package web;

import dao.CoursesJdbiDAO;
import domain.Course;
import io.jooby.Jooby;
import io.jooby.StatusCode;
import java.util.Collection;

/**
 *
 * author [Your Name]
 */
public class CourseModule extends Jooby {

    public CourseModule(CoursesJdbiDAO dao) {
        
        // GET endpoint to retrieve all courses
        get("/api/courses", ctx -> {
            // Retrieve all courses from the DAO
            Collection<Course> courses = dao.getCourses();
            // Return the courses as JSON
            return courses;
        });

        // GET endpoint to retrieve a course by ID
        get("/api/courses/searchByCourseId", ctx -> {
            // Retrieve the course ID from the path parameter
            String courseId = ctx.query("id").value();
            // Retrieve the course from the DAO
            Course course = dao.getCourseById(courseId);
            // If course is null, return 404 Not Found
            if (course == null) {
                ctx.send(StatusCode.NOT_FOUND);
                return "Course not found";
            }
            // Otherwise, return the course as JSON
            return course;
        });

        // POST endpoint to create a new course
        post("/api/courses", ctx -> {
            // Retrieve course data from the request body
            Course newCourse = ctx.body().to(Course.class);
            // Create the course using the DAO
            Course createdCourse = dao.createCourse(newCourse.getCourseName(), newCourse.getCourseCode(), newCourse.getCourseDescription());
            // Return the created course as JSON
            return createdCourse;
        });

        // PUT endpoint to update an existing course
        put("/api/courses", ctx -> {
            // Retrieve course data from the request body
            Course updatedCourse = ctx.body().to(Course.class);
            if (dao.getCourseById(updatedCourse.getCourseId()) == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "Course with id '" + updatedCourse.getCourseId() + "' does not exist";
            }
            // Update the course using the DAO
            dao.updateCourse(updatedCourse);
            // Return success message
            return "Course updated successfully";
        });

        // TODO: must check if deleting course will conflict with any foreign keys
        // DELETE endpoint to delete a course by ID
        delete("/api/courses/delete", ctx -> {
            // Retrieve the course ID from the path parameter
            String courseId = ctx.query("id").value();
            // Delete the course using the DAO
            dao.deleteCourseById(courseId);
            // Return success message
            return "Course deleted successfully";
        });
        
    }
}
