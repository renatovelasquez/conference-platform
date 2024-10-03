package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.model.ConferenceUser;

public interface RegistrationService {
    ConferenceUser register(ConferenceUser conferenceUser);

    boolean cancelRegistration(String participantCode);
}
