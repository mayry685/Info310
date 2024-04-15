package web;

import dao.AssignmentsJdbiDAO;
import domain.Assignment;
import io.jooby.Jooby;
import io.jooby.StatusCode;

public class AssignmentModule extends Jooby {

    public AssignmentModule(AssignmentsJdbiDAO assignmentsDAO) {

        get("/api/assignments", ctx -> {
            return assignmentsDAO.getAssignments();
        });

        get("/api/assignments/:AssignmentName", ctx -> {
            String assignmentName = ctx.path("AssignmentName").value();
            return assignmentsDAO.searchByUsername(assignmentName);
        });

        post("/api/CreateAssignment", ctx -> {
            Assignment assignment = ctx.body().to(Assignment.class);
            assignmentsDAO.createAssignment(assignment);
            return ctx.send(StatusCode.OK);
        });

        put("/api/UpdateAssignmentDetails", ctx -> {
            Assignment assignment = ctx.body().to(Assignment.class);
            assignmentsDAO.updateAssignmentDetails(assignment);
            return ctx.send(StatusCode.OK);
        });

        delete("/api/DeleteAssignment/:AssignmentID", ctx -> {
            int assignmentID = ctx.path("AssignmentID").intValue();
            assignmentsDAO.deleteAssignmentByAssignmentID(assignmentID);
            return ctx.send(StatusCode.OK);
        });
    }
    
}
