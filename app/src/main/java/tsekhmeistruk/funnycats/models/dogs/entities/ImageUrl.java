package tsekhmeistruk.funnycats.models.dogs.entities;

import java.util.ArrayList;
import java.util.List;

import tsekhmeistruk.funnycats.models.PictureEntity;
import tsekhmeistruk.funnycats.models.cats.entities.Image;

/**
 * Created by fbrsw on 31.10.2017.
 */

public class ImageUrl implements PictureEntity {

    private String url;

    private ImageUrl(String url) {
        this.url = url;
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getId() {
        return url.substring(url.lastIndexOf('_'), url.lastIndexOf('.'));
    }

    @Override
    public String getSourceUrl() {
        return url;
    }

    public static List<ImageUrl> convertStringsToImageUrls(List<String> strings) {
        if (strings == null) return null;
        List<ImageUrl> imageUrls = new ArrayList<ImageUrl>();
        for (String s : strings) {
            imageUrls.add(new ImageUrl(s));
        }
        return imageUrls;
    }
}
