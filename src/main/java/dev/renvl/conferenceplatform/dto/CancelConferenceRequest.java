package dev.renvl.conferenceplatform.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class CancelConferenceRequest {
    @NotNull(message = "id must not be null")
    private Long id;
}