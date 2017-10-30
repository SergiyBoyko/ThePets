package tsekhmeistruk.funnycats.presenters.cats_presenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tsekhmeistruk.funnycats.activities.MainActivity;
import tsekhmeistruk.funnycats.models.cats.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.presenters.BasePresenter;
import tsekhmeistruk.funnycats.utils.rx.RxErrorAction;
import tsekhmeistruk.funnycats.utils.rx.RxRetryWithDelay;

/**
 * Created by Roman Tsekhmeistruk on 12.05.2017.
 */

public class PhotoListPresenter extends BasePresenter<MainActivity> {

    private final ICatPhotosDataSource catPhotosDataSource;

    public PhotoListPresenter(ICatPhotosDataSource catPhotosDataSource) {
        this.catPhotosDataSource = catPhotosDataSource;
    }

    public void loadCategoryList() {
        MainActivity view = getView();
        subscribe(catPhotosDataSource.getCategoryList()
                .retryWhen(new RxRetryWithDelay())
                .map(theCatApiResponse -> theCatApiResponse.getData().getCategoryList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showCategoryList, new RxErrorAction(getView().getContext()))
        );
    }

    public void loadPhotoList(String categoryName) {
        MainActivity view = getView();
        subscribe(catPhotosDataSource.getPhotoList(categoryName)
                .retryWhen(new RxRetryWithDelay())
                .map(theCatApiResponse -> theCatApiResponse.getData().getImageList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }

    public void loadFavouritesPhotoList(String userId) {
        MainActivity view = getView();
        subscribe(catPhotosDataSource.getFavouritesPhotoList(userId)
                .retryWhen(new RxRetryWithDelay())
                .map(theCatApiResponse -> theCatApiResponse.getData().getImageList())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }

}
