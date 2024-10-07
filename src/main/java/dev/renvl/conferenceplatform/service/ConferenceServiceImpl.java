package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.*;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.model.Status;
import dev.renvl.conferenceplatform.repository.ConferenceRegistrationRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRoomRepository;
import exceptions.ConferencePlatformException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository repository;
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ConferenceRegistrationRepository conferenceRegistrationRepository;

    public ConferenceServiceImpl(ConferenceRepository repository, ConferenceRoomRepository conferenceRoomRepository, ConferenceRegistrationRepository conferenceRegistrationRepository) {
        this.repository = repository;
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.conferenceRegistrationRepository = conferenceRegistrationRepository;
    }

    @Override
    @Transactional
    public Conference createConference(ConferenceRequest request) {
        ConferenceRoom conferenceRoom = conferenceRoomRepository
                .findById(request.getIdConferenceRoom())
                .orElseThrow(() -> new ConferencePlatformException("Conference room not found."));

        if (!conferenceRoom.getStatus().equals(Status.AVAILABLE))
            throw new ConferencePlatformException("Conference room " + conferenceRoom.getStatus());

        List<Conference> conferences = repository.conferencesBetweenStartAndEndDates(
                request.getStartConference(), request.getEndConference());
        if (!conferences.isEmpty())
            throw new ConferencePlatformException("Conference room not available during that time.");

        Conference conference = Conference.builder()
                .name(request.getName())
                .startConference(LocalDateTime.from(request.getStartConference()))
                .endConference(LocalDateTime.from(request.getEndConference()))
                .conferenceRoom(conferenceRoom)
                .availability(Status.AVAILABLE)
                .build();
        return repository.save(conference);
    }

    @Override
    @Transactional
    public void cancelConference(Long idConference) {
        Conference conference = repository.getConferenceAfterStartOrEndCurrentDate(idConference)
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));

        conferenceRegistrationRepository.deleteAll(conference.getRegistrations());
        repository.delete(conference);
    }

    @Override
    public Conference availabilityConference(Long idConference) {
        Conference conference = repository.findById(idConference)
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));
        conference.setAvailability(conference.getRegistrations().size() == conference.getConferenceRoom().getMaxCapacity() ? Status.FULL : Status.AVAILABLE);
        conference.setFreeSpots(conference.getConferenceRoom().getMaxCapacity() - conference.getRegistrations().size());
        return conference;
    }

    @Override
    @Transactional
    public Conference updateConference(UpdateConferenceRequest request) {
        Conference conference = repository.findById(request.getIdConference())
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));

        ConferenceRoom conferenceRoom = conferenceRoomRepository.findById(request.getIdConferenceRoom())
                .orElseThrow(() -> new ConferencePlatformException("Conference Room not found."));
        if (!conferenceRoom.getStatus().equals(Status.AVAILABLE))
            throw new ConferencePlatformException("Conference room " + conferenceRoom.getStatus());

        List<Conference> conferences = repository.conferencesBetweenStartAndEndDatesByConferenceRoom(request.getIdConferenceRoom(),
                request.getStartConference(), request.getEndConference());
        if (!conferences.isEmpty())
            throw new ConferencePlatformException("Conference room not available during that time.");

        conference.setStartConference(LocalDateTime.from(request.getStartConference()));
        conference.setEndConference(LocalDateTime.from(request.getEndConference()));
        conference.setConferenceRoom(conferenceRoom);

        return repository.save(conference);
    }

    @Override
    public AvailabilityConferencesResponse getAvailableConferences(AvailableConferencesRequest request) {
        AvailabilityConferencesResponse response = new AvailabilityConferencesResponse();
        List<Conference> conferences = repository.getAvailableConferences(request.getStartConference(), request.getEndConference());
        for (Conference conference : conferences) {
            AvailableConference availableConference = AvailableConference.builder().conference(conference).build();
            response.getAvailableConferences().add(availableConference);
        }
        return response;
    }
}
