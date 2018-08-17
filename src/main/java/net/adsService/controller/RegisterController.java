package net.adsService.controller;

import net.adsService.form.UserRequestForm;
import net.adsService.form.UserResponseForm;
import net.adsService.model.City;
import net.adsService.model.Region;
import net.adsService.repository.CityRepository;
import net.adsService.repository.RegionRepository;
import net.adsService.service.UserService;
import net.adsService.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private Environment env;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private RegionRepository regionRepository;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/cities")
    public @ResponseBody
    List<City> loadCities(@RequestParam("regionId")int regionId){
        LOGGER.debug("regionId : {}",regionId);
        return cityRepository.findAllByRegionId(regionId);
    }

    @PostMapping("/regions")
    public @ResponseBody
    List<Region> loadRegions(){
        return regionRepository.findAll();
    }

    @PostMapping
    public @ResponseBody
    UserResponseForm register(@Valid UserRequestForm form, BindingResult result,
                              Locale locale){
        String lang = locale.getLanguage();
        LOGGER.debug("UserRequestFrom : {},lang : {},field errors count : {}",
                form,lang,result.getFieldErrorCount());
        UserResponseForm response;
        if(result.hasErrors()){
            List<FieldError> errors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                errors.add(new FieldError("user",
                        error.getField(),env.getProperty(error.getField() + ".error." + lang)));
            }
            response = UserResponseForm.builder()
                    .errors(errors)
                    .build();
        }else if(userService.existsByUsername(form.getUsername())){
            FieldError error = new FieldError("user","username",
                    env.getProperty("username.unique.error."  + lang));
            response = UserResponseForm.builder()
                    .errors(Collections.singletonList(error))
                    .build();
        }else if(form.getImage().isEmpty()){
            FieldError fieldError = new FieldError("user","image",
                    env.getProperty("image.empty.error." + lang));
            response = UserResponseForm.builder()
                    .errors(Collections.singletonList(fieldError))
                    .build();
        }else if(!imageUtil.isValidFormat(form.getImage().getContentType())){
            FieldError fieldError = new FieldError("user","image",
                    env.getProperty("image.format.error." + lang));
            response = UserResponseForm.builder()
                    .errors(Collections.singletonList(fieldError))
                    .build();
        }else {
            userService.save(form);
            response = UserResponseForm.builder()
                    .success(true)
                    .message(env.getProperty("success." + lang))
                    .build();
        }
        return response;
    }


    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response){
        LOGGER.error(e.getMessage(),e);
        response.setStatus(500);
    }
}
