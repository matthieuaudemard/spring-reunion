package com.udev.reunion.util;

import com.udev.reunion.domain.Message;
import com.udev.reunion.domain.User;
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

}
