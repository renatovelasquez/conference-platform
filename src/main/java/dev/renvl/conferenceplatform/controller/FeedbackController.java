package dev.renvl.conferenceplatform.controller;

import dev.renvl.conferenceplatform.dto.*;
import dev.renvl.conferenceplatform.model.Feedback;
import dev.renvl.conferenceplatform.service.FeedbackService;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Tag(name = "Feedback", description = "Feedback management APIs")
@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Operation(
            summary = "Send Feedback",
            description = "Send Feedback object by specifying its values. The response is Feedback object")
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {@Content(schema = @Schema(implementation = Feedback.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createFeedback(@Valid @RequestBody FeedbackRequest request) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.CREATED;
        try {
            Feedback feedback = feedbackService.sendFeedback(request);
            return ResponseEntity.status(httpStatus).body(feedback);
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
            summary = "Feedback",
            description = "Feedback object by specifying its values. The response is Conference object")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = Feedback.class), mediaType = "application/json")}),
            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
    @GetMapping("{idConference}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAvailableConferences(@PathVariable("idConference") Long idConference) {
        Set<String> messages = new HashSet<>();
        HttpStatus httpStatus = HttpStatus.OK;
        try {
            FeedbackResponse response = feedbackService.findByConference(idConference);
            return ResponseEntity.status(httpStatus).body(response);
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
