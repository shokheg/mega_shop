package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.MessageDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Message;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MessageServiceImpl extends ReadWriteServiceImpl<Message, Long> implements MessageService {

    private MessageDao messageDao;

    @Autowired
    public void setMessageDao(MessageDao messageDao) {
        this.messageDao = messageDao;
    }

    public MessageServiceImpl(ReadWriteDao<Message, Long> dao) {
        super(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public Message findMessageBySenderAndRecipient(User sender, User recipient) {
        return messageDao.findMessageBySenderAndRecipient(sender, recipient);
    }
}
