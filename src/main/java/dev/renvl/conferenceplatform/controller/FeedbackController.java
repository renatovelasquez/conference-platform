package dev.renvl.conferenceplatform.controller;

import dev.renvl.conferenceplatform.dto.FeedbackRequest;
import dev.renvl.conferenceplatform.dto.MessageResponseDto;
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

//    @Operation(
//            summary = "Cancel Registration",
//            description = "Cancel Registration object by specifying its values. The response is Registration object")
//    @ApiResponses({
//            @ApiResponse(responseCode = "200", content = {@Content(schema = @Schema(implementation = String.class), mediaType = "application/json")}),
//            @ApiResponse(responseCode = "404", content = {@Content(schema = @Schema())}),
//            @ApiResponse(responseCode = "500", content = {@Content(schema = @Schema())})})
//    @DeleteMapping("{code}")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<?> cancelRegistration(@PathVariable("code") String registrationCode) {
//        Set<String> messages = new HashSet<>();
//        HttpStatus httpStatus;
//        try {
//            registrationService.cancelRegistration(registrationCode);
//            return ResponseEntity.ok("Registration cancelled");
//        } catch (ConferencePlatformException e) {
//            httpStatus = HttpStatus.BAD_REQUEST;
//            messages.add(e.getMessage());
//        } catch (Exception e) {
//            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
//            messages.add(e.getMessage());
//        }
//        MessageResponseDto messageResponseDto = MessageResponseDto.builder()
//                .httpStatus(httpStatus)
//                .timestamp(System.currentTimeMillis())
//                .messages(messages).build();
//        return ResponseEntity.status(messageResponseDto.getHttpStatus()).body(messageResponseDto);
//    }
}
