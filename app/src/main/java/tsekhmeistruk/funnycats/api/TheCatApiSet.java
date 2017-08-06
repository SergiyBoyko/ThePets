package tsekhmeistruk.funnycats.api;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.models.entities.TheCatApiResponse;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

public interface TheCatApiSet {

    @GET("categories/list")
    Observable<TheCatApiResponse> getCategoryList();

    @GET("images/get?format=xml&results_per_page=50&size=small")
    Observable<TheCatApiResponse> getPhotoList(@Query("category") String categoryName);

    @GET("images/getfavourites?api_key=" + Constants.THE_CAT_API_KEY)
    Observable<TheCatApiResponse> getFavourites(@Query("sub_id") String userId);

    @GET("images/favourite?api_key=" + Constants.THE_CAT_API_KEY)
    Observable<TheCatApiResponse> favouriteImage(@Query("sub_id") String userId,
                                                 @Query("image_id") String imageId,
                                                 @Query("action") String action);

}
