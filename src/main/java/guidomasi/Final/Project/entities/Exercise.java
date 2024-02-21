package guidomasi.Final.Project.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import guidomasi.Final.Project.enums.DifficultyLevel;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.util.QTypeContributor;

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
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "text")
    private String imageUrl;
    private String videoUrl;
    private String targetArea;
    @Enumerated(EnumType.STRING)
    private DifficultyLevel difficultyLevel;
    @OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    
    private List<ExerciseDetails> exerciseDetails;

}
