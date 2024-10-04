package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
}