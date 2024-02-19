package guidomasi.Final.Project.controllers;

import guidomasi.Final.Project.entities.ExercisesAssignment;
import guidomasi.Final.Project.entities.Physiotherapist;
import guidomasi.Final.Project.entities.Review;
import guidomasi.Final.Project.exceptions.BadRequestException;
import guidomasi.Final.Project.payloads.exercisesAssignment.ExercisesAssignmentDTO;
import guidomasi.Final.Project.payloads.review.NewReviewDTO;
import guidomasi.Final.Project.payloads.review.ReviewResponseDTO;
import guidomasi.Final.Project.services.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
public class ReviewsController {
    @Autowired
    ReviewsService reviewsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Review create(@RequestBody @Validated NewReviewDTO review, BindingResult validation) {
        if(validation.hasErrors()) {
            System.out.println(validation.getAllErrors());
            throw new BadRequestException("Something is wrong in the payload.");
        } else {
            return reviewsService.saveReview(review);
        }
    }

    @GetMapping
    public Page<Review> getReviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return reviewsService.getReviews(page,size,id);
    }
    @GetMapping("byPhysio/{physiotherapist_id}/AndPatient/{patient_id}")
    public Page<Review> getReviewsByPhysioAndPatient(
            @PathVariable UUID physiotherapist_id,
            @PathVariable UUID patient_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return reviewsService.getReviews(page,size,id);
    }
    @GetMapping("byPhysio/{physiotherapist_id}")
    public Page<Review> getReviewsByPhysio(
            @PathVariable UUID physiotherapist_id,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String id) {
        return reviewsService.getReviewsByPhysiotherapist(physiotherapist_id,page,size,id);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable UUID id) {
        reviewsService.deleteById(id);
    }
}
