package dev.renvl.conferenceplatform.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

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
    @Column(name="date_created")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCreated;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conference_user_id", nullable = false, updatable = false)
    private ConferenceUser conferenceUser;
    @ManyToOne(optional = false)
    @JoinColumn(name = "conference_room_id", nullable = false, updatable = false)
    private ConferenceRoom conferenceRoom;
}