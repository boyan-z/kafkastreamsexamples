package com.endava.examplekafka.config;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface Streams {

    String OUTPUT = "streams-out";

    @Output(OUTPUT)
    MessageChannel outboundStreams();
}