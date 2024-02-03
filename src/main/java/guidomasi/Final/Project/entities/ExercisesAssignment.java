package guidomasi.Final.Project.entities;

import guidomasi.Final.Project.enums.AssignmentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.sql.ast.tree.update.Assignment;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "assignments")
public class ExercisesAssignment {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private LocalDate assignmentDate;
    private AssignmentStatus assignmentStatus;
    private String notes;
    @Enumerated(EnumType.STRING)
    @OneToMany(mappedBy = "exercisesAssignment")
    private List<ExerciseDetails> exerciseDetails;
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;
    @ManyToOne
    @JoinColumn(name = ("physiotherapist_id"))
    private Physiotherapist assignedBy;
}
