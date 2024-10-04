package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.RegistrationConference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConferenceRegistrationRepository extends JpaRepository<RegistrationConference, Long> {
    Optional<RegistrationConference> findByRegistrationCode(String registrationCode);
}