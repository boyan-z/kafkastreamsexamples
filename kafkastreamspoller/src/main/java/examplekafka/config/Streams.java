package examplekafka.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.binder.PollableMessageSource;
import org.springframework.messaging.SubscribableChannel;

public interface Streams {

    String INPUT = "streams-in";

    @Input(INPUT)
    PollableMessageSource inboundMessages();
}
