package com.endava.examplekafka.processor;

import com.endava.examplekafka.config.Streams;
import com.endava.examplekafka.dto.Message;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class AddTimestampProcessor {

    @StreamListener(Streams.MESSAGES_IN)
    @SendTo(Streams.MESSAGES_COUNT)
    public KStream<String, Long> process(KStream<String, Message> input) {
        KStream<String, Long> output = input
                .filterNot((key, value) -> value.getReceiver().equals("Mark"))
                .map((key, value) -> new KeyValue<>(value.getSender(), "0"))
                .groupByKey()
                .count(Materialized.as(Streams.MAT))
                .toStream();

        return output;
    }
}
