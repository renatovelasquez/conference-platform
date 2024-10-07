package dev.renvl.conferenceplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private Long id;
    private String name;
    private LocalDateTime startConference;
    private LocalDateTime endConference;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conference_room_id", nullable = false, updatable = false)
    private ConferenceRoom conferenceRoom;
    @JsonIgnore
    @OneToMany(mappedBy = "conference")
    private Set<RegistrationConference> registrations;
    @Transient
    private int freeSpots;
    @Transient
    private Status availability;
}