package tsekhmeistruk.funnycats.models.cats.remote;

import rx.Observable;
import tsekhmeistruk.funnycats.models.cats.entities.TheCatApiResponse;

/**
 * Created by Roman Tsekhmeistruk on 12.05.2017.
 */

public interface ICatPhotosDataSource {

    Observable<TheCatApiResponse> getCategoryList();

    Observable<TheCatApiResponse> getPhotoList(String category);

    Observable<TheCatApiResponse> getFavouritesPhotoList(String userId);

    Observable<TheCatApiResponse> favouritePhoto(String userId, String imageId, String action);

}
