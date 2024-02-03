package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.Physiotherapist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PhysiotherapistsDAO extends JpaRepository<Physiotherapist, UUID> {
    Optional<Physiotherapist> findByEmail(String email);
}
