package net.adsService.form;

import lombok.*;
import net.adsService.model.Category;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdRequestForm {

    @Length(min = 2,max = 255)
    private String title;

    @Length(min = 10)
    private String description;

    @Length(min = 3)
    private String price;

    private Category category;
}
