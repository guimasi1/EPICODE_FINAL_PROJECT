package guidomasi.Final.Project.controllers;


import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.services.PatientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @GetMapping("/byLastName")
    public Page<Patient> getPatientsByLastName(
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return patientsService.getPatientsByLastName(lastName,page,size,id);
    }

    @GetMapping("/byPhysiotherapist/{id}")
    public Page<Patient> getPatientsByPhysiotherapist(
            @PathVariable UUID id,
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
 /*       System.out.println(id);
        System.out.println(lastName);*/
        return patientsService.getPatientsByPhysiotherapist(id,lastName,page,size,orderBy);
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

    @GetMapping("/me")
    public Patient getMyProfile(@AuthenticationPrincipal Patient myUser) {
        return myUser;
    }

    @PostMapping("/{id}/profilePicture")
    public Patient uploadExample(@PathVariable UUID id, @RequestParam("picture") MultipartFile body) throws IOException {
        return patientsService.uploadPicture(id, body);
    }

}