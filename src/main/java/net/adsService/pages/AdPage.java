package net.adsService.pages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.adsService.model.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdPage {

    private Ad ad;

    private boolean like;

    private boolean dislike;

    private int likes;

    private int dislikes;

    private List<Image> images;

    private List<Category> categories;

    private List<Region> regions;

    private List<Comment> comments;

    private List<AttributeAd> attributes;
}
