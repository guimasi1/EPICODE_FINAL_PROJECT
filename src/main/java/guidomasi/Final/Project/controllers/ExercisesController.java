package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.entities.ExerciseDetails;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.exercise.ExerciseResponseDTO;
import guidomasi.Final.Project.payloads.exercise.ExercisesPutDTO;
import guidomasi.Final.Project.payloads.exercise.NewExerciseDTO;
import guidomasi.Final.Project.payloads.exerciseDetails.ExerciseDetailsDTO;
import guidomasi.Final.Project.services.ExercisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return exercisesService.getExercises(page,size,orderBy);
    }
    @GetMapping("/byName")
    public Page<Exercise> getExercisesByName(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return exercisesService.getExercisesByName(name,page,size,orderBy);
    }
    @PutMapping("/{id}")
    public Exercise updateById(@PathVariable UUID id, @RequestBody ExercisesPutDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            return exercisesService.findByIdAndUpdate(id, body);
        }    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        exercisesService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Exercise getExerciseById(@PathVariable UUID id) {
        return exercisesService.findById(id);
    }

}
