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
            Assignment assignment = assignmentsDAO.searchByAssignmentName(assignmentName);
            if (assignment != null)
                return assignment;
            ctx.setResponseCode(StatusCode.NOT_FOUND);
            return "Assignment '" + assignmentName + "' does not exist";
        });

        get("/api/assignments/searchByAssignmentID", ctx -> {
            int assignmentID = ctx.query("AssignmentID").intValue();
            Assignment assignment = assignmentsDAO.getByAssignmentID(assignmentID);
            if (assignment != null)
                return assignment;
            ctx.setResponseCode(StatusCode.NOT_FOUND);
            return "Assignment with id '" + assignmentID + "' does not exist";
        });

        post("/api/assignments/CreateAssignment", ctx -> {
            Assignment assignment = ctx.body().to(Assignment.class);
            assignment = assignmentsDAO.createAssignment(assignment);
            ctx.setResponseCode(StatusCode.OK);
            return assignment;
        });

        put("/api/assignments/UpdateAssignmentDetails", ctx -> {
            Assignment assignment = ctx.body().to(Assignment.class);
            if (assignmentsDAO.getByAssignmentID(assignment.getAssignmentID()) == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "Assignment with ID '" + assignment.getAssignmentID() + "' does not exist";
            }
            assignmentsDAO.updateAssignmentDetails(assignment);
            return "Assignment updated successfully";
        });

        delete("/api/assignments/DeleteAssignmentByID", ctx -> {
            int assignmentID = ctx.query("AssignmentID").intValue();
            if (assignmentsDAO.getByAssignmentID(assignmentID) == null) {
                ctx.setResponseCode(StatusCode.NOT_FOUND);
                return "Assignment with ID '" + assignmentID + "' does not exist";
            }
            assignmentsDAO.deleteAssignmentByAssignmentID(assignmentID);
            ctx.setResponseCode(StatusCode.OK);
            return "Assignment deleted successfully";
        });
    }
    
}
