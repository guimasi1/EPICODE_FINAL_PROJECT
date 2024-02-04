package guidomasi.Final.Project.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import guidomasi.Final.Project.enums.Role;
import guidomasi.Final.Project.enums.Specialization;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
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
@Table(name = "physiotherapists")
@JsonIgnoreProperties({"password", "authorities", "enabled", "credentialsNonExpired", "accountNonExpired", "accountNonLocked"})
public class Physiotherapist implements UserDetails {
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
    @Enumerated(EnumType.STRING)
    private Specialization specialization;
    private String bio;
    private String profilePictureUrl;
    private LocalDate registrationDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    @JsonIgnore
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "physiotherapists_patients",
            joinColumns = @JoinColumn(name = "physiotherapist_id"),
            inverseJoinColumns = @JoinColumn(name = "patient_id"))
    private List<Patient> patients;
    @OneToMany(mappedBy = "assignedBy")
    @JsonManagedReference
    @ToString.Exclude
    private List<ExercisesAssignment> exercisesAssignments;

    @OneToMany(mappedBy = "physiotherapist")
    @JsonManagedReference
    @ToString.Exclude
    private List<LinkRequest> linkRequests;

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
