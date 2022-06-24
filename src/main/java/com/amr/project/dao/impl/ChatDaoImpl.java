package com.amr.project.dao.impl;

import com.amr.project.dao.Util;
import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class ChatDaoImpl extends ReadWriteDaoImpl<Chat, Long> implements ChatDao {

    @Override
    public Chat findChatBySenderAndRecipient(User sender, User recipient) {
        return Util.result(em.createQuery("select c from Chat c where c.sender = :sender and c.recipient = :recipient", Chat.class).setParameter("sender", sender).setParameter("recipient", recipient));
    }

    @Override
    public boolean existsChatBySenderAndRecipient(User sender, User recipient) {
        return Util.result(em.createQuery("select c from Chat c where c.sender = :sender and c.recipient = :recipient", Chat.class).setParameter("sender", sender).setParameter("recipient", recipient)) != null;
    }

    @Override
    public Chat findChatBySenderAndRecipient(Long senderId, Long recipientId) {
        return Util.result(em.createQuery("select c from Chat c where c.sender.id = :senderId and c.recipient.id = :recipientId", Chat.class)
                .setParameter("senderId", senderId).setParameter("recipientId", recipientId));
    }
}
