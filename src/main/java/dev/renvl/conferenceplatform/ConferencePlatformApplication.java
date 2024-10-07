package dev.renvl.conferenceplatform;

import dev.renvl.conferenceplatform.model.Conference;
import dev.renvl.conferenceplatform.model.ConferenceRoom;
import dev.renvl.conferenceplatform.model.Status;
import dev.renvl.conferenceplatform.repository.ConferenceRepository;
import dev.renvl.conferenceplatform.repository.ConferenceRoomRepository;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;
import java.time.LocalDateTime;

@SpringBootApplication
public class ConferencePlatformApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConferencePlatformApplication.class);

    @Bean
    public CommandLineRunner initDatabase(ConferenceRoomRepository conferenceRoomRepository, ConferenceRepository conferenceRepository) {
        return args -> {

            ConferenceRoom conferenceRoom1 = ConferenceRoom.builder().name("room one").status(Status.AVAILABLE).location("calle 1").maxCapacity(1).build();
            ConferenceRoom conferenceRoom2 = ConferenceRoom.builder().name("room two").status(Status.UNDER_CONSTRUCTION).location("calle 2").maxCapacity(3).build();
            ConferenceRoom conferenceRoom3 = ConferenceRoom.builder().name("room three").status(Status.AVAILABLE).location("calle 3").maxCapacity(6).build();
            ConferenceRoom conferenceRoom4 = ConferenceRoom.builder().name("room four").status(Status.CLOSED).location("calle 4").maxCapacity(9).build();
            LOGGER.info("Preloading {}", conferenceRoomRepository.save(conferenceRoom1));
            LOGGER.info("Preloading {}", conferenceRoomRepository.save(conferenceRoom2));
            LOGGER.info("Preloading {}", conferenceRoomRepository.save(conferenceRoom3));
            LOGGER.info("Preloading {}", conferenceRoomRepository.save(conferenceRoom4));

            LocalDateTime localDateTime = LocalDateTime.parse("2024-10-03T12:00:00");

            Conference conference1 = Conference.builder().name("conference one").startConference(localDateTime.minusHours(4)).endConference(localDateTime.minusHours(3)).conferenceRoom(conferenceRoom1).build();
            Conference conference2 = Conference.builder().name("conference two").startConference(localDateTime.plusHours(3)).endConference(localDateTime.plusHours(4)).conferenceRoom(conferenceRoom2).build();
            Conference conference3 = Conference.builder().name("conference three").startConference(localDateTime.plusHours(5)).endConference(localDateTime.plusHours(6)).conferenceRoom(conferenceRoom3).build();
            Conference conference4 = Conference.builder().name("conference four").startConference(localDateTime.plusHours(1)).endConference(localDateTime.plusMinutes(90)).conferenceRoom(conferenceRoom4).build();
            LOGGER.info("Preloading {}", conferenceRepository.save(conference1));
            LOGGER.info("Preloading {}", conferenceRepository.save(conference2));
            LOGGER.info("Preloading {}", conferenceRepository.save(conference3));
            LOGGER.info("Preloading {}", conferenceRepository.save(conference4));

        };
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public Server h2Server() throws SQLException {
        return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
    }

    public static void main(String[] args) {
        SpringApplication.run(ConferencePlatformApplication.class, args);
    }
}
