package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.ConferenceRoomRequest;
import dev.renvl.conferenceplatform.dto.UpdateConferenceRoomRequest;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.model.Status;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRoomRepository;
import exceptions.ConferencePlatformException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConferenceRoomServiceImpl implements ConferenceRoomService {

    private final ConferenceRoomRepository repository;
    private final ConferenceRepository conferenceRepository;

    public ConferenceRoomServiceImpl(ConferenceRoomRepository repository, ConferenceRepository conferenceRepository) {
        this.repository = repository;
        this.conferenceRepository = conferenceRepository;
    }

    @Override
    public List<ConferenceRoom> createConferenceRoom(ConferenceRoomRequest request) {
        return repository.saveAll(request.getConferenceRooms());
    }

    @Override
    @Transactional
    public ConferenceRoom updateConferenceRoom(UpdateConferenceRoomRequest request) {
        ConferenceRoom conferenceRoom = repository.findById(request.getIdConferenceRoom())
                .orElseThrow(() -> new ConferencePlatformException("Conference Room not found."));

        if (request.getStatus().equals(Status.UNDER_CONSTRUCTION)) {
            List<Conference> conferencesAfter = conferenceRepository.conferencesAfterStartOrEndDatesByIdRoom(request.getIdConferenceRoom());
            if (!conferencesAfter.isEmpty())
                throw new ConferencePlatformException("Conference Room with conferences, not possible update to '"
                        + Status.UNDER_CONSTRUCTION + "' status.");
        } else {
            List<Conference> roomConferences = conferenceRepository.findConferencesByConferenceRoom_Id(request.getIdConferenceRoom());
            for (Conference conference : roomConferences) {
                if (conference.getRegistrations().size() > request.getMaxCapacity())
                    throw new ConferencePlatformException("Conference '" + conference.getName()
                            + "' number of participants exceeds new capacity.");
            }
        }

        conferenceRoom.setStatus(request.getStatus());
        conferenceRoom.setMaxCapacity(request.getMaxCapacity());

        return repository.save(conferenceRoom);
    }
}
