package com.endava.examplekafka.processor;

import com.endava.examplekafka.config.MessageProcessingChannels;
import com.endava.examplekafka.dto.Message;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.kstream.Windowed;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

import java.time.Instant;

@Log4j2
@EnableBinding(KafkaStreamsProcessor.class)
public class AddTimestampProcessor {

    @StreamListener(MessageProcessingChannels.MESSAGES_IN)
    //@SendTo(MessageProcessingChannels.MESSAGES_OUT)
    public /*KStream<String, Long>*/ void process(KStream<String, Message> input) {
        KTable<Windowed<String>, Long> table = input
                //.through(MessageProcessingChannels.ANALYZE)
                .filterNot((key, value) -> value.getReceiver().equals("Mark"))
                .map((key, value) -> new KeyValue<>(value.getSender(), "0"))
                .groupByKey()
                .windowedBy(TimeWindows.of(1000 * 60 * 60))
                .count(Materialized.as(MessageProcessingChannels.MAT));

    }
}
