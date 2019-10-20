package com.endava.examplekafka.service;

import com.endava.examplekafka.config.Streams;
import com.endava.examplekafka.dto.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StreamsService {

    private final Streams streams;

    public void sendMessage(final Message message, final String partition) {
        log.info("Sending message: {}, for partition {}", message.toString(), partition);
        MessageChannel messageChannel = streams.outboundStreams();
        messageChannel.send(MessageBuilder
                .withPayload(message)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .setHeader(KafkaHeaders.MESSAGE_KEY, generateId())
                .setHeader("partitionKey", partition)
                .build());
    }

    private byte[] generateId() {
        return UUID.randomUUID().toString().getBytes();
    }
}