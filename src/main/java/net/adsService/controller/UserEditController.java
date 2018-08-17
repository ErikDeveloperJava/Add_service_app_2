package net.adsService.controller;

import net.adsService.form.UserUpdateRequestForm;
import net.adsService.form.UserUpdateResponseForm;
import net.adsService.model.User;
import net.adsService.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Controller
public class UserEditController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Environment env;

    @Autowired
    private UserService userService;

    @PostMapping("/user/update")
    public @ResponseBody
    UserUpdateResponseForm update(@Valid UserUpdateRequestForm form,
                                         BindingResult result, Locale locale,
                                         @RequestAttribute("user")User user){
        String lang = locale.getLanguage();
        LOGGER.debug("userUpdateRequestForm : {},lang : {}",form,lang);
        if (result.hasErrors()){
            List<FieldError> errors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                FieldError fieldError =
                        new FieldError("user",error.getField(),
                                env.getProperty(error.getField() + ".error." + lang));
                errors.add(fieldError);
            }
            return UserUpdateResponseForm.builder()
                    .errors(errors)
                    .build();
        }else if(!user.getUsername().equals(form.getUsername()) && userService.existsByUsername(form.getUsername())){
            List<FieldError> errors = new ArrayList<>();
            FieldError fieldError =
                    new FieldError("user","username",
                            env.getProperty("username.unique.error." + lang));
            errors.add(fieldError);
            return UserUpdateResponseForm.builder()
                    .errors(errors)
                    .build();
        }else if(!passwordEncoder.matches(form.getOldPassword(),user.getPassword())){
            List<FieldError> errors = new ArrayList<>();
            FieldError fieldError =
                    new FieldError("user","oldPassword",
                            env.getProperty("old.password.error." + lang));
            errors.add(fieldError);
            return UserUpdateResponseForm.builder()
                    .errors(errors)
                    .build();
        }else {
            user.setName(form.getName());
            user.setSurname(form.getSurname());
            user.setUsername(form.getUsername());
            user.setPassword(form.getNewPassword());
            user.setTelephone(form.getTelephone());
            userService.update(user);
            return UserUpdateResponseForm.builder()
                    .success(true)
                    .successMsg(env.getProperty("user.update.success." + lang))
                    .build();
        }
    }

    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response){
        LOGGER.error(e.getMessage(),e);
        response.setStatus(500);
    }

    @PostMapping("/user/account/delete")
    public String delete(@RequestParam("userId")int id, HttpSession session){
        LOGGER.debug("userId : {}",id);
        userService.deleteById(id);
        session.invalidate();
        return "redirect:/";
    }
}
