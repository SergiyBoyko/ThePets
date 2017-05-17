package tsekhmeistruk.funnycats.presenters;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tsekhmeistruk.funnycats.activities.CatsActivity;
import tsekhmeistruk.funnycats.models.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.utils.rx.RxErrorAction;
import tsekhmeistruk.funnycats.utils.rx.RxRetryWithDelay;

/**
 * Created by Roman Tsekhmeistruk on 12.05.2017.
 */

public class CatPhotosPresenter extends BasePresenter<CatsActivity> {

    private final ICatPhotosDataSource catPhotosDataSource;

    public CatPhotosPresenter(ICatPhotosDataSource catPhotosDataSource) {
        this.catPhotosDataSource = catPhotosDataSource;
    }

    public void loadCategoryList() {
        CatsActivity view = getView();
        subscribe(catPhotosDataSource.getCategoryList()
                .retryWhen(new RxRetryWithDelay())
                .map(theCatApiResponse -> theCatApiResponse.getData().getCategoryList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showCategoryList, new RxErrorAction(getView().getContext()))
        );
    }

    public void loadPhotoList(String categoryName) {
        CatsActivity view = getView();
        subscribe(catPhotosDataSource.getPhotoList(categoryName)
                .retryWhen(new RxRetryWithDelay())
                .map(theCatApiResponse -> theCatApiResponse.getData().getImageList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }

}
