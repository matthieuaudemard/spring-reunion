package com.udev.reunion.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MessageDto {

    private UserDto sender;
    private Long messageId;
    private String messageTitle;
    private String messageBody;
    private Date publicationDate;
    private List<CommentDto> commentJsonList;

    public MessageDto() {
        // Constructeur par défaut
    }
}
