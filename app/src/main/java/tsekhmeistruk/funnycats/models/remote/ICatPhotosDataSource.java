package tsekhmeistruk.funnycats.models.remote;

import rx.Observable;
import tsekhmeistruk.funnycats.models.entities.TheCatApiResponse;

/**
 * Created by Roman Tsekhmeistruk on 12.05.2017.
 */

public interface ICatPhotosDataSource {

    Observable<TheCatApiResponse> getCategoryList();

    Observable<TheCatApiResponse> getPhotoList(String category);

    Observable<TheCatApiResponse> getFavouritesPhotoList(String userId);

}
