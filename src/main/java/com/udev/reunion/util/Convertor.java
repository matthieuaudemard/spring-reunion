package com.udev.reunion.util;

import com.udev.reunion.domain.Comment;
import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.dto.CommentDto;
import com.udev.reunion.dto.MessageDto;
import com.udev.reunion.dto.UserDto;

public class Convertor {

    private Convertor() {
        // Empeche la création d'une instance de la classe Convertor
    }

    public static MessageDto convertToDto(Message message) {
        final User sender = message.getSender();
        MessageDto json = new MessageDto();
        json.setSender(Convertor.convertToDto(sender));
        json.setMessageId(message.getId());
        json.setMessageTitle(message.getTitle());
        json.setMessageBody(message.getBody());
        json.setPublicationDate(message.getPublicationDate());
        return json;
    }

    public static CommentDto convertToDto(Comment comment) {
        final User sender = comment.getSender();
        final Message message = comment.getMessage();
        CommentDto json = new CommentDto();
        json.setSender(Convertor.convertToDto(sender));
        json.setMessageId(message.getId());
        json.setCommentId(comment.getId());
        json.setCommentBody(comment.getBody());
        json.setCommentDate(comment.getCommentDate());
        return json;
    }

    public static UserDto convertToDto(User user) {
        UserDto json = new UserDto();
        json.setId(user.getId());
        json.setLogin(user.getLogin());
        json.setFirstname(user.getFirstname());
        json.setLastname(user.getLastname());
        json.setAbout(user.getAbout());
        return json;
    }

}
