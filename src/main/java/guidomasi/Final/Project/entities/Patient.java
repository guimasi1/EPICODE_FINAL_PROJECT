package guidomasi.Final.Project.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import guidomasi.Final.Project.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate dateOfBirth;
    private Gender gender;
    private String profilePictureUrl;
    private LocalDate registrationDate;
    @OneToMany(mappedBy = "patient")
    private List<ExercisesAssignment> exercisesAssignments;
    @JsonIgnore
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "physiotherapists_patients",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "physiotherapist_id"))
    private List<Physiotherapist> physiotherapists;

}
