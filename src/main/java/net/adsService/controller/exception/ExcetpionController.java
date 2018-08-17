package net.adsService.controller.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExcetpionController {

    @GetMapping("/error-500")
    public String error500(){
        return "error/500";
    }
}
