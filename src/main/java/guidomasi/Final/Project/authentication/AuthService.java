package guidomasi.Final.Project.authentication;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.Gender;
import guidomasi.Final.Project.enums.Role;
import guidomasi.Final.Project.enums.Specialization;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.exceptions.UnauthorizedException;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.payloads.physiotherapist.NewPhysiotherapistDTO;
import guidomasi.Final.Project.payloads.user.UserLoginDTO;
import guidomasi.Final.Project.repositories.PatientsDAO;
import guidomasi.Final.Project.repositories.PhysiotherapistsDAO;
import guidomasi.Final.Project.security.JWTTools;
import guidomasi.Final.Project.services.PatientsService;
import guidomasi.Final.Project.services.PhysiotherapistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AuthService {
    @Autowired
    private PhysiotherapistsService physiotherapistsService;

    @Autowired
    private PatientsService patientsService;
    @Autowired
    private PhysiotherapistsDAO physiotherapistsDAO;

    @Autowired
    private PatientsDAO patientsDAO;
    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bCrypt;



    public String authenticatePhysiotherapist(UserLoginDTO body) {
        Physiotherapist physiotherapist = physiotherapistsService.findByEmail(body.email());
        if(bCrypt.matches(body.password(),physiotherapist.getPassword())) {
            return jwtTools.createPhysiotherapistToken(physiotherapist);
        } else {
            throw new UnauthorizedException("Errore nelle credenziali");
        }
    }
 public String authenticatePatient(UserLoginDTO body) {
        Patient patient = patientsService.findByEmail(body.email());
        if(bCrypt.matches(body.password(),patient.getPassword())) {
            return jwtTools.createPatientToken(patient);
        } else {
            throw new UnauthorizedException("Errore nelle credenziali");
        }
    }

    public Physiotherapist savePhysiotherapist(NewPhysiotherapistDTO body) {
        physiotherapistsDAO.findByEmail(body.email()).ifPresent(physiotherapist -> {
            throw new BadRequestException("Email " + body.email() + " già in uso");
        });

        Physiotherapist newPhysiotherapist = new Physiotherapist();
        newPhysiotherapist.setEmail(body.email());
        newPhysiotherapist.setRole(Role.PHYSIOTHERAPIST);
        newPhysiotherapist.setPassword(bCrypt.encode(body.password()));
        newPhysiotherapist.setFirstName(body.firstName());
        newPhysiotherapist.setLastName(body.lastName());
        newPhysiotherapist.setPhoneNumber(body.phoneNumber());
        newPhysiotherapist.setDateOfBirth(body.dateOfBirth());
        Specialization specialization = Specialization.valueOf(body.specialization().toUpperCase());
        newPhysiotherapist.setSpecialization(specialization);
        newPhysiotherapist.setRegistrationDate(LocalDate.now());
        return physiotherapistsDAO.save(newPhysiotherapist);
    }
    public Patient savePatient(NewPatientDTO body) {
        Patient patient = new Patient();
        patient.setEmail(body.email());
        patient.setRole(Role.PATIENT);
        patient.setPassword(bCrypt.encode(body.password()));
        patient.setFirstName(body.firstName());
        patient.setLastName(body.lastName());
        patient.setPhoneNumber(body.phoneNumber());
        patient.setDateOfBirth(body.dateOfBirth());
        Gender gender = Gender.valueOf(body.gender().toUpperCase());
        patient.setGender(gender);
        patient.setRegistrationDate(LocalDate.now());
        return patientsDAO.save(patient);
    }

}
