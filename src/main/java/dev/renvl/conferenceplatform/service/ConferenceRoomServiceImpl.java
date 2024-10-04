package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.ConferenceRoomRequest;
import dev.renvl.conferenceplatform.dto.UpdateConferenceRoomRequest;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.model.RoomStatus;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRoomRepository;
import dev.renvl.conferenceplatform.repository.ParticipantRepository;
import exceptions.ConferencePlatformException;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

    private final ConferenceRoomRepository repository;
    private final ConferenceRepository conferenceRepository;
    private final ParticipantRepository participantRepository;

    public ConferenceRoomServiceImpl(ConferenceRoomRepository repository, ConferenceRepository conferenceRepository, ParticipantRepository participantRepository) {
        this.repository = repository;
        this.conferenceRepository = conferenceRepository;
        this.participantRepository = participantRepository;
    }

    @Override
    @Transactional
    public List<ConferenceRoom> createConferenceRoom(ConferenceRoomRequest request) {
        Iterable<ConferenceRoom> conferenceRooms = repository.saveAll(request.getConferenceRooms());
        return Streamable.of(conferenceRooms).toList();
    }

    @Override
    public ConferenceRoom updateConferenceRoom(UpdateConferenceRoomRequest request) {
        ConferenceRoom conferenceRoom = repository.findById(request.getIdConferenceRoom())
                .orElseThrow(() -> new ConferencePlatformException("Conference Room not found."));

        if (request.getStatus().equals(RoomStatus.UNDER_CONSTRUCTION)) {
            List<Conference> conferencesAfter = conferenceRepository.conferencesAfterStartAndEndDates(conferenceRoom);
            if (!conferencesAfter.isEmpty())
                throw new ConferencePlatformException("Conference room with booked conferences, not possible to update status.");
        }

        if (request.getMaxCapacity() > conferenceRoom.getMaxCapacity())
            throw new ConferencePlatformException("Conference room exceeds maximum capacity.");

        conferenceRoom.setStatus(request.getStatus());
        conferenceRoom.setMaxCapacity(request.getMaxCapacity());

        return repository.save(conferenceRoom);
    }
}
