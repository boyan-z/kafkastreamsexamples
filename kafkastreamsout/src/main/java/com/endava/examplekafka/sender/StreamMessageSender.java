package com.endava.examplekafka.sender;

import com.endava.examplekafka.dto.Message;
import com.endava.examplekafka.service.StreamsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Log4j2
public class StreamMessageSender implements ApplicationListener<ContextRefreshedEvent> {

    private static final int PAUSE_BETWEEN_SENDING = 10000;
    private static final List<String> users = Arrays.asList("Steve", "Bill", "Jeff", "Elon", "Tim", "Mark", "Larry",
            "Sergey", "Jack", "Larry");

    private final StreamsService streamsService;

    private final TaskExecutor taskExecutor;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        taskExecutor.execute(() -> {
            log.info("Started sending messages.");
            while (true) {
                Message message = generateRandomMessage();
                streamsService.sendMessage(message);
                log.info("Sent message \"{}\"", message.toString());
                try {
                    Thread.sleep(PAUSE_BETWEEN_SENDING);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            }
        });
    }

    private Message generateRandomMessage() {
        Random random = new Random();

        Message message = new Message();
        message.setSender(getRandomUser(random));
        message.setReceiver(getRandomUser(random));
        message.setBody(generateRandomString(random, 64));
        message.setSubject(generateRandomString(random, 16));
        message.setTimestamp(Instant.now());

        return message;
    }

    private String getRandomUser(Random random) {
        return users.get(random.nextInt(users.size()));
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