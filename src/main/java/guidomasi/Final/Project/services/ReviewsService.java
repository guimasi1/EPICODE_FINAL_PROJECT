package guidomasi.Final.Project.services;

import guidomasi.Final.Project.entities.Patient;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.entities.Review;
import guidomasi.Final.Project.exceptions.NotFoundException;
import guidomasi.Final.Project.payloads.review.NewReviewDTO;
import guidomasi.Final.Project.repositories.ReviewsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ReviewsService {

    @Autowired
    ReviewsDAO reviewsDAO;

    @Autowired
    PhysiotherapistsService physiotherapistsService;

    @Autowired
    PatientsService patientsService;

    public Review saveReview(NewReviewDTO body) {
        Review review = new Review();
        Physiotherapist physiotherapist= physiotherapistsService.findById(body.physiotherapist_id());
        Patient patient = patientsService.findById(body.patient_id());
        review.setContent(body.content());
        review.setRating(body.rating());
        review.setPhysiotherapist(physiotherapist);
        review.setPatient(patient);
        return reviewsDAO.save(review);
    }
    public Page<Review> getReviews(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return reviewsDAO.findAll(pageable);
    }

    public Page<Review> getReviewsByPhysiotherapistAndPatient(UUID physiotherapist_id, UUID patient_id, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        Patient patient = patientsService.findById(patient_id);
        Physiotherapist physiotherapist = physiotherapistsService.findById(physiotherapist_id);
        return reviewsDAO.findByPhysiotherapistAndPatient(physiotherapist,patient,pageable);
    }
    public Page<Review> getReviewsByPhysiotherapist(UUID physiotherapist_id, int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        Physiotherapist physiotherapist = physiotherapistsService.findById(physiotherapist_id);
        return reviewsDAO.findByPhysiotherapist(physiotherapist,pageable);
    }

    public Review findById(UUID id) {
        return reviewsDAO.findById(id).orElseThrow(() -> new NotFoundException(id));

    }

    public void deleteById(UUID uuid) {
        Review found = this.findById(uuid);
        reviewsDAO.delete(found);
    }

}
