package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.CancelConferenceRequest;
import dev.renvl.conferenceplatform.dto.ConferenceRequest;
import dev.renvl.conferenceplatform.dto.UpdateConferenceRequest;
import dev.renvl.conferenceplatform.model.Conference;

import java.util.HashMap;

public interface ConferenceService {
    Conference createConference(ConferenceRequest conferenceRequest);

    void cancelConference(CancelConferenceRequest cancelConferenceRequest);

    HashMap<Conference, Boolean> availabilityConferences();

    Conference updateConference(UpdateConferenceRequest updateConferenceRequest);
}
