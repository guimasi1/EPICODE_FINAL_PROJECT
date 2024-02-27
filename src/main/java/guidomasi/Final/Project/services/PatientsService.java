package guidomasi.Final.Project.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.Gender;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.repositories.PatientsDAO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class PatientsService {
    @Autowired
    PatientsDAO patientsDAO;
    @Autowired
    PhysiotherapistsService physiotherapistsService;

    @Autowired
    Cloudinary cloudinary;

    public List<Patient> findAll() {
        return patientsDAO.findAll();
    }


    public Page<Patient> getPatients(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return patientsDAO.findAll(pageable);
    }
    public Page<Patient> getPatientsByLastName(String lastName,int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return patientsDAO.findByLastNameContainingIgnoreCase(lastName,pageable);
    }

    public Page<Patient> getPatientsByPhysiotherapist(UUID id ,String lastName ,int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        if(lastName == null) {
            return patientsDAO.findAllByPhysio(id,pageable);
        }
        return patientsDAO.findAllByPhysiotherapistId(id, lastName, pageable);
    }

    public long getTotalPatientCount() {
        return patientsDAO.count();
    }


    public Patient findById(UUID id) {
        return patientsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public Patient findByIdAndUpdate(UUID uuid, NewPatientDTO body) {
        Patient found = this.findById(uuid);
        found.setEmail(body.email());
        found.setPassword(body.password());
        found.setFirstName(body.firstName());
        found.setLastName(body.lastName());
        found.setPhoneNumber(body.phoneNumber());
        found.setDateOfBirth(body.dateOfBirth());
        Gender gender = Gender.valueOf(body.gender().toUpperCase());
        found.setGender(gender);
        return patientsDAO.save(found);
    }


    public void deleteById(UUID uuid) {
        Patient found = this.findById(uuid);
        patientsDAO.delete(found);
    }

    public Patient findByEmail(String email) throws NotFoundException
    {
        return patientsDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Patient with email " + email + " not found!"));
    }

    public String uploadPicture(UUID id, MultipartFile file) throws IOException {
        Patient patient = patientsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        patient.setProfilePictureUrl(url);
        patientsDAO.save(patient);
        return patient.getProfilePictureUrl();
    }


}


