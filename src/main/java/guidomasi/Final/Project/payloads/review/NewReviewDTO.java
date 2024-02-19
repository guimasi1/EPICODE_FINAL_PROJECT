package guidomasi.Final.Project.payloads.review;

import java.util.UUID;

public record NewReviewDTO(String content,int rating, UUID physiotherapist_id, UUID patient_id) {
}
