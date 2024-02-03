package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientsDAO extends JpaRepository<Patient, UUID> {
    Optional<Patient> findByEmail(String email);
}
