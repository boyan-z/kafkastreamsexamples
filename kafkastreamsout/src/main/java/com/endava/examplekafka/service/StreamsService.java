package com.endava.examplekafka.service;

import com.endava.examplekafka.config.Streams;
import com.endava.examplekafka.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamsService {

    private final Streams streams;

    public void sendMessage(final Message message) {
        log.info("Sending message: {}", message.toString());
        MessageChannel messageChannel = streams.outboundStreams();
        messageChannel.send(MessageBuilder
                .withPayload(message)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }
}