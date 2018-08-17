package net.adsService.form;

import lombok.*;
import net.adsService.model.CategoryAttribute;
import org.springframework.validation.FieldError;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdResponseForm {

    List<FieldError> errors;

    List<CategoryAttribute> attributes;

    boolean success;
}
