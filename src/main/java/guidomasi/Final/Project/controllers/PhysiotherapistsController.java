package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.payloads.physiotherapist.NewPhysiotherapistDTO;
import guidomasi.Final.Project.services.PhysiotherapistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/physiotherapists")
public class PhysiotherapistsController {
    @Autowired
    PhysiotherapistsService physiotherapistsService;

    @GetMapping
    public Page<Physiotherapist> getPhysiotherapists(

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return physiotherapistsService.getPhysiotherapists(page, size, id);
    }
    @GetMapping("/byName")
    public Page<Physiotherapist> getByParams(
            @RequestParam(required = false) String firstName,
            @RequestParam(required = false) String lastName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return physiotherapistsService.getPhysiosByParams(firstName,lastName,page, size, orderBy);
    }
    @GetMapping("/bySpecialization")
    public Page<Physiotherapist> getBySpecialization(
            @RequestParam(required = false) String specialization,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return physiotherapistsService.findBySpecialization(specialization,page, size, orderBy);
    }
    @GetMapping("/byPatient/{patientId}")
    public Page<Physiotherapist> getByPhysioId(
            @PathVariable UUID patientId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String orderBy) {
        return physiotherapistsService.getPhysiosByPatientId(patientId,page, size, orderBy);
    }

    @PutMapping("/{id}")
    public Physiotherapist updateById(@PathVariable UUID id, @RequestBody NewPhysiotherapistDTO body, BindingResult validation) {
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            return physiotherapistsService.findByIdAndUpdate(id, body);
        }    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        physiotherapistsService.deleteById(id);
    }

    @PostMapping("/{physiotherapist_id}/removePatient/{patient_id}")
    public void removePatientFromPhysio(@PathVariable UUID physiotherapist_id, @PathVariable UUID patient_id) {
        physiotherapistsService.removePatientFromPhysio(physiotherapist_id,patient_id);
    }

    @GetMapping("/{id}")
    public Physiotherapist getPhysiotherapistById(@PathVariable UUID id) {
        return physiotherapistsService.findById(id);
    }

    @GetMapping("/me")
    public Physiotherapist getMyProfile(@AuthenticationPrincipal Physiotherapist myUser) {
        return myUser;
    }

    @PostMapping("/{id}/profilePicture")
    public Physiotherapist uploadExample(@PathVariable UUID id, @RequestParam("picture") MultipartFile body) throws IOException {
        return physiotherapistsService.uploadPicture(id, body);
    }


}
