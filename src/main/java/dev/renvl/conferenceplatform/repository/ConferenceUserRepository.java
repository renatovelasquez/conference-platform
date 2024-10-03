package dev.renvl.conferenceplatform.repository;

import dev.renvl.conferenceplatform.model.ConferenceUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceUserRepository extends CrudRepository<ConferenceUser, Long> {
}