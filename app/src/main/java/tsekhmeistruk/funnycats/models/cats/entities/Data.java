package tsekhmeistruk.funnycats.models.cats.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "data")
public class Data {

    @Element(name = "categories", required = false)
    private CategoryList categoryList;

    @Element(name = "images", required = false)
    private ImageList imageList;

    public CategoryList getCategoryList() {
        return categoryList;
    }

    public ImageList getImageList() {
        return imageList;
    }

}
