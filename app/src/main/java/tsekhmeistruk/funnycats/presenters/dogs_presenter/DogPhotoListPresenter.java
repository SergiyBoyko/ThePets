package tsekhmeistruk.funnycats.presenters.dogs_presenter;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tsekhmeistruk.funnycats.activities.cats_activities.CatsActivity;
import tsekhmeistruk.funnycats.activities.dog_activities.DogsActivity;
import tsekhmeistruk.funnycats.models.dogs.entities.ImageUrl;
import tsekhmeistruk.funnycats.models.dogs.entities.TheDogApiResponse;
import tsekhmeistruk.funnycats.models.dogs.remote.IDogPhotosDataSource;
import tsekhmeistruk.funnycats.presenters.BasePresenter;
import tsekhmeistruk.funnycats.utils.rx.RxErrorAction;
import tsekhmeistruk.funnycats.utils.rx.RxRetryWithDelay;

/**
 * Created by fbrsw on 01.11.2017.
 */

public class DogPhotoListPresenter extends BasePresenter<DogsActivity> {

    private final IDogPhotosDataSource dogPhotosDataSource;

    public DogPhotoListPresenter(IDogPhotosDataSource dogPhotosDataSource) {
        this.dogPhotosDataSource = dogPhotosDataSource;
    }

    public void loadPhotoList() {
        DogsActivity view = getView();
        subscribe(dogPhotosDataSource.getTestImages()
                .retryWhen(new RxRetryWithDelay())
                .map(TheDogApiResponse::getMessage)
                .map(ImageUrl::convertStringsToImageUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }
}
