package tsekhmeistruk.funnycats.models.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Roman Tsekhmeistruk on 17.01.2017.
 */

@Root(name = "images")
public class ImageList {

    @ElementList(name = "images", inline = true, required = false)
    private List<Image> imageList;

    public List<Image> getImageList() {
        return imageList;
    }

    public List<String> getImagesUrls() {
        List<String> imagesUrls = new ArrayList<>();

        for (Image image : getImageList()) {
            imagesUrls.add(image.getUrl());
        }

        return imagesUrls;
    }

}
