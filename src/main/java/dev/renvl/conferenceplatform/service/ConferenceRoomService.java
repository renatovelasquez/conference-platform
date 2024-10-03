package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.ConferenceRoomRequest;
import dev.renvl.conferenceplatform.dto.UpdateConferenceRoomRequest;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;

import java.util.List;

public interface ConferenceRoomService {
    List<ConferenceRoom> createConferenceRoom(ConferenceRoomRequest request);

    ConferenceRoom updateConferenceRoom(UpdateConferenceRoomRequest request);

}
