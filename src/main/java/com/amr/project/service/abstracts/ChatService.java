package com.amr.project.service.abstracts;

import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.User;

public interface ChatService extends ReadWriteService<Chat, Long> {

    Chat findChatBySenderAndRecipient(User sender, User recipient);

    boolean existsChatBySenderAndRecipient(User sender, User recipient);

    Chat findChatBySenderAndRecipient(Long senderId, Long recipientId);
}
