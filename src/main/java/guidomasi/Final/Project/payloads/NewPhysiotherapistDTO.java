package guidomasi.Final.Project.payloads;

import java.time.LocalDate;

public record NewPhysiotherapistDTO(String email, String password, String firstName, String lastName, String phoneNumber, LocalDate dateOfBirth, String specialization) {
}
