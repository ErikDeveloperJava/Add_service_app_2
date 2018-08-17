package net.adsService.form;

import lombok.*;
import net.adsService.model.City;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "image")
public class UserRequestForm {

    @Length(min = 4,max = 255,message = "in field name invalid data")
    private String name;

    @Length(min = 4,max = 255,message = "in field surname invalid data")
    private String surname;

    @Length(min = 4,max = 255,message = "in field username invalid data")
    private String username;

    @Length(min = 4,max = 255,message = "in field password invalid data")
    private String password;

    @Length(min = 8,max = 9,message = "in field telephone invalid data")
    private String telephone;

    private City city;

    private MultipartFile image;
}
