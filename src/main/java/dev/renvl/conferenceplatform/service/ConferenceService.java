package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.*;
import dev.renvl.conferenceplatform.model.Conference;

import java.util.HashMap;
import java.util.List;

public interface ConferenceService {
    Conference createConference(ConferenceRequest request);

    void cancelConference(Long idConference);

    Conference availabilityConference(Long idConference);

    Conference updateConference(UpdateConferenceRequest request);

    AvailabilityConferencesResponse getAvailableConferences(AvailableConferencesRequest request);
}
