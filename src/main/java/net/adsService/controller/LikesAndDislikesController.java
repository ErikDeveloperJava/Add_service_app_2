package net.adsService.controller;

import net.adsService.model.User;
import net.adsService.service.LikesAndDislikesService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
public class LikesAndDislikesController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private LikesAndDislikesService likesAndDislikesService;

    @PostMapping("/like")
    public String likes(@RequestAttribute("user")User user,
                        @RequestParam("adId")int adId){
        LOGGER.debug("adId : {}",adId);
        return likesAndDislikesService.addLike(user,adId);
    }

    @PostMapping("/dislike")
    public String dislikes(@RequestAttribute("user")User user,
                           @RequestParam("adId")int adId){
        LOGGER.debug("adId : {}",adId);
        return likesAndDislikesService.addDislikes(user,adId);
    }

    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response){
        LOGGER.error(e.getMessage(),e);
        response.setStatus(500);
    }
}