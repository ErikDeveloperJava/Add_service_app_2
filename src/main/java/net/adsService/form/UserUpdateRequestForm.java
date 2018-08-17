package net.adsService.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateRequestForm {

    @Length(min = 4,max = 255)
    private String name;

    @Length(min = 4,max = 255)
    private String surname;

    @Length(min = 4,max = 255)
    private String username;

    @Length(min = 4,max = 255)
    private String newPassword;

    @Length(min = 4,max = 255)
    private String oldPassword;

    @Length(min = 8,max = 9)
    private String telephone;
}
