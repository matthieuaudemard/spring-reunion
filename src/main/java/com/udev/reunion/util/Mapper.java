package com.udev.reunion.util;

import com.udev.reunion.domain.Comment;
import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.CommentJson;
import com.udev.reunion.model.MessageJson;

public class Mapper {

    private Mapper() {
        // Empeche la création d'une instance de la classe Mapper
    }

    public static MessageJson convert(Message message) {
        final User sender = message.getSender();
        MessageJson json = new MessageJson();
        json.setSenderId(sender.getId());
        json.setSenderFirstname(sender.getFirstname());
        json.setSenderLastname(sender.getLastname());
        json.setMessageId(message.getId());
        json.setMessageTitle(message.getTitle());
        json.setMessageBody(message.getBody());
        json.setPublicationDate(message.getPublicationDate());
        return json;
    }

    public static CommentJson convert(Comment comment) {
        final User sender = comment.getSender();
        final Message message = comment.getMessage();
        CommentJson json = new CommentJson();
        json.setSenderId(sender.getId());
        json.setSenderFirstname(sender.getFirstname());
        json.setSenderLastname(sender.getLastname());
        json.setMessageId(message.getId());
        json.setCommentId(comment.getId());
        json.setCommentBody(comment.getBody());
        json.setCommentDate(comment.getCommentDate());
        return json;
    }

}
