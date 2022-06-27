package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;

public interface MessageDao extends ReadWriteDao<Message, Long> {

    Message findMessageBySenderAndRecipient(User sender, User recipient);
}
