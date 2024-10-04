package dev.renvl.conferenceplatform.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender must not be null")
    private Gender gender;
    private String email;
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    @OneToMany(mappedBy = "participant")
    private Set<ConferenceRegistration> registrations;
}