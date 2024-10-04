package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.*;
import dev.renvl.conferenceplatform.model.Conference;

import java.util.HashMap;
import java.util.List;

public interface ConferenceService {
    Conference createConference(ConferenceRequest conferenceRequest);

    void cancelConference(CancelConferenceRequest cancelConferenceRequest);

    AvailabilityConferencesResponse availabilityConferences();

    Conference updateConference(UpdateConferenceRequest updateConferenceRequest);

    AvailabilityConferencesResponse getAvailableConferences(AvailableConferencesRequest request);
}
