package dev.renvl.conferenceplatform.controller;

import dev.renvl.conferenceplatform.dto.CancelConferenceRequest;
import dev.renvl.conferenceplatform.dto.ConferenceRequest;
import dev.renvl.conferenceplatform.dto.MessageResponseDto;
import dev.renvl.conferenceplatform.dto.UpdateConferenceRequest;
import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.service.ConferenceService;
import exceptions.ConferencePlatformException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Tag(name = "Conference", description = "Conference management APIs")
@RestController
@RequestMapping("/api/conference")
public class ConferenceController {

    private final ConferenceService conferenceService;

    public ConferenceController(ConferenceService conferenceService) {
        this.conferenceService = conferenceService;
    }

    @Operation(
            summary = "Create Conference",
            description = "Create Conference object by specifying its values. The response is Conference object")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Conference.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createConference(@Valid @RequestBody ConferenceRequest conferenceRequest) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            Conference conference = conferenceService.createConference(conferenceRequest);
            return ResponseEntity.status(httpStatus).body(conference);
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

    @Operation(
            summary = "Cancel Conference",
            description = "Cancel Conference object by specifying its values. The response is Conference object")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Conference.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> cancelConference(@Valid @RequestBody CancelConferenceRequest cancelConferenceRequest) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            conferenceService.cancelConference(cancelConferenceRequest);
            return ResponseEntity.status(httpStatus).build();
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

    @Operation(
            summary = "Availability Conferences",
            description = "Availability Conferences object by specifying its values. The response is Conference object")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = HashMap.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> availabilityConferences() {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            HashMap<Conference, Boolean> conferences = conferenceService.availabilityConferences();
            return ResponseEntity.status(httpStatus).body(conferences);
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
            summary = "Update Conference",
            description = "Update Conference object by specifying its values. The response is Conference object")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Conference.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateConference(@Valid @RequestBody UpdateConferenceRequest updateConferenceRequest) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            Conference conference = conferenceService.updateConference(updateConferenceRequest);
            return ResponseEntity.status(httpStatus).body(conference);
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
