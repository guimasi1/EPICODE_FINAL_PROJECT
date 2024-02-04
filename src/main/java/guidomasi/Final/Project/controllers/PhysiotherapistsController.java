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

    @GetMapping("/{id}")
    public Physiotherapist getPhysiotherapistById(@PathVariable UUID id) {
        return physiotherapistsService.findById(id);
    }

    @GetMapping("/me")
    public Physiotherapist getMyProfile(@AuthenticationPrincipal Physiotherapist myUser) {
        return myUser;
    }

}
