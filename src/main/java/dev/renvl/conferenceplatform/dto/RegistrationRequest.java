package dev.renvl.conferenceplatform.dto;

import dev.renvl.conferenceplatform.model.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
@AllArgsConstructor
public class RegistrationRequest {

    private String fullName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Email
    @NotBlank(message = "Email must not be blank")
    private String email;
    @Schema(type = "string", pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Long idConference;
}
