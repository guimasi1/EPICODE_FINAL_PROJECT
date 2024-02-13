package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.LinkRequest;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.RequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientsDAO extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByEmail(String email);
    @Query("SELECT p FROM Patient p JOIN p.physiotherapists pt WHERE pt.id = :physiotherapistId AND " +
/*            "(:lastName IS NULL OR LOWER(p.lastName) = LOWER(:lastName))")*/
            "(:lastName IS NULL OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :lastName, '%')))")
    Page<Patient> findAllByPhysiotherapistId(@Param("physiotherapistId") UUID physiotherapistId, @Param("lastName") String lastName, Pageable pageable);

    Page<Patient> findByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
}
