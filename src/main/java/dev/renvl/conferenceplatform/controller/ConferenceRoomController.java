package dev.renvl.conferenceplatform.controller;

import dev.renvl.conferenceplatform.dto.ConferenceRoomRequest;
import dev.renvl.conferenceplatform.dto.MessageResponseDto;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.service.ConferenceRoomService;
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
import java.util.List;
import java.util.Set;

@Tag(name = "ConferenceRoom", description = "ConferenceRoom management APIs")
@RestController
@RequestMapping("/api/conference-room")
public class ConferenceRoomController {

    private final ConferenceRoomService conferenceRoomService;

    public ConferenceRoomController(ConferenceRoomService conferenceRoomService) {
        this.conferenceRoomService = conferenceRoomService;
    }

    @Operation(
            summary = "Create ConferenceRoom",
            description = "Create ConferenceRoom object by specifying its values. The response is ConferenceRoom object")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = ConferenceRoom.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createConferenceRoom(@Valid @RequestBody ConferenceRoomRequest conferenceRoomRequest) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            List<ConferenceRoom> conferenceRooms = conferenceRoomService.createConferenceRoom(conferenceRoomRequest);
            return ResponseEntity.status(httpStatus).body(conferenceRooms);
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

//    @Operation(
//            summary = "Retrieve ConferenceRoom by ID",
//            description = "Get ConferenceRoom by specifying its ID. The response is ConferenceRoom object")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = ConferenceRoomResponse.class), mediaType = "application/json")}),
//            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
//            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
//    @GetMapping("/{account_id}")
//    public ResponseEntity<ConferenceRoomResponse> getConferenceRoom(@PathVariable("account_id") Integer account_id) {
//        return ResponseEntity.ok(accountService.getConferenceRoom(account_id));
//    }


}
