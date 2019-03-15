package com.endava.examplekafka.config;

import com.endava.examplekafka.dto.Message;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface MessageProcessingChannels {

    String MESSAGES_IN = "input";
    String MESSAGES_OUT = "output";
    String ANALYZE = "analytics";
    String REPORTING = "reporting";

    @Input(MESSAGES_IN)
    KStream<String, Message> inputMessages();

    @Output(MESSAGES_OUT)
    KStream<String, Message> outputMessages();

    @Output
    KTable<String, Long> analytics();
}