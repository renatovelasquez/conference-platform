package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConferenceRepository extends JpaRepository<Conference, Long> {

    @Query("select c from Conference c where " +
            "(?1 between c.startConference and c.endConference) and " +
            "(?2 between c.startConference and c.endConference) and " +
            "(c.endConference != ?1 or c.startConference != ?2)")
    List<Conference> conferencesBetweenStartAndEndDates(LocalDateTime start, LocalDateTime end);

    @Query("select c from Conference c where c.conferenceRoom = ?1 and " +
            "current_time >= c.startConference or current_time >= c.endConference")
    List<Conference> conferencesAfterStartAndEndDates(ConferenceRoom room);
}