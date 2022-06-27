package com.amr.project.webapp.controller;

import com.amr.project.converter.ChatConverter;
import com.amr.project.converter.MessageConverter;
import com.amr.project.model.dto.ChatDto;
import com.amr.project.model.dto.MessageDto;
import com.amr.project.model.entity.Chat;
import com.amr.project.model.entity.ChatNotification;
import com.amr.project.model.entity.Message;
import com.amr.project.service.abstracts.ChatService;
import com.amr.project.service.abstracts.MessageService;
import io.swagger.annotations.ApiOperation;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name="Чат Websocket Контроллер", description="Подключение к WebSocket: /ws , отправить сообщение: /app/chat , получить уведомление: /user/{recipientId}/queue/messages")
public class ChatWsController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageConverter messageConverter;
    private final ChatService chatService;
    private final MessageService messageService;
    private final ChatConverter chatConverter;

    @Autowired
    public ChatWsController(SimpMessagingTemplate simpMessagingTemplate, MessageConverter messageConverter, ChatService chatService, MessageService messageService, ChatConverter chatConverter) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.messageConverter = messageConverter;
        this.chatService = chatService;
        this.messageService = messageService;
        this.chatConverter = chatConverter;
    }

    @MessageMapping("/chat")
    public void message(@Payload MessageDto messageDto) {
        //Конвертировать Dto в Entity
        Message messageEntity = messageConverter.dtoToEntity(messageDto);
        //Добавить сообщение в чат и сохранить в базу данных
        Chat chatOne = null;
        if (!chatService.existsChatBySenderAndRecipient(messageEntity.getSender(), messageEntity.getRecipient())) {
            chatOne = Chat.builder()
                    .sender(messageEntity.getSender())
                    .recipient(messageEntity.getRecipient())
                    .build();
            chatService.persist(chatOne);

            Chat chatTwo = Chat.builder()
                    .sender(messageEntity.getRecipient())
                    .recipient(messageEntity.getSender())
                    .build();
            chatService.persist(chatTwo);

            chatOne = chatService.findChatBySenderAndRecipient(messageEntity.getSender(), messageEntity.getRecipient());
            messageEntity.setChat(chatOne);
            messageService.persist(messageEntity);

            chatTwo = chatService.findChatBySenderAndRecipient(messageEntity.getRecipient(), messageEntity.getSender());
            Message message = Message.builder()
                    .date(messageEntity.getDate())
                    .textMessage(messageEntity.getTextMessage())
                    .sender(messageEntity.getSender())
                    .recipient(messageEntity.getRecipient())
                    .chat(chatTwo)
                    .build();
            messageService.persist(message);
        } else {
            chatOne = chatService.findChatBySenderAndRecipient(messageEntity.getSender(), messageEntity.getRecipient());
            messageEntity.setChat(chatOne);
            messageService.persist(messageEntity);

            Chat chatTwo = chatService.findChatBySenderAndRecipient(messageEntity.getRecipient(), messageEntity.getSender());
            Message message = Message.builder()
                    .date(messageEntity.getDate())
                    .textMessage(messageEntity.getTextMessage())
                    .sender(messageEntity.getSender())
                    .recipient(messageEntity.getRecipient())
                    .chat(chatTwo)
                    .build();
            messageService.persist(message);
        }

        //Отправить уведомление адресату
        simpMessagingTemplate.convertAndSendToUser(messageDto.getToUserId().toString(),"/queue/messages",
                ChatNotification.builder()
                        .id(chatOne.getId())
                        .senderId(chatOne.getSender().getId())
                        .senderName(chatOne.getSender().getUsername())
                        .build());
    }

    @ApiOperation(value = "Получить чат по отправителю и получателю")
    @GetMapping("/api/chats/{senderId}/{recipientId}")
    public ResponseEntity<ChatDto> chat(@PathVariable Long senderId, @PathVariable Long recipientId) {
        return new ResponseEntity<>(chatConverter.entityToDto(chatService.findChatBySenderAndRecipient(senderId, recipientId)), HttpStatus.OK);
    }
}
