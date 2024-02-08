package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.ExercisesAssignment;
import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExercisesAssignmentsDAO extends JpaRepository<ExercisesAssignment, UUID> {
    Page<ExercisesAssignment> findByPatientAndAssignedBy(Patient patient, Physiotherapist physiotherapist, Pageable pageable);
}
