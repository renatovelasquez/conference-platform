package dev.renvl.conferenceplatform.service;

import dev.renvl.conferenceplatform.dto.ConferenceRoomRequest;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;

import java.util.List;

public interface ConferenceUserService {
    List<ConferenceRoom> createConferenceRoom(ConferenceRoomRequest conferenceRoomRequest);

    ConferenceRoom updateConferenceRoom(Conference conference);

}
