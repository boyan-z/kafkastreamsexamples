package examplekafka.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Streams {

    String INPUT = "streams-in";

    @Input(INPUT)
    SubscribableChannel inboundMessages();
}