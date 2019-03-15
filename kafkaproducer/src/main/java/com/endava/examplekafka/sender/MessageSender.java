package com.endava.examplekafka.sender;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class MessageSender implements ApplicationListener<ContextRefreshedEvent> {

    private static final int PAUSE_BETWEEN_SENDING = 10000;

    @Value(value = "${kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final TaskExecutor taskExecutor;

    private static long counter = 0;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        taskExecutor.execute(() -> {
            log.info("Started sending messages.");
            do {
                String message = String.format("Message id: %s", counter);
                sendMessage(message);
                log.info("Sent message \"{}\"", message);
                counter++;
                try {
                    Thread.sleep(PAUSE_BETWEEN_SENDING);
                } catch (InterruptedException e) {
                    log.error(e);
                }
            } while (counter < Long.MAX_VALUE);
            log.info("Sending messages ended.");
        });
    }

    private void sendMessage(String msg) {
        kafkaTemplate.send(topic, msg);
    }
}