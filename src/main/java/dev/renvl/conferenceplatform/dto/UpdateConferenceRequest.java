package dev.renvl.conferenceplatform.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
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
    @JsonFormat(locale = "ee", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Tallinn")
    private LocalDateTime startConference;
    @NotNull(message = "End time conference must not be null")
    @JsonFormat(locale = "ee", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Tallinn")
    private LocalDateTime endConference;
    @NotNull(message = "Conference room must not be blank")
    private Long idConferenceRoom;
}
