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

        get("/api/assignments/searchByAssignmentName", ctx -> {
            String assignmentName = ctx.query("AssignmentName").value();
            return assignmentsDAO.searchByAssignmentName(assignmentName);
        });

        get("/api/assignments/searchByAssignmentID", ctx -> {
            int assignmentID = ctx.query("AssignmentID").intValue();
            return assignmentsDAO.getByAssignmentID(assignmentID);
        });

        post("/api/assignments/CreateAssignment", ctx -> {
            Assignment assignment = ctx.body().to(Assignment.class);
            assignmentsDAO.createAssignment(assignment);
            return ctx.send(StatusCode.OK);
        });

        put("/api/assignments/UpdateAssignmentDetails", ctx -> {
            Assignment assignment = ctx.body().to(Assignment.class);
            if (assignmentsDAO.getByAssignmentID(assignment.getAssignmentID()) == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "Event with ID '" + assignment.getAssignmentID() + "' does not exist";
            }
            assignmentsDAO.updateAssignmentDetails(assignment);
        
            return "Event updated successfully";
        });

        delete("/api/assignments/DeleteAssignmentByID", ctx -> {
            int assignmentID = ctx.query("AssignmentID").intValue();
            assignmentsDAO.deleteAssignmentByAssignmentID(assignmentID);
            return ctx.send(StatusCode.OK);
        });
    }
    
}
