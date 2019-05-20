package com.udev.reunion.model;

import lombok.Data;

import java.util.Date;

@Data
public class MessageJson {

    private Long senderId;
    private String senderFirstname;
    private String senderLastname;
    private Long messageId;
    private String messageTitle;
    private String messageBody;
    private Date publicationDate;

    public MessageJson() {
        // Constructeur par défaut
    }
}
