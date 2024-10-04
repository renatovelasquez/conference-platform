package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.model.Participant;

public interface RegistrationService {
    Participant register(Participant participant);

    boolean cancelRegistration(String participantCode);
}
