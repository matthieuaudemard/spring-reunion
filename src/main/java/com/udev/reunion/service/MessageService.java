package com.udev.reunion.service;

import com.udev.reunion.domain.Message;
import com.udev.reunion.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public List<Message> getLastMessages() {
        return (List<Message>) messageRepository.findAll();
    }

    public Message send(Message message) {
        return messageRepository.save(message);
    }
}
