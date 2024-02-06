package guidomasi.Final.Project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import guidomasi.Final.Project.enums.RequestStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "link_requests")
public class LinkRequest {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "physiotherapist_id")
    private Physiotherapist physiotherapist;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @Enumerated(EnumType.STRING)
    private RequestStatus requestStatus;
    private LocalDateTime requestDateTime;
}
