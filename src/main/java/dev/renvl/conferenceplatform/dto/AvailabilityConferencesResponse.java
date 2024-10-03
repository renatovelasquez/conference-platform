package dev.renvl.conferenceplatform.dto;

import dev.renvl.conferenceplatform.model.Conference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailabilityConferencesResponse {
    private Conference conference;
    private boolean available;
}