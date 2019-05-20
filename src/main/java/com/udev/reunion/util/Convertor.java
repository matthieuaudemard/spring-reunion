package com.udev.reunion.util;

import com.udev.reunion.domain.Comment;
import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
import com.udev.reunion.model.CommentJson;
import com.udev.reunion.model.MessageJson;
import com.udev.reunion.model.UserJson;

public class Convertor {

    private Convertor() {
        // Empeche la création d'une instance de la classe Convertor
    }

    public static MessageJson convert(Message message) {
        final User sender = message.getSender();
        MessageJson json = new MessageJson();
        json.setSender(Convertor.convert(sender));
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
        json.setSender(Convertor.convert(sender));
        json.setMessageId(message.getId());
        json.setCommentId(comment.getId());
        json.setCommentBody(comment.getBody());
        json.setCommentDate(comment.getCommentDate());
        return json;
    }

    public static UserJson convert(User user) {
        UserJson json = new UserJson();
        json.setId(user.getId());
        json.setLogin(user.getLogin());
        json.setFirstname(user.getFirstname());
        json.setLastname(user.getLastname());
        json.setAbout(user.getAbout());
        return json;
    }

}
