package net.adsService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class LoginController {

    @GetMapping(value = "/login")
    public @ResponseBody
    String login(@RequestParam(value = "param",required = false,defaultValue = "???")String param,
                 Locale locale){
        if(param.equals("error")){
            if(locale.getLanguage().equals("en")){
                return "invalid username or password";
            }else if(locale.getLanguage().equals("ru")){
                return "неправильное имя пользователя или пароль";
            }else {
                return "սխալ է անունը կամ գաղտնաբառը";
            }
        }else {
            return param;
        }
    }
}