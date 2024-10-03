package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.CancelConferenceRequest;
import dev.renvl.conferenceplatform.dto.ConferenceRequest;
import dev.renvl.conferenceplatform.dto.UpdateConferenceRequest;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.model.RoomStatus;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRoomRepository;
import dev.renvl.conferenceplatform.repository.ParticipantRepository;
import exceptions.ConferencePlatformException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class ConferenceServiceImpl implements ConferenceService {

    private final ConferenceRepository conferenceRepository;
    private final ConferenceRoomRepository conferenceRoomRepository;
    private final ParticipantRepository participantRepository;

    public ConferenceServiceImpl(ConferenceRepository conferenceRepository, ConferenceRoomRepository conferenceRoomRepository, ParticipantRepository participantRepository) {
        this.conferenceRepository = conferenceRepository;
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

        List<Conference> conferences = conferenceRepository.conferencesBetweenStartAndEndDates
                (conferenceRequest.getStartConference(), conferenceRequest.getEndConference());
        if (!conferences.isEmpty())
            throw new ConferencePlatformException("Conference room not available during that time.");

        Conference conference = Conference.builder()
                .name(conferenceRequest.getName())
                .startConference(conferenceRequest.getStartConference())
                .endConference(conferenceRequest.getEndConference())
                .conferenceRoom(conferenceRoom)
                .build();
        return conferenceRepository.save(conference);
    }

    @Override
    public void cancelConference(CancelConferenceRequest cancelConferenceRequest) {
        Conference conference = conferenceRepository.findById(cancelConferenceRequest.getId())
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));
        conferenceRepository.delete(conference);
        //TODO delete all registrations
    }

    @Override
    public HashMap<Conference, Boolean> availabilityConferences() {
        HashMap<Conference, Boolean> availabilityConferences = new HashMap<>();
        List<Conference> conferences = conferenceRepository.findAll();
        for (Conference conf : conferences) {
            int participants = participantRepository.countParticipantsByConference_Id(conf.getId());
            boolean availability = conf.getConferenceRoom().getMaxCapacity() < participants;
            availabilityConferences.put(conf, availability);
        }
        return availabilityConferences;
    }

    @Override
    @Transactional
    public Conference updateConference(UpdateConferenceRequest updateConferenceRequest) {
        Conference conference = conferenceRepository.findById(updateConferenceRequest.getIdConference())
                .orElseThrow(() -> new ConferencePlatformException("Conference not found."));

        ConferenceRoom conferenceRoom = conferenceRoomRepository.findById(updateConferenceRequest.getIdConference())
                .orElseThrow(() -> new ConferencePlatformException("Conference Room not found."));

        conference.setStartConference(updateConferenceRequest.getStartConference());
        conference.setEndConference(updateConferenceRequest.getEndConference());
        conference.setConferenceRoom(conferenceRoom);

        return conferenceRepository.save(conference);
    }
}
