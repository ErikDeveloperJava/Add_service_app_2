package net.adsService.service.impl;

import net.adsService.form.MessageResponseForm;
import net.adsService.model.Message;
import net.adsService.model.MessageStatus;
import net.adsService.model.User;
import net.adsService.repository.MessageRepository;
import net.adsService.repository.UserRepository;
import net.adsService.service.MessageService;
import net.adsService.util.MessageResponseFormComparatorImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public int countByToIdAndStatus(int toId, MessageStatus status) {
        return messageRepository.countByToIdAndStatus(toId,status);
    }

    public List<MessageResponseForm> getResponseForm(int userId) {
        List<MessageResponseForm> messageForms = new ArrayList<>();
        List<User> users = userRepository.findAllUsersWhereExistsMessages(userId);
        for (User user : users) {
            MessageResponseForm form = MessageResponseForm.builder()
                    .user(user)
                    .count(messageRepository.countByFromIdAndToIdAndStatus(user.getId(),userId,MessageStatus.NEW))
                    .build();
            messageForms.add(form);
        }
        messageForms.sort(new MessageResponseFormComparatorImpl());
        return messageForms;
    }

    @Override
    public Message add(Message message) {
        message = messageRepository.save(message);
        LOGGER.debug("message saved");
        return message;
    }

    @Transactional(readOnly = true)
    public List<Message> getAll(int toId, int fromId) {
        return messageRepository.findAllMessages(toId,fromId);
    }

    @Override
    public void updateStatus(int toId, int fromId) {
        List<Message> allMessages = messageRepository.findAllMessages(toId, fromId);
        for (Message msg : allMessages) {
            if(msg.getTo().getId() == toId){
                msg.setStatus(MessageStatus.VIEWED);
            }
        }
    }


}
