package examplekafka.listener;

import examplekafka.config.Streams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@Slf4j
public class StreamsListener {

    @StreamListener(Streams.INPUT)
    public void handleMessages(@Payload String message) throws Exception {
        if (getRandomBoolean()) {
            throw new Exception("Message unable to be processed.");
        }
    }

    public boolean getRandomBoolean() {
        Random random = new Random();
        return random.nextBoolean();
    }
}
