package net.adsService.controller;

import net.adsService.form.AdRequestForm;
import net.adsService.form.AdResponseForm;
import net.adsService.form.AttributeRequestForm;
import net.adsService.form.AttributeResponseForm;
import net.adsService.model.*;
import net.adsService.service.AdService;
import net.adsService.service.AttributesService;
import net.adsService.util.ImageUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/ad/add")
public class CreateAdController {

    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private Environment env;

    @Autowired
    private AttributesService attributesService;

    @Autowired
    private ImageUtil imageUtil;

    @Autowired
    private AdService adService;

    @PostMapping("/1")
    public AdResponseForm addPart1(@Valid AdRequestForm form, BindingResult result,
                            Locale locale, HttpSession session) {
        String lang = locale.getLanguage();
        LOGGER.debug("adRequestForm : {},field error counts : {},lang : {}", form, result.getFieldErrorCount(),
                lang);
        double price;
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = new ArrayList<>();
            for (FieldError error : result.getFieldErrors()) {
                FieldError fieldError =
                        new FieldError("ad", error.getField(),
                                env.getProperty(error.getField() + ".error." + lang));
                fieldErrors.add(fieldError);
            }
            return AdResponseForm.builder()
                    .errors(fieldErrors)
                    .build();
        } else if ((price = validPrice(form.getPrice())) == -1.0) {
            List<FieldError> fieldErrors = new ArrayList<>();
            FieldError fieldError =
                    new FieldError("ad", "price",
                            env.getProperty("price.error." + lang));
            fieldErrors.add(fieldError);
            return AdResponseForm.builder()
                    .errors(fieldErrors)
                    .build();
        } else {
            Ad ad = Ad.builder()
                    .title(form.getTitle())
                    .description(form.getDescription())
                    .price(price)
                    .category(form.getCategory())
                    .build();
            session.setAttribute("ad", ad);
            LOGGER.info("{}, saved in session attribute", ad);
            return AdResponseForm.builder()
                    .success(true)
                    .attributes(attributesService.getByCategoryId(form.getCategory().getId()))
                    .build();
        }
    }

    private double validPrice(String priceStr) {
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            price = -1.0;
        }
        if (price < 100) {
            price = -1.0;
        }
        return price;
    }

    @PostMapping("/2")
    public AttributeResponseForm addPart2(AttributeRequestForm form, HttpSession session,
                                          @RequestAttribute("user")User user,Locale locale){
        String lang = locale.getLanguage();
        LOGGER.debug("attributeResponseForm : {}, lang : {}",form,lang);
        Ad ad = (Ad) session.getAttribute("ad");
        List<MultipartFile> imageList;
        if(ad == null){
            return AttributeResponseForm.builder()
                    .build();
        }else if((imageList = checkImages(form.getImages())).size() == 0){
            return AttributeResponseForm.builder()
                    .imageError(env.getProperty("image.error." + lang))
                    .build();
        }else {
            ad.setCreatedDate(new Date());
            ad.setUser(user);
            ad.setImage(" ");
            List<AttributeAd> attributes = new ArrayList<>();
            if(form.getValues() != null && form.getIdList() != null) {
                attributes = getAttributes(form.getValues(), form.getIdList());
            }
            int id = adService.create(ad,attributes,imageList);
            return AttributeResponseForm.builder()
                    .success(true)
                    .adId(id)
                    .build();
        }
    }

    private List<AttributeAd> getAttributes(List<String> values, List<Integer> idList){
        List<AttributeAd> attributes = new ArrayList<>();
        for (int i = 0; i < values.size(); i++) {
            String value = values.get(i);
            CategoryAttribute attribute = CategoryAttribute.builder()
                    .id(idList.get(i))
                    .build();
            AttributeAd attributeAd = AttributeAd.builder()
                    .value(value.length() == 0 ? "*******" : value)
                    .categoryAttribute(attribute)
                    .build();
            attributes.add(attributeAd);
        }
        return attributes;
    }

    private List<MultipartFile> checkImages(List<MultipartFile> images){
        List<MultipartFile> imageList = new ArrayList<>();
        for (MultipartFile image : images) {
            if(!image.isEmpty() && imageUtil.isValidFormat(image.getContentType())){
                imageList.add(image);
            }
        }
        return imageList;
    }

    @ExceptionHandler(Exception.class)
    public void exceptionCatch(Exception e, HttpServletResponse response){
        LOGGER.error(e.getMessage(),e);
        response.setStatus(500);
    }
}
