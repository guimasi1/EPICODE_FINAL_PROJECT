package guidomasi.Final.Project.controllers;


import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.services.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    @PutMapping("/{uuid}")
    public Patient updateById(@PathVariable UUID uuid, @RequestBody NewPatientDTO body) {
        return patientsService.findByIdAndUpdate(uuid, body);
    }

    @DeleteMapping("/{uuid}")
    public void deleteById(@PathVariable UUID uuid) {
        patientsService.deleteById(uuid);
    }

    @GetMapping("/{id}")
    public Patient getPatientById(@PathVariable UUID id) {
        return patientsService.findById(id);
    }

}