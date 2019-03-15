package com.endava.examplekafka.processor;

import com.endava.examplekafka.config.MessageProcessingChannels;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor;
import org.springframework.messaging.handler.annotation.SendTo;

@Log4j2
@EnableBinding(KafkaStreamsProcessor.class)
public class AddTimestampProcessor {

    @StreamListener(MessageProcessingChannels.ANALYZE)
    public void process(KStream<Object, String> input) {
        KTable<String, Long> output =  input.map((o, s) -> {
            long id = Long.parseLong(s.replaceAll("\\D+", ""));
            if (id % 2 == 0)
                return new KeyValue<>("odd", 0);
            else
                return new KeyValue<>("even", 0);
        }).groupByKey().count(Materialized.as(MessageProcessingChannels.ANALYZE_TABLE));
    }
}
