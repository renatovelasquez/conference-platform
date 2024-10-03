package dev.renvl.conferenceplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConferenceUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String gender;
    private String email;
    @Column(name="date_of_birth")
    @Temporal(TemporalType.DATE)
    private LocalDate dateOfBirth;
    private String participantCode;
}