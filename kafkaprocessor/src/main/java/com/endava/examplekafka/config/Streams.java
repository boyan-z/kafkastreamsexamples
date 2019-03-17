package com.endava.examplekafka.config;

import com.endava.examplekafka.dto.Message;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Windowed;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface Streams {

    String MESSAGES_IN = "streams-in";
    String MESSAGES_OUT = "streams-out";
    String MESSAGES_COUNT = "streams-count";
    String MAT = "mat";
    String MESSAGE_MIRROR = "streams-mirror";

    @Input(MESSAGES_IN)
    KStream<String, Message> inputMessages();

//    @Output(MESSAGE_MIRROR)
//    KStream<String, Message> mirrorMessages();

    @Output(MESSAGES_COUNT)
    KStream<String, Long> messagesCount();

}