package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.ExerciseDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ExerciseDetailsDAO extends JpaRepository<ExerciseDetails, UUID> {}
