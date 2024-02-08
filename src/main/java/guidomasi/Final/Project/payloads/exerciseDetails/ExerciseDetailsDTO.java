package guidomasi.Final.Project.payloads.exerciseDetails;

import java.util.UUID;

public record ExerciseDetailsDTO (int sets, int reps, UUID exercise_id){
}
