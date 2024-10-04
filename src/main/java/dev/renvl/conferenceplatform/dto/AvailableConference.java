package dev.renvl.conferenceplatform.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.renvl.conferenceplatform.model.Conference;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AvailableConference {
    private Conference conference;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean available;
}