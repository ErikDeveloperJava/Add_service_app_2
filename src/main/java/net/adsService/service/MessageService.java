package net.adsService.service;

import net.adsService.form.MessageResponseForm;
import net.adsService.model.Message;
import net.adsService.model.MessageStatus;

import java.util.List;

public interface MessageService {

    int countByToIdAndStatus(int toId, MessageStatus status);

    List<MessageResponseForm> getResponseForm(int userId);

    Message add(Message message);

    List<Message> getAll(int toId,int fromId);

    void updateStatus(int toId,int fromId);
}
