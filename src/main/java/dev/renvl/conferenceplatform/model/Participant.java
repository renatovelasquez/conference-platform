package dev.renvl.conferenceplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conference_user_id", nullable = false, updatable = false)
    private ConferenceUser conferenceUser;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conference_id", nullable = false, updatable = false)
    private Conference conference;
}