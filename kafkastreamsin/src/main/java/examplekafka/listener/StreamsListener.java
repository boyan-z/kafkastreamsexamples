package examplekafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StreamsListener {

    @StreamListener()
    public void handleMessages(@Payload String message) {
        log.info("Received message: {}", message);
    }
}
