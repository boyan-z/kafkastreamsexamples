package com.endava.examplekafka.config;

import com.endava.examplekafka.dto.Message;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface Streams {

    String MESSAGES_IN = "streams-in";
    String MESSAGES_OUT_1 = "streams-out-1";
    String MESSAGES_OUT_2 = "streams-out-2";
    String MESSAGES_OUT_3 = "streams-out-3";

    @Input(MESSAGES_IN)
    KStream<String, Message> inputMessages();

    @Output(MESSAGES_OUT_1)
    KStream<String, Message> out1();

    @Output(MESSAGES_OUT_2)
    KStream<String, Message> out2();

    @Output(MESSAGES_OUT_3)
    KStream<String, Message> out3();
}