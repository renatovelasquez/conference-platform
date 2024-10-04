package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.ConferenceRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConferenceRegistrationRepository extends JpaRepository<ConferenceRegistration, Long> {
    Optional<ConferenceRegistration> findByRegistrationCode(String registrationCode);
}