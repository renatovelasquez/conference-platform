package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    int countParticipantsByConference_Id(Long conferenceId);
    int countParticipantsByConference_ConferenceRoom(ConferenceRoom conferenceRoom);
}