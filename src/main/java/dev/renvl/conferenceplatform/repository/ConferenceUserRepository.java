package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.Participant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceUserRepository extends CrudRepository<Participant, Long> {
}