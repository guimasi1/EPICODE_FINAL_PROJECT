package guidomasi.Final.Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String content;
    private int rating;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "physiotherapist_id")
    @JsonIgnore
    private Physiotherapist physiotherapist;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
}
