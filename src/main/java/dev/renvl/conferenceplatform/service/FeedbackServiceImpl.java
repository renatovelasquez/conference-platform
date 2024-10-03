package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.model.Feedback;
import dev.renvl.conferenceplatform.repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repository;

    public FeedbackServiceImpl(FeedbackRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Feedback> findByConference(Long conferenceId) {
        List<Feedback> feedbacks = repository.findAll();
        for (Feedback feedback : feedbacks) {
            String name = feedback.getConferenceUser().getFullName().replaceAll("(\\b\\w)(\\w+)", "$1***");
            feedback.getConferenceUser().setFullName(name);
        }
        return feedbacks;
    }

    @Override
    public Feedback sendFeedback(Feedback feedback) {
        return null;
    }
}
