package com.udev.reunion.model;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MessageJson {

    private UserJson sender;
    private Long messageId;
    private String messageTitle;
    private String messageBody;
    private Date publicationDate;
    private List<CommentJson> commentJsonList;

    public MessageJson() {
        // Constructeur par défaut
    }
}
