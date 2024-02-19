package guidomasi.Final.Project.repositories;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.entities.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReviewsDAO extends JpaRepository<Review, UUID> {

    Page<Review> findByPhysiotherapistAndPatient(Physiotherapist physiotherapist, Patient patient, Pageable pageable);
    Page<Review> findByPhysiotherapist(Physiotherapist physiotherapist, Pageable pageable);
}
