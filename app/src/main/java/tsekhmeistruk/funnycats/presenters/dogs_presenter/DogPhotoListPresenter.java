package tsekhmeistruk.funnycats.presenters.dogs_presenter;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import tsekhmeistruk.funnycats.Constants;
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

    public void loadCategoryList() {
        DogsActivity view = getView();
        view.showCategoryList(Constants.dogsCategories);
    }

    public void loadBulldogPhotoList() {
        DogsActivity view = getView();
        subscribe(dogPhotosDataSource.getBulldogImages()
                .retryWhen(new RxRetryWithDelay())
                .map(TheDogApiResponse::getMessage)
                .map(ImageUrl::convertStringsToImageUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }

    public void loadBoxerPhotoList() {
        DogsActivity view = getView();
        subscribe(dogPhotosDataSource.getBoxerImages()
                .retryWhen(new RxRetryWithDelay())
                .map(TheDogApiResponse::getMessage)
                .map(ImageUrl::convertStringsToImageUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }

    public void loadDobermanPhotoList() {
        DogsActivity view = getView();
        subscribe(dogPhotosDataSource.getDobermanImages()
                .retryWhen(new RxRetryWithDelay())
                .map(TheDogApiResponse::getMessage)
                .map(ImageUrl::convertStringsToImageUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }

    public void loadLabradorPhotoList() {
        DogsActivity view = getView();
        subscribe(dogPhotosDataSource.getLabradorImages()
                .retryWhen(new RxRetryWithDelay())
                .map(TheDogApiResponse::getMessage)
                .map(ImageUrl::convertStringsToImageUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }

    public void loadPoodlePhotoList() {
        DogsActivity view = getView();
        subscribe(dogPhotosDataSource.getPoodleImages()
                .retryWhen(new RxRetryWithDelay())
                .map(TheDogApiResponse::getMessage)
                .map(ImageUrl::convertStringsToImageUrls)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(view::showPhotoList, new RxErrorAction(getView().getContext()))
        );
    }
}
