package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    Optional<Participant> findByEmail(String email);
}