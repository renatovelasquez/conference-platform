package dev.renvl.conferenceplatform.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class Conference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startConference;
    private LocalDateTime endConference;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conference_room_id", nullable = false, updatable = false)
    private ConferenceRoom conferenceRoom;
    @ManyToMany
    Set<Participant> participants;
}