package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.FeedbackConference;
import dev.renvl.conferenceplatform.dto.FeedbackRequest;
import dev.renvl.conferenceplatform.dto.FeedbackResponse;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.Feedback;
import dev.renvl.conferenceplatform.model.RegistrationConference;
import dev.renvl.conferenceplatform.repository.ConferenceRegistrationRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.FeedbackRepository;
import exceptions.ConferencePlatformException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository repository;
    private final ConferenceRegistrationRepository registrationRepository;
    private final ConferenceRepository conferenceRepository;

    public FeedbackServiceImpl(FeedbackRepository repository, ConferenceRegistrationRepository registrationRepository, ConferenceRepository conferenceRepository) {
        this.repository = repository;
        this.registrationRepository = registrationRepository;
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    @Transactional
    public FeedbackResponse findByConference(Long idConference) {
        Conference conference = conferenceRepository.findById(idConference)
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));

        List<Feedback> feedbacks = repository.getFeedbacksByIdConference(conference.getId());
        List<FeedbackConference> feedbackConferences = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            String author = feedback.getRegistrationConference().getParticipant().getFullName()
                    .replaceAll("(\\b\\w+\\s)(\\w)(\\w+)", "$1$2***");
            feedbackConferences.add(FeedbackConference.builder()
                    .author(author)
                    .content(feedback.getContent())
                    .build()
            );
        }
        return FeedbackResponse.builder().feedbackConferences(feedbackConferences).build();
    }

    @Override
    @Transactional
    public Feedback sendFeedback(FeedbackRequest request) {
        RegistrationConference registration = registrationRepository.findByRegistrationCode(request.getRegistrationCode())
                .orElseThrow(() -> new ConferencePlatformException("Registration not found."));

        Feedback feedback = Feedback.builder()
                .content(request.getFeedback())
                .dateCreated(LocalDateTime.now())
                .registrationConference(registration)
                .build();

        return repository.save(feedback);
    }
}
