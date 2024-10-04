package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.FeedbackRequest;
import dev.renvl.conferenceplatform.model.Feedback;

import java.util.List;

public interface FeedbackService {
    List<Feedback> findByConference(Long conferenceId);

    Feedback sendFeedback(FeedbackRequest request);
}
