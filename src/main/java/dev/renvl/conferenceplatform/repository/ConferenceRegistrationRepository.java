package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.ConferenceRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRegistrationRepository extends JpaRepository<ConferenceRegistration, Long> {
}