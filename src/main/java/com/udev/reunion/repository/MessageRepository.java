package com.udev.reunion.repository;

import com.udev.reunion.domain.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE id = :i")
    List<Message> findMessageBySenderId(long i);

    @Query("SELECT m FROM Message m ORDER BY publication_date DESC")
    Iterable<Message> findAllOrderByPublicationDateDesc();
}
