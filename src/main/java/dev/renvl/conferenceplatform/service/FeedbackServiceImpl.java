package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.FeedbackRequest;
import dev.renvl.conferenceplatform.model.Feedback;
import dev.renvl.conferenceplatform.model.RegistrationConference;
import dev.renvl.conferenceplatform.repository.ConferenceRegistrationRepository;
import dev.renvl.conferenceplatform.repository.FeedbackRepository;
import exceptions.ConferencePlatformException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repository;
    private final ConferenceRegistrationRepository registrationRepository;

    public FeedbackServiceImpl(FeedbackRepository repository, ConferenceRegistrationRepository registrationRepository) {
        this.repository = repository;
        this.registrationRepository = registrationRepository;
    }

    @Override
    public List<Feedback> findByConference(Long conferenceId) {
        List<Feedback> feedbacks = repository.findAll();
        for (Feedback feedback : feedbacks) {
//            String name = feedback.getParticipant().getFullName().replaceAll("(\\b\\w)(\\w+)", "$1***");
//            feedback.getParticipant().setFullName(name);
        }
        return feedbacks;
    }

    @Override
    public Feedback sendFeedback(FeedbackRequest request) {
        RegistrationConference registration = registrationRepository.findByRegistrationCode(request.getRegistrationCode())
                .orElseThrow(() -> new ConferencePlatformException("Registration not found."));

        Feedback feedback = Feedback.builder()
                .feedback(request.getFeedback())
                .dateCreated(LocalDateTime.now())
                .registrationConference(registration)
                .build();

        return repository.save(feedback);
    }
}
