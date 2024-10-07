package dev.renvl.conferenceplatform.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class UpdateConferenceRequest {

    @NotNull(message = "Conference must not be blank")
    private Long idConference;
    @NotNull(message = "Start time conference must not be null")
    @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startConference;
    @NotNull(message = "End time conference must not be null")
    @Schema(type = "string", pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endConference;
    @NotNull(message = "Conference room must not be blank")
    private Long idConferenceRoom;
}
