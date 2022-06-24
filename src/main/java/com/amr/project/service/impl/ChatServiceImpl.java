package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.ChatDao;
import com.amr.project.dao.abstracts.ReadWriteDao;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChatServiceImpl extends ReadWriteServiceImpl<Chat, Long> implements ChatService {

    private ChatDao chatDao;

    @Autowired
    public void setChatDao(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    public ChatServiceImpl(ReadWriteDao<Chat, Long> dao) {
        super(dao);
    }

    @Override
    @Transactional(readOnly = true)
    public Chat findChatBySenderAndRecipient(User sender, User recipient) {
        return chatDao.findChatBySenderAndRecipient(sender, recipient);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsChatBySenderAndRecipient(User sender, User recipient) {
        return chatDao.existsChatBySenderAndRecipient(sender, recipient);
    }

    @Override
    @Transactional(readOnly = true)
    public Chat findChatBySenderAndRecipient(Long senderId, Long recipientId) {
        return chatDao.findChatBySenderAndRecipient(senderId, recipientId);
    }
}
