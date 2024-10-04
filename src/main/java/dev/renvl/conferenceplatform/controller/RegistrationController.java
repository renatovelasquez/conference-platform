package dev.renvl.conferenceplatform.controller;

import dev.renvl.conferenceplatform.dto.MessageResponseDto;
import dev.renvl.conferenceplatform.dto.RegistrationRequest;
import dev.renvl.conferenceplatform.dto.RegistrationResponse;
import dev.renvl.conferenceplatform.service.RegistrationService;
import exceptions.ConferencePlatformException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Tag(name = "Registration", description = "Registration management APIs")
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Operation(
            summary = "Create Registration",
            description = "Create Registration object by specifying its values. The response is Registration object")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = RegistrationResponse.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRegistration(@Valid @RequestBody RegistrationRequest request) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            String registrationCode = registrationService.registerParticipant(request);
            return ResponseEntity.status(httpStatus).body(RegistrationResponse.builder().registrationCode(registrationCode).build());
        } catch (ConstraintViolationException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations())
                messages.add(constraintViolation.getMessageTemplate());
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            messages.add(e.getMessage());
        }
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .httpStatus(httpStatus)
                .timestamp(System.currentTimeMillis())
                .messages(messages).build();
        return ResponseEntity.status(messageResponseDto.getHttpStatus()).body(messageResponseDto);
    }

    @Operation(
            summary = "Cancel Registration",
            description = "Cancel Registration object by specifying its values. The response is Registration object")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping("{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> cancelRegistration(@PathVariable("code") String registrationCode) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus;
        try {
            registrationService.cancelRegistration(registrationCode);
            return ResponseEntity.ok("Registration cancelled");
        } catch (ConferencePlatformException e) {
            httpStatus = HttpStatus.BAD_REQUEST;
            messages.add(e.getMessage());
        } catch (Exception e) {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            messages.add(e.getMessage());
        }
        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
                .httpStatus(httpStatus)
                .timestamp(System.currentTimeMillis())
                .messages(messages).build();
        return ResponseEntity.status(messageResponseDto.getHttpStatus()).body(messageResponseDto);
    }
}
