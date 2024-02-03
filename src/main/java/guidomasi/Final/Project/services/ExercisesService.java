package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.enums.DifficultyLevel;
import guidomasi.Final.Project.enums.Gender;
import guidomasi.Final.Project.enums.Role;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.payloads.NewExerciseDTO;
import guidomasi.Final.Project.payloads.NewPatientDTO;
import guidomasi.Final.Project.repositories.ExercisesDAO;
import org.apache.commons.lang3.builder.Diff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ExercisesService {
    @Autowired
    ExercisesDAO exercisesDAO;

    public Exercise findByName(String name) {
        return exercisesDAO.findByName(name).orElseThrow(() -> new NotFoundException("Exercise with name " + name + " not found!"));

    }

    public Page<Exercise> getExercises(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return exercisesDAO.findAll(pageable);
    }

    public Exercise saveExercise(NewExerciseDTO body) {
        Exercise exercise = new Exercise();
        exercise.setName(body.name());
        exercise.setDescription(body.description());
        exercise.setTargetArea(body.targetArea());
        DifficultyLevel difficultyLevel = DifficultyLevel.valueOf(body.difficultyLevel().toUpperCase());
        exercise.setDifficultyLevel(difficultyLevel);
        return exercisesDAO.save(exercise);
    }

}
