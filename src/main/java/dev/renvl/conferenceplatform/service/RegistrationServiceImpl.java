package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.RegistrationRequest;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRegistration;
import dev.renvl.conferenceplatform.model.Participant;
import dev.renvl.conferenceplatform.repository.ConferenceRegistrationRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.ParticipantRepository;
import exceptions.ConferencePlatformException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final ParticipantRepository participantRepository;
    private final ConferenceRepository conferenceRepository;
    private final ConferenceRegistrationRepository conferenceRegistrationRepository;

    public RegistrationServiceImpl(ParticipantRepository participantRepository, ConferenceRepository conferenceRepository, ConferenceRegistrationRepository conferenceRegistrationRepository) {
        this.participantRepository = participantRepository;
        this.conferenceRepository = conferenceRepository;
        this.conferenceRegistrationRepository = conferenceRegistrationRepository;
    }

    @Override
    @Transactional
    public String registerParticipant(RegistrationRequest request) {
        Participant participant = participantRepository.findByEmail(request.getEmail())
                .orElse(participantRepository.save(Participant.builder()
                        .fullName(request.getFullName())
                        .email(request.getEmail())
                        .dateOfBirth(request.getDateOfBirth())
                        .gender(request.getGender())
                        .build())
                );

        Conference conference = conferenceRepository.findById(request.getIdConference())
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));

        String registrationCode = UUID.randomUUID().toString().split("-")[0];
        ConferenceRegistration registration = ConferenceRegistration.builder()
                .conference(conference)
                .participant(participant)
                .registrationCode(registrationCode)
                .build();

        registration = conferenceRegistrationRepository.save(registration);
        return registration.getRegistrationCode();
    }

    @Override
    public void cancelRegistration(String registrationCode) {
        ConferenceRegistration conferenceRegistration = conferenceRegistrationRepository.findByRegistrationCode(registrationCode)
                .orElseThrow(() -> new ConferencePlatformException("Registration not found."));
        conferenceRegistrationRepository.delete(conferenceRegistration);
    }
}
