package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AdminsDAO extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByEmail(String email);
}
