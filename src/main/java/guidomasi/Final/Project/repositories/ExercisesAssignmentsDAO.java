package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.ExercisesAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExercisesAssignmentsDAO extends JpaRepository<ExercisesAssignment, UUID> {
}
