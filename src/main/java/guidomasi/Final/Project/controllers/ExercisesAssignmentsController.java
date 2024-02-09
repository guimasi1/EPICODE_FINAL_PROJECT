package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.ExercisesAssignment;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.exercise.ExerciseResponseDTO;
import guidomasi.Final.Project.payloads.exercisesAssignment.ExercisesAssignmentDTO;
import guidomasi.Final.Project.payloads.exercisesAssignment.ExercisesAssignmentPutDTO;
import guidomasi.Final.Project.services.ExercisesAssignmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/exercisesAssignments")
public class ExercisesAssignmentsController {
    @Autowired
    ExercisesAssignmentsService exercisesAssignmentsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExercisesAssignment create(@RequestBody @Validated ExercisesAssignmentDTO assignment, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            return exercisesAssignmentsService.save(assignment);
        }
    }

    @GetMapping
    public Page<ExercisesAssignment> getExercisesAssignments(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return exercisesAssignmentsService.getExercisesAssignments(page,size,id);
    }

    @PatchMapping("/complete/{id}")
    public ExercisesAssignment completeAssignment(@PathVariable UUID id) {
        return exercisesAssignmentsService.completeExercises(id);
    }
    @PatchMapping("/cancel/{id}")
    public ExercisesAssignment cancelAssignment(@PathVariable UUID id) {
        return exercisesAssignmentsService.cancelExercises(id);
    }
    @PatchMapping("/inProgress/{id}")
    public ExercisesAssignment setInProgress(@PathVariable UUID id) {
        return exercisesAssignmentsService.setInProgress(id);
    }

    @PutMapping("/{id}")
    public ExercisesAssignment updateById(@PathVariable UUID id, @RequestBody ExercisesAssignmentPutDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            return exercisesAssignmentsService.findByIdAndUpdate(id, body);
        }    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        exercisesAssignmentsService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ExercisesAssignment getExercisesAssignmentById(@PathVariable UUID id) {
        return exercisesAssignmentsService.findById(id);
    }

  /*  @PostMapping("/addExercise/{id}/{exerciseDetails_id}")
    public ExercisesAssignment addExercise(@PathVariable UUID id, @PathVariable UUID exerciseDetails_id ){
        return exercisesAssignmentsService.addExercise(id ,exerciseDetails_id);
    }
*/

    @GetMapping("/getByPatient/{patientId}/andPhysio/{physioId}")
    public Page<ExercisesAssignment> getExercisesAssignmentsByPatientAndPhysio(
            @PathVariable UUID patientId,
            @PathVariable UUID physioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {

        return exercisesAssignmentsService.getExercisesAssignmentsByPhysioAndPatient(patientId,physioId,page,size,id);
    }
}
