package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.ExerciseDetails;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.exerciseDetails.ExerciseDetailsDTO;
import guidomasi.Final.Project.payloads.exerciseDetails.ExercisesDetailsResponseDTO;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.services.ExercisesDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/exercisesDetails")
public class ExercisesDetailsController {

    @Autowired
    ExercisesDetailsService exercisesDetailsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExercisesDetailsResponseDTO create(@RequestBody @Validated ExerciseDetailsDTO exerciseDetails, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            ExerciseDetails newExerciseDetails = exercisesDetailsService.save(exerciseDetails);
            return new ExercisesDetailsResponseDTO(newExerciseDetails.getId());
        }
    }

    @GetMapping
    public Page<ExerciseDetails> getExercises(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return exercisesDetailsService.getExercisesDetails(page,size,id);
    }

    @PutMapping("/{id}")
    public ExerciseDetails updateById(@PathVariable UUID id, @RequestBody ExerciseDetailsDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            return exercisesDetailsService.findByIdAndUpdate(id, body);
        }    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        exercisesDetailsService.deleteById(id);
    }

    @GetMapping("/{id}")
    public ExerciseDetails getExerciseDetailsById(@PathVariable UUID id) {
        return exercisesDetailsService.findById(id);
    }



}
