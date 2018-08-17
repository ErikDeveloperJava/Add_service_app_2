package net.adsService.form;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttributeResponseForm {

    private String imageError;

    private boolean success;

    private int adId;
}
