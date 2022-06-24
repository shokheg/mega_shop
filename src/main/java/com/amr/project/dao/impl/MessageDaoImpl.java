package com.amr.project.dao.impl;

import com.amr.project.dao.Util;
import com.amr.project.dao.abstracts.MessageDao;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class MessageDaoImpl extends ReadWriteDaoImpl<Message, Long> implements MessageDao {

    @Override
    public Message findMessageBySenderAndRecipient(User sender, User recipient) {
        return Util.result(em.createQuery("select m from Message m where m.sender = :sender and m.recipient = :recipient", Message.class).setParameter("sender", sender).setParameter("recipient", recipient));
    }
}
