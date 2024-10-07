package dev.renvl.conferenceplatform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FeedbackResponse {
    private List<FeedbackConference> feedbackConferences;
}
