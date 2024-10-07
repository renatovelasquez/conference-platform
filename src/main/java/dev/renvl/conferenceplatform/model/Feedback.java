package dev.renvl.conferenceplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateCreated;
    @ManyToOne(optional = false)
    @JoinColumn(name = "registration_conference_id", nullable = false, updatable = false)
    private RegistrationConference registrationConference;
}