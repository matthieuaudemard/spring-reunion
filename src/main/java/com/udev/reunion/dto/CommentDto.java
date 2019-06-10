package com.udev.reunion.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    private UserDto sender;
    private Long messageId;
    private Long commentId;
    private String commentBody;
    private Date commentDate;

    public CommentDto() {
        // Constructeur par défaut
    }
}
