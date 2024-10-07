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
            "(c.startConference < ?1 and c.endConference > ?1) or " +
            "(c.startConference < ?2 and c.endConference > ?2) or " +
            "(c.startConference >= ?1 and c.endConference <= ?2)")
    List<Conference> conferencesBetweenStartAndEndDates(LocalDateTime start, LocalDateTime end);

    @Query("select c from Conference c where c.conferenceRoom = ?1 and " +
            "current_time >= c.startConference or current_time >= c.endConference")
    List<Conference> conferencesAfterStartAndEndDates(ConferenceRoom room);

    @Query("select c, cr.maxCapacity - count(rc) as freeSpots," +
            "case when count(rc) < cr.maxCapacity then 'AVAILABLE' else 'FULL' end as availability " +
            "from Conference c " +
            "inner join ConferenceRoom cr on cr = c.conferenceRoom " +
            "left join RegistrationConference rc on rc.conference = c " +
            "where (c.startConference < ?1 and c.endConference > ?1) or " +
            "(c.startConference < ?2 and c.endConference > ?2) or " +
            "(c.startConference >= ?1 and c.endConference <= ?2) " +
            "group by cr.maxCapacity")
    List<Conference> getAvailableConferences(LocalDateTime start, LocalDateTime end);
}