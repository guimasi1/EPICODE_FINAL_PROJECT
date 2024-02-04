package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.entities.LinkRequest;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.DifficultyLevel;
import guidomasi.Final.Project.enums.Gender;
import guidomasi.Final.Project.enums.RequestStatus;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.payloads.exercise.NewExerciseDTO;
import guidomasi.Final.Project.payloads.linkRequest.LinkRequestDTO;
import guidomasi.Final.Project.payloads.linkRequest.LinkRequestPutDTO;
import guidomasi.Final.Project.payloads.patient.NewPatientDTO;
import guidomasi.Final.Project.repositories.LinkRequestsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LinkRequestsService {

    @Autowired
    LinkRequestsDAO linkRequestsDAO;
    @Autowired
    PhysiotherapistsService physiotherapistsService;
    @Autowired
    PatientsService patientsService;
    public List<LinkRequest> findAll() {
        return linkRequestsDAO.findAll();
    }


    public Page<LinkRequest> getLinkRequests(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return linkRequestsDAO.findAll(pageable);
    }


    public LinkRequest findById(UUID id) {
        return linkRequestsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
    public LinkRequest save(LinkRequestDTO body) {
        LinkRequest request = new LinkRequest();
        Physiotherapist physiotherapist = physiotherapistsService.findById(body.physiotherapist_id());
        Patient patient = patientsService.findById(body.patient_id());
        request.setPhysiotherapist(physiotherapist);
        request.setPatient(patient);
        request.setRequestStatus(RequestStatus.PENDING);
        request.setRequestDateTime(LocalDateTime.now());
        return linkRequestsDAO.save(request);
    }

    public LinkRequest findByIdAndUpdate(UUID uuid, LinkRequestPutDTO body) {
        LinkRequest found = this.findById(uuid);
        Physiotherapist physiotherapist = physiotherapistsService.findById(body.physiotherapist_id());
        Patient patient = patientsService.findById(body.patient_id());
        found.setPhysiotherapist(physiotherapist);
        found.setPatient(patient);
         RequestStatus requestStatus = RequestStatus.valueOf(body.requestStatus().toUpperCase());
        found.setRequestStatus(requestStatus);

        return linkRequestsDAO.save(found);
    }

    public void deleteById(UUID uuid) {
        LinkRequest found = this.findById(uuid);
        linkRequestsDAO.delete(found);
    }

    public LinkRequest findByIdAndAccept(UUID uuid) {
        LinkRequest found = this.findById(uuid);
        found.setRequestStatus(RequestStatus.ACCEPTED);
       return linkRequestsDAO.save(found);
    }
    public LinkRequest findByIdAndReject(UUID uuid) {
        LinkRequest found = this.findById(uuid);
        found.setRequestStatus(RequestStatus.REJECTED);
        return linkRequestsDAO.save(found);
    }
}
