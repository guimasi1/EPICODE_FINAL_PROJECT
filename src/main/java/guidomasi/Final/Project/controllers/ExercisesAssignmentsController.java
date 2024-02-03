package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.entities.ExercisesAssignment;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.exercise.ExerciseResponseDTO;
import guidomasi.Final.Project.payloads.exercise.NewExerciseDTO;
import guidomasi.Final.Project.payloads.exercisesAssignment.ExercisesAssignmentDTO;
import guidomasi.Final.Project.services.ExercisesAssignmentsService;
import guidomasi.Final.Project.services.ExercisesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercisesAssignments")
public class ExercisesAssignmentsController {
    @Autowired
    ExercisesAssignmentsService exercisesAssignmentsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExerciseResponseDTO create(@RequestBody @Validated ExercisesAssignmentDTO assignment, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            ExercisesAssignment newAssignment = exercisesAssignmentsService.save(assignment);
            return new ExerciseResponseDTO(newAssignment.getId());
        }
    }

    @GetMapping
    public Page<ExercisesAssignment> getExercisesAssignments(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return exercisesAssignmentsService.getExercisesAssignments(page,size,id);
    }
}
