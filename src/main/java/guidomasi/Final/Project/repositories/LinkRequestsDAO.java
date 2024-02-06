package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.LinkRequest;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.enums.RequestStatus;
import io.swagger.v3.oas.models.links.Link;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRequestsDAO extends JpaRepository<LinkRequest, UUID> {
    Page<LinkRequest> findByPatient(Patient patient, Pageable pageable);
    Page<LinkRequest> findByRequestStatusAndPatient(RequestStatus requestStatus, Patient patient, Pageable pageable);

}
