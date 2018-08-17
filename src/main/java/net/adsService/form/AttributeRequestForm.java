package net.adsService.form;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "images")
public class AttributeRequestForm {

    private List<MultipartFile> images;

    private List<String> values;

    private List<Integer> idList;
}
