package examplekafka.config;

import examplekafka.dto.Message;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;

public interface Streams {

    String INPUT = "streams-in";
    String DEAD_OUT = "dead-out";

    @Input(INPUT)
    KStream<String, Message> inboundMessages();

    @Input(DEAD_OUT)
    KStream<String, Message> deadMessages();
}
