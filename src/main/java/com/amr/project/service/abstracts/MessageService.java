package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;

public interface MessageService extends ReadWriteService<Message, Long> {

    Message findMessageBySenderAndRecipient(User sender, User recipient);
}
