package examplekafka.listener;

import examplekafka.config.Streams;
import examplekafka.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@EnableBinding(Streams.class)
@Component
@Slf4j
public class StreamsListener {

    @StreamListener
    public void mergeMessages(@Input(Streams.INPUT) KStream<String, Message> defaultMessages,
                              @Input(Streams.DEAD_OUT) KStream<String, Message> extraMessages) {
        defaultMessages.merge(extraMessages).foreach((k, v) -> log.info("key {}; value {}", k, v));
    }
}
