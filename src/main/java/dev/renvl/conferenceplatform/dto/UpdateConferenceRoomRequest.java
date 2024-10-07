package dev.renvl.conferenceplatform.dto;

import dev.renvl.conferenceplatform.model.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class UpdateConferenceRoomRequest {

    @NotNull(message = "Conference Room must not be blank")
    private Long idConferenceRoom;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status must not be blank")
    private Status status;
    @Positive(message = "Max capacity must not be zero")
    private int maxCapacity;
}
