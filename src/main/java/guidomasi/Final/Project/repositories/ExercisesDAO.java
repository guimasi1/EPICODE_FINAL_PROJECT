package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.Exercise;
import guidomasi.Final.Project.enums.DifficultyLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExercisesDAO extends JpaRepository<Exercise, UUID> {
    Optional<Exercise> findByName(String name);
    Page<Exercise> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT e FROM Exercise e WHERE (LOWER(e.name) LIKE LOWER(CONCAT( '%', :name, '%' ))) AND " +
            "(:targetArea IS NULL OR LOWER(e.targetArea) LIKE LOWER(CONCAT( '%', :targetArea, '%' ))) AND " +
            "(:difficulty IS NULL OR e.difficultyLevel = :difficulty)")
    Page<Exercise> findExercisesByParams(@Param("name") String name,
                                         @Param("targetArea") String targetArea,
                                         @Param("difficulty") DifficultyLevel difficultyLevel,
                                         Pageable pageable);

    Long countByTargetArea(String targetArea);
    Long countByDifficultyLevel(DifficultyLevel difficultyLevel);
}
