package tsekhmeistruk.funnycats.models.remote;

import rx.Observable;
import tsekhmeistruk.funnycats.api.TheCatApiSet;
import tsekhmeistruk.funnycats.models.entities.TheCatApiResponse;

/**
 * Created by Roman Tsekhmeistruk on 12.05.2017.
 */

public class CatPhotosDataSource implements ICatPhotosDataSource {

    private TheCatApiSet theCatApiSet;

    public CatPhotosDataSource(TheCatApiSet theCatApiSet) {
        this.theCatApiSet = theCatApiSet;
    }

    @Override
    public Observable<TheCatApiResponse> getCategoryList() {
        return theCatApiSet.getCategoryList();
    }

    @Override
    public Observable<TheCatApiResponse> getPhotoList(String category) {
        return theCatApiSet.getPhotoList(category);
    }

    @Override
    public Observable<TheCatApiResponse> getFavouritesPhotoList(String userId) {
        return theCatApiSet.getFavourites(userId);
    }

}
