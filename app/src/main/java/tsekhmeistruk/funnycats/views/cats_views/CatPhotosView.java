package tsekhmeistruk.funnycats.views.cats_views;

import tsekhmeistruk.funnycats.models.cats.entities.CategoryList;
import tsekhmeistruk.funnycats.models.cats.entities.ImageList;
import tsekhmeistruk.funnycats.views.BaseView;

/**
 * Created by Roman Tsekhmeistruk on 12.05.2017.
 */

public interface CatPhotosView extends BaseView {

    void showCategoryList(CategoryList categoryNameList);

    void showPhotoList(ImageList imageList);

}
