package dev.renvl.conferenceplatform.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AvailabilityConferencesResponse {
    private List<AvailableConference> availableConferences;

    public AvailabilityConferencesResponse() {
        this.availableConferences = new ArrayList<>();
    }
}
