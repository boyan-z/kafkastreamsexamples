package com.endava.examplekafka.processor;

import com.endava.examplekafka.config.Streams;
import com.endava.examplekafka.dto.Message;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Predicate;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class StreamProcessor {

    @StreamListener(Streams.MESSAGES_IN)
    @SendTo({Streams.MESSAGES_OUT_1, Streams.MESSAGES_OUT_2, Streams.MESSAGES_OUT_3})
    public KStream<?, Message>[] process(KStream<String, Message> input) {
        Predicate<String, Message> fromMark = (k, v) -> v.getSender().equalsIgnoreCase("Mark");
        Predicate<String, Message> fromSergey = (k, v) -> v.getSender().equalsIgnoreCase("Sergey");
        Predicate<String, Message> fromLarry = (k, v) -> v.getSender().equalsIgnoreCase("Larry");

        return input.peek((k, v) -> log.info("Received {}.", v.toString())).branch(fromMark, fromSergey, fromLarry);
    }
}
