package examplekafka.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface Streams {

    String INPUT = "streams-in";
    String DEAD_OUT = "dead-out";

    @Input(INPUT)
    SubscribableChannel inboundMessages();

    @Input(DEAD_OUT)
    SubscribableChannel deadMessages();
}
