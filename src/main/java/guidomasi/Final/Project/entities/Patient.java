package guidomasi.Final.Project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import guidomasi.Final.Project.enums.Gender;
import guidomasi.Final.Project.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "patients")
@JsonIgnoreProperties({"password", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked","username"})
public class Patient implements UserDetails {
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
    private Role role;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<ExercisesAssignment> exercisesAssignments;
    @JsonIgnore
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "physiotherapists_patients",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "physiotherapist_id"))
    private List<Physiotherapist> physiotherapists;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<LinkRequest> linkRequests;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private List<Review> reviews;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
