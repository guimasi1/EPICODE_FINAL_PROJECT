package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.LinkRequest;
import guidomasi.Final.Project.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LinkRequestsDAO extends JpaRepository<LinkRequest, UUID> {
}
