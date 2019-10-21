package examplekafka.listener;

import examplekafka.config.Streams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StreamsListener {

    @StreamListener(Streams.INPUT)
    public void listen(@Payload String message, @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
        log.info("Received message from {} with partition {} : {}", Streams.INPUT, partition, message);
    }

    @StreamListener(Streams.DEAD_OUT)
    public void handleDeadMessages(@Payload String message) {
        log.info("Received message from {} : {}", Streams.DEAD_OUT, message);
    }

}
