package net.adsService.pages;

import lombok.*;
import net.adsService.model.User;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMainPage {

    private List<User> users;

    private int pages[];
}
