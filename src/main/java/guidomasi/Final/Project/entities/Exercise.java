package guidomasi.Final.Project.entities;


import guidomasi.Final.Project.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "exercises")
public class Exercise {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;
    private String name;
    private String description;
    private String imageUrl;
    private String videoUrl;
    private String targetArea;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    @OneToMany(mappedBy = "exercise")
    private List<ExerciseDetails> exerciseDetails;

}
