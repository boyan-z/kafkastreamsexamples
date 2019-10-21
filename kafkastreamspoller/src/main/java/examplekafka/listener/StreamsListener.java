package examplekafka.listener;

import examplekafka.config.Streams;
import examplekafka.dto.Message;
import org.springframework.core.ParameterizedTypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
@EnableScheduling
public class StreamsListener {

    @Autowired
    private PollableMessageSource inboundMessages;

    @Scheduled(fixedDelay = 10000)
    public void listen() {
        inboundMessages.poll( v -> {
            log.info("Received message from {} : {}", Streams.INPUT, v.getPayload().toString());
        }, new ParameterizedTypeReference<Message>() {});
    }
}
