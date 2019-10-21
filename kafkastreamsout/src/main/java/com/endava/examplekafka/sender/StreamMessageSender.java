package com.endava.examplekafka.sender;

import com.endava.examplekafka.dto.Message;
import com.endava.examplekafka.service.StreamsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Log4j2
@EnableScheduling
public class StreamMessageSender {

    private static final int PAUSE_BETWEEN_SENDING = 1000;
    private static final List<String> USERS = Arrays.asList("Steve", "Bill", "Jeff", "Elon", "Tim", "Mark", "Larry",
            "Sergey", "Jack", "Larry");
    private static final List<Integer> PARTITION_KEYS = Arrays.asList(0, 1);

    private final StreamsService streamsService;

    @Scheduled(fixedRate = PAUSE_BETWEEN_SENDING)
    public void sendMessage() {
        Message message = generateRandomMessage();
        streamsService.sendMessage(message, getRandomElement(PARTITION_KEYS));
    }

    private <T> T getRandomElement(List<T> elements) {
        Random rand = new Random();
        return elements.get(rand.nextInt(elements.size()));
    }

    private Message generateRandomMessage() {
        Random random = new Random();

        Message message = new Message();
        message.setSender(getRandomElement(USERS));
        message.setReceiver(getRandomElement(USERS));
        message.setBody(generateRandomString(random, 64));
        message.setSubject(generateRandomString(random, 16));
        message.setTimestamp(new Date());

        return message;
    }

    private static String generateRandomString(Random random, int length) {
        return random.ints(48, 122)
                .filter(i -> (i < 57 || i > 65) && (i < 90 || i > 97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
}
