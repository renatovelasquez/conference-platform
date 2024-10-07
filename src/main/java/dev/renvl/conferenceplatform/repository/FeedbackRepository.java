package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    @Query("select f from Feedback f where f.registrationConference.conference.id = ?1")
    List<Feedback> getFeedbacksByIdConference(Long idConference);
}