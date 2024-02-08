package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.entities.ExerciseDetails;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.payloads.exerciseDetails.ExerciseDetailsDTO;
import guidomasi.Final.Project.repositories.ExerciseDetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExercisesDetailsService {
    @Autowired
    ExerciseDetailsDAO exerciseDetailsDAO;

    @Autowired
    ExercisesService exercisesService;
    public Page<ExerciseDetails> getExercisesDetails(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return exerciseDetailsDAO.findAll(pageable);
    }

    public ExerciseDetails save(ExerciseDetailsDTO body) {
        ExerciseDetails exerciseDetails = new ExerciseDetails();
        exerciseDetails.setReps(body.reps());
        exerciseDetails.setSets(body.sets());
        Exercise exercise = exercisesService.findById(body.exercise_id());
        exerciseDetails.setExercise(exercise);
        return exerciseDetailsDAO.save(exerciseDetails);
    }

    public ExerciseDetails findById(UUID id) {
        return exerciseDetailsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public ExerciseDetails findByIdAndUpdate(UUID uuid, ExerciseDetailsDTO body) {
        ExerciseDetails found = this.findById(uuid);
        found.setSets(body.sets());
        found.setReps(body.reps());
        return exerciseDetailsDAO.save(found);
    }

    public void deleteById(UUID uuid) {
        ExerciseDetails found = this.findById(uuid);
        exerciseDetailsDAO.delete(found);
    }


}
