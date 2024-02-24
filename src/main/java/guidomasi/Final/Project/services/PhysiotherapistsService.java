package guidomasi.Final.Project.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.Gender;
import guidomasi.Final.Project.enums.Specialization;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.payloads.physiotherapist.NewPhysiotherapistDTO;
import guidomasi.Final.Project.repositories.PhysiotherapistsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PhysiotherapistsService {
    @Autowired
    PhysiotherapistsDAO physiotherapistsDAO;

    @Autowired
    Cloudinary cloudinary;

    public Page<Physiotherapist> getPhysiotherapists(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return physiotherapistsDAO.findAll(pageable);
    }

    public Physiotherapist findByEmail(String email) throws NotFoundException {
        return physiotherapistsDAO.findByEmail(email).orElseThrow(() -> new NotFoundException("Physiotherapist with email " + email + " not found!"));
    }
    public Physiotherapist findById(UUID id) throws NotFoundException {
        return physiotherapistsDAO.findById(id).orElseThrow(() -> new NotFoundException("Physiotherapist with id " + id + " not found!"));
    }

    public void deleteById(UUID uuid) {
        Physiotherapist found = this.findById(uuid);
        physiotherapistsDAO.delete(found);
    }

    public void removePatientFromPhysio(UUID physio_Id,UUID patient_id) {
        Physiotherapist physiotherapist = this.findById(physio_Id);
        List<Patient> patients = physiotherapist.getPatients();
        List<Patient> updatedPatients = patients.stream().filter(patient -> !patient.getId().equals(patient_id))
                .collect(Collectors.toList());
        physiotherapist.setPatients(updatedPatients);
        physiotherapistsDAO.save(physiotherapist);
    }

    public Physiotherapist findByIdAndUpdate(UUID uuid, NewPhysiotherapistDTO body) {
        Physiotherapist found = this.findById(uuid);
        found.setEmail(body.email());
        found.setPassword(body.password());
        found.setFirstName(body.firstName());
        found.setLastName(body.lastName());
        found.setPhoneNumber(body.phoneNumber());
        found.setDateOfBirth(body.dateOfBirth());
        found.setBio(body.bio());
        Specialization specialization = Specialization.valueOf(body.specialization().toUpperCase());
        found.setSpecialization(specialization);
        return physiotherapistsDAO.save(found);
    }

    public Page<Physiotherapist> findPhysiosByParams(String lastName, Specialization specialization, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return physiotherapistsDAO.findPhysiosByParams(lastName, specialization, pageable);
    }

    public Page<Physiotherapist> getPhysiosByPatientId(UUID patient_id, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return physiotherapistsDAO.findPhysiosByPatientId(patient_id,pageable);
    }


    public Page<Physiotherapist> findBySpecialization(String specializationStr,int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        Specialization specialization = null;
        if (specializationStr != null && !specializationStr.isEmpty()) {
            specialization = Specialization.valueOf(specializationStr.toUpperCase());
        }
        return physiotherapistsDAO.findBySpecialization(specialization, pageable);
    }
    public String uploadPicture(UUID id, MultipartFile file) throws IOException {
        Physiotherapist physiotherapist = physiotherapistsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
        String url = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
        physiotherapist.setProfilePictureUrl(url);
        physiotherapistsDAO.save(physiotherapist);
        return physiotherapist.getProfilePictureUrl();
    }

}
