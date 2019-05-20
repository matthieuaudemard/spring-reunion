package com.udev.reunion.repository;

import com.udev.reunion.domain.Message;
import org.springframework.data.repository.CrudRepository;

public interface MessageRepository extends CrudRepository<Message, Long> {
}
