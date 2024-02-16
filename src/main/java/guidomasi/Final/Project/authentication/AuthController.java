package guidomasi.Final.Project.authentication;

import guidomasi.Final.Project.config.EmailSender;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.payloads.patient.PatientsResponseDTO;
import guidomasi.Final.Project.payloads.physiotherapist.NewPhysiotherapistDTO;
import guidomasi.Final.Project.payloads.physiotherapist.PhysiotherapistResponseDTO;
import guidomasi.Final.Project.payloads.user.UserLoginDTO;
import guidomasi.Final.Project.payloads.user.UserLoginResponseDTO;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @Autowired
    EmailSender emailSender;

    @PostMapping("/login/physiotherapist")
    public UserLoginResponseDTO login(@RequestBody UserLoginDTO body) {
        String token = authService.authenticatePhysiotherapist(body);
        return new UserLoginResponseDTO(token);
    }
    @PostMapping("/login/patient")
    public UserLoginResponseDTO loginPatient(@RequestBody UserLoginDTO body) {
        String token = authService.authenticatePatient(body);
        return new UserLoginResponseDTO(token);
    }


    @PostMapping("/register/physiotherapist")
    @ResponseStatus(HttpStatus.CREATED)
    public PhysiotherapistResponseDTO createPhysiotherapist(@RequestBody @Validated NewPhysiotherapistDTO newPhysiotherapistPayload, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            Physiotherapist newPhysiotherapist = authService.savePhysiotherapist(newPhysiotherapistPayload);
            emailSender.sendRegistrationEmailToPhysio(newPhysiotherapistPayload.email());
            return new PhysiotherapistResponseDTO(newPhysiotherapist.getId());
        }
    }
    @PostMapping("/register/patient")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientsResponseDTO createPatient(@RequestBody @Validated NewPatientDTO newPatientPayload, BindingResult validation) {
        System.out.println(validation);
        if (validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Ci sono errori nel payload!");
        } else {
            Patient newPatient = authService.savePatient(newPatientPayload);

            return new PatientsResponseDTO(newPatient.getId());
        }
    }

}
