package dev.renvl.conferenceplatform.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(exclude = "id")
public class ConferenceRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @NotBlank(message = "Name must not be blank")
    private String name;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status must not be blank")
    private Status status;
    @NotBlank(message = "Location must not be blank")
    private String location;
    @Positive(message = "Max capacity must not be zero")
    private int maxCapacity;

}