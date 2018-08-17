package net.adsService.controller;

import net.adsService.model.Ad;
import net.adsService.model.Comment;
import net.adsService.model.User;
import net.adsService.repository.CommentRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@RestController
public class CommentController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/comment/add")
    public Comment add(@RequestParam("adId")int adId,@RequestParam("comment")String commentText,
                       @RequestAttribute("user")User user){
        LOGGER.debug("adId : {}",adId);
        Comment comment = Comment.builder()
                .comment(commentText)
                .ad(Ad.builder().id(adId).build())
                .sendDate(new Date())
                .user(user)
                .build();
        return commentRepository.save(comment);
    }

    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response){
        LOGGER.error(e.getMessage(),e);
        response.setStatus(500);
    }
}
