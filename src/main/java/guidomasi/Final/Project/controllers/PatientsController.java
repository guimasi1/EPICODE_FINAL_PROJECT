package guidomasi.Final.Project.controllers;


import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.services.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients")
public class PatientsController {
    @Autowired
    PatientsService patientsService;

    @GetMapping
    public Page<Patient> getPatients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return patientsService.getPatients(page,size,id);
    }

    @PutMapping("/{id}")
    public Patient updateById(@PathVariable UUID id, @RequestBody NewPatientDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            return patientsService.findByIdAndUpdate(id, body);
        }    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        patientsService.deleteById(id);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable UUID id) {
        return patientsService.findById(id);
    }

}