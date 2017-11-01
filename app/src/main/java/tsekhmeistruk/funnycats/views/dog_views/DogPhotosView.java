package tsekhmeistruk.funnycats.views.dog_views;

import java.util.List;

import tsekhmeistruk.funnycats.models.cats.entities.CategoryList;
import tsekhmeistruk.funnycats.models.dogs.entities.ImageUrl;
import tsekhmeistruk.funnycats.views.BaseView;

/**
 * Created by fbrsw on 31.10.2017.
 */

public interface DogPhotosView extends BaseView {

    void showPhotoList(List<ImageUrl> imageUrls);

    void showCategoryList(List<String> categories);
}
