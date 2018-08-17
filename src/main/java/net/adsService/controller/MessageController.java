package net.adsService.controller;

import net.adsService.form.MessageResponseForm;
import net.adsService.model.Message;
import net.adsService.model.MessageStatus;
import net.adsService.model.User;
import net.adsService.service.MessageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/message")
public class MessageController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private MessageService messageService;

    @PostMapping("/counts")
    public @ResponseBody
    int loadNewMessagesCount(@RequestAttribute("user")User user){
        return messageService.countByToIdAndStatus(user.getId(),MessageStatus.NEW);
    }

    @PostMapping("/users")
    public @ResponseBody
    List<MessageResponseForm> loadAllUsers(@RequestAttribute("user")User user){
        return messageService.getResponseForm(user.getId());
    }

    @PostMapping("/send")
    public @ResponseBody
    String send(@RequestParam("message")String messageText,
                @RequestParam("to.id")int toId,
                @RequestAttribute("user")User user){
        LOGGER.debug("message : {},toId : {}",messageText,toId);
        Message message = Message.builder()
                .message(messageText)
                .sendDate(new Date())
                .status(MessageStatus.NEW)
                .from(user)
                .to(User.builder().id(toId).build())
                .build();
        messageService.add(message);
        return "success";
    }

    @PostMapping("/all")
    public @ResponseBody
    List<Message> loadAll(@RequestParam("userId")int userId,@RequestAttribute("user")User user){
        LOGGER.debug("userId : {}",userId);
        return messageService.getAll(userId,user.getId());
    }

    @PostMapping("/update/status")
    public @ResponseBody
    String updateStatus(@RequestParam("userId")int userId,@RequestAttribute("user")User user){
        LOGGER.debug("userId : {}",userId);
        messageService.updateStatus(user.getId(),userId);
        return "success";
    }

    @PostMapping("/send2")
    public @ResponseBody
    Message send2(@RequestParam("message")String messageText,
                  @RequestParam("userId")int toId,
                  @RequestAttribute("user")User user){
        LOGGER.debug("message : {},toId : {}",messageText,toId);
        Message message = Message.builder()
                .message(messageText)
                .sendDate(new Date())
                .status(MessageStatus.NEW)
                .from(user)
                .to(User.builder().id(toId).build())
                .build();
        return messageService.add(message);
    }

    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response){
        LOGGER.error(e.getMessage(),e);
        response.setStatus(500);
    }
}
