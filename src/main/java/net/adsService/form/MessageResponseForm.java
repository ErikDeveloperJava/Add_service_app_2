package net.adsService.form;

import lombok.*;
import net.adsService.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageResponseForm {

    private User user;

    private int count;
}