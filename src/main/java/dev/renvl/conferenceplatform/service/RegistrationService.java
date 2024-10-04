package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.RegistrationRequest;

public interface RegistrationService {
    String registerParticipant(RegistrationRequest request);

    void cancelRegistration(String registrationCode);
}
