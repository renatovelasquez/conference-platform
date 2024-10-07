package dev.renvl.conferenceplatform.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FeedbackConference {
    private String content;
    private String author;
}
