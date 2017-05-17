package tsekhmeistruk.funnycats.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import tsekhmeistruk.funnycats.models.entities.TheCatApiResponse;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

public interface TheCatApiSet {

    @GET("categories/list")
    Observable<TheCatApiResponse> getCategoryList();

    @GET("images/get?format=xml&results_per_page=50&size=small")
    Observable<TheCatApiResponse> getPhotoList(@Query("category") String categoryName);

}
