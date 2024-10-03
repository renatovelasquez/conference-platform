package dev.renvl.conferenceplatform.dto;

import dev.renvl.conferenceplatform.model.ConferenceRoom;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomRequest {
    List<ConferenceRoom> conferenceRooms;
}
