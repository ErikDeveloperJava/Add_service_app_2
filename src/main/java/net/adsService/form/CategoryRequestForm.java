package net.adsService.form;

import lombok.*;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestForm {

    @Length(min = 2,max = 255)
    private String nameEn;

    @Length(min = 2,max = 255)
    private String nameRu;

    @Length(min = 2,max = 255)
    private String nameArm;

    private int parent;
}
