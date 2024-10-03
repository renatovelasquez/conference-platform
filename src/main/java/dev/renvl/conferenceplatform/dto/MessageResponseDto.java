package dev.renvl.conferenceplatform.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponseDto {
    private HttpStatus httpStatus;
    private Set<String> messages;
    private long timestamp;
}
