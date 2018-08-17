package net.adsService.pages;

import lombok.*;
import net.adsService.model.Ad;
import net.adsService.model.Category;
import net.adsService.model.Region;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MainPage {

    private List<Region> regions;

    private List<Category> categories;

    private List<AdDetails> adDetails;

    private List<Ad> popularAds;

    private int[] pages;
}
