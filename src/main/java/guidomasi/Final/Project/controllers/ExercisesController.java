package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.ExerciseResponseDTO;
import guidomasi.Final.Project.payloads.NewExerciseDTO;
import guidomasi.Final.Project.services.ExercisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercises")
public class ExercisesController {

    @Autowired
    ExercisesService exercisesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseResponseDTO create(@RequestBody @Validated NewExerciseDTO exercise, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            Exercise newExercise = exercisesService.saveExercise(exercise);
            return new ExerciseResponseDTO(newExercise.getId());
        }
    }

    @GetMapping
    public Page<Exercise> getExercises(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return exercisesService.getExercises(page,size,id);
    }
}
