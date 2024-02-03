package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExercisesDAO extends JpaRepository<Exercise, UUID> {
    Page<Exercise> findByName(String name, Pageable pageable);
}
