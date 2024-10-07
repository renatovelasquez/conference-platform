package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.FeedbackRequest;
import dev.renvl.conferenceplatform.dto.FeedbackResponse;
import dev.renvl.conferenceplatform.model.Feedback;

import java.util.List;

public interface FeedbackService {
    FeedbackResponse findByConference(Long idConference);

    Feedback sendFeedback(FeedbackRequest request);
}
