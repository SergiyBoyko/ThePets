package tsekhmeistruk.funnycats.views;

import tsekhmeistruk.funnycats.models.entities.CategoryList;

/**
 * Created by Roman Tsekhmeistruk on 12.05.2017.
 */

public interface CatPhotosView extends BaseView {

    void showCategoryList(CategoryList categoryNameList);

}
