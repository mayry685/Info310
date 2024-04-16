package web;

import dao.CoursesJdbiDAO;
import domain.Course;
import io.jooby.Jooby;
import io.jooby.StatusCode;

/**
 *
 * author [Your Name]
 */
public class CourseModule extends Jooby {

    public CourseModule(CoursesJdbiDAO dao) {
        
        get("/api/courses", ctx -> {
            System.out.println(dao.getCourses());
            return dao.getCourses();
        });
        
    }
}
