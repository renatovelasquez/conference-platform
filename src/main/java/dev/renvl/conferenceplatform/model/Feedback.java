package dev.renvl.conferenceplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String feedback;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateCreated;
    @ManyToOne(optional = false)
    @JoinColumn(name = "participant_id", nullable = false, updatable = false)
    private Participant participant;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conference_room_id", nullable = false, updatable = false)
    private ConferenceRoom conferenceRoom;
}