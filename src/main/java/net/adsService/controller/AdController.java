package net.adsService.controller;

import net.adsService.model.User;
import net.adsService.service.AdService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private AdService adService;

    @GetMapping("/ad/{id}")
    public String findAd(@PathVariable("id")String strId, @RequestAttribute("user")User user,
                         Model model){
        LOGGER.debug("adId : {}",strId);
        int id = parseToInteger(strId);
        if(id == -1 || !adService.existById(id)){
            return "redirect:/";
        }
        model.addAttribute("adPage",adService.getById(user,id));
        return "ad";
    }

    @PostMapping("/ad/delete")
    public String delete(@RequestParam("adId")int adId){
        LOGGER.debug("adId : {}",adId);
        adService.deleteById(adId);
        return "redirect:/";
    }

    private int parseToInteger(String strId){
        int id;
        try {
            id = Integer.parseInt(strId);
            if(id <= 0){
                id = -1;
            }
        }catch (NumberFormatException e){
            id = -1;
        }
        return id;
    }
}
