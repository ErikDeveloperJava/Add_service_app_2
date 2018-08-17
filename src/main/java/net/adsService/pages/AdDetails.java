package net.adsService.pages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.adsService.model.Ad;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdDetails {

    private Ad ad;

    private boolean like;

    private boolean dislike;

    private int likes;

    private int dislikes;
}
