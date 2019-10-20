package com.endava.examplekafka.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String sender;
    private String receiver;
    private String subject;
    private String body;
    private Date timestamp;
}
