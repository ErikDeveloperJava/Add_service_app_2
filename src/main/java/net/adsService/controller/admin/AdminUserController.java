package net.adsService.controller.admin;

import net.adsService.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AdminUserController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/delete")
    public boolean delete(@RequestParam("id") int id) {
        LOGGER.debug("userId : {}",id);
        userRepository.deleteById(id);
        return true;
    }
}
