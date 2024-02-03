package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.ExerciseDetails;
import guidomasi.Final.Project.payloads.exerciseDetails.ExerciseDetailsDTO;
import guidomasi.Final.Project.repositories.ExerciseDetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ExercisesDetailsService {
    @Autowired
    ExerciseDetailsDAO exerciseDetailsDAO;
    public Page<ExerciseDetails> getExercisesDetails(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return exerciseDetailsDAO.findAll(pageable);
    }

    public ExerciseDetails save(ExerciseDetailsDTO body) {
        ExerciseDetails exerciseDetails = new ExerciseDetails();
        exerciseDetails.setReps(body.reps());
        exerciseDetails.setSets(body.sets());
        return exerciseDetailsDAO.save(exerciseDetails);
    }

}
