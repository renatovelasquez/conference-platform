package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.*;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.model.RoomStatus;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRoomRepository;
import dev.renvl.conferenceplatform.repository.ParticipantRepository;
import exceptions.ConferencePlatformException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository repository;
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ParticipantRepository participantRepository;

    public ConferenceServiceImpl(ConferenceRepository repository, ConferenceRoomRepository conferenceRoomRepository, ParticipantRepository participantRepository) {
        this.repository = repository;
        this.conferenceRoomRepository = conferenceRoomRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    public Conference createConference(ConferenceRequest conferenceRequest) {
        ConferenceRoom conferenceRoom = conferenceRoomRepository
                .findById(conferenceRequest.getIdConferenceRoom())
                .orElseThrow(() -> new ConferencePlatformException("Conference room not found."));

        if (!conferenceRoom.getStatus().equals(RoomStatus.FREE))
            throw new ConferencePlatformException("Conference room not available.");

        List<Conference> conferences = repository.conferencesBetweenStartAndEndDates(
                conferenceRequest.getStartConference(), conferenceRequest.getEndConference());
        if (!conferences.isEmpty())
            throw new ConferencePlatformException("Conference room not available during that time.");

        Conference conference = Conference.builder()
                .name(conferenceRequest.getName())
                .startConference(LocalDateTime.from(conferenceRequest.getStartConference()))
                .endConference(LocalDateTime.from(conferenceRequest.getEndConference()))
                .conferenceRoom(conferenceRoom)
                .build();
        return repository.save(conference);
    }

    @Override
    public void cancelConference(CancelConferenceRequest cancelConferenceRequest) {
        Conference conference = repository.findById(cancelConferenceRequest.getId())
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));
        repository.delete(conference);
        //TODO delete all registrations
    }

    @Override
    public AvailabilityConferencesResponse availabilityConferences() {
        AvailabilityConferencesResponse response = new AvailabilityConferencesResponse();
        List<Conference> conferences = repository.findAll();
        for (Conference conference : conferences) {
            int participants = conference.getRegistrations().size();
            boolean availability = conference.getConferenceRoom().getMaxCapacity() < participants;
            AvailableConference availableConference = AvailableConference.builder().conference(conference).available(availability).build();
            response.getAvailableConferences().add(availableConference);
        }
        return response;
    }

    @Override
    @Transactional
    public Conference updateConference(UpdateConferenceRequest updateConferenceRequest) {
        Conference conference = repository.findById(updateConferenceRequest.getIdConference())
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));

        ConferenceRoom conferenceRoom = conferenceRoomRepository.findById(updateConferenceRequest.getIdConference())
                .orElseThrow(() -> new ConferencePlatformException("Conference Room not found."));

        conference.setStartConference(LocalDateTime.from(updateConferenceRequest.getStartConference()));
        conference.setEndConference(LocalDateTime.from(updateConferenceRequest.getEndConference()));
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
