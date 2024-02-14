package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.enums.Specialization;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysiotherapistsDAO extends JpaRepository<Physiotherapist, UUID> {
    Optional<Physiotherapist> findByEmail(String email);

    Page<Physiotherapist> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName, Pageable pageable);

    Page<Physiotherapist> findBySpecialization(Specialization specialization, Pageable pageable);

}
