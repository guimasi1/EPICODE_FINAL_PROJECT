package guidomasi.Final.Project.payloads.exercisesAssignment;

import java.util.UUID;

public record ExercisesAssignmentPutDTO (String notes, UUID patient_id, UUID physiotherapist_id, String assignmentStatus){
}
