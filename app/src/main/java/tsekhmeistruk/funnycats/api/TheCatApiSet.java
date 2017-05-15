package tsekhmeistruk.funnycats.api;

import retrofit2.http.GET;
import rx.Observable;
import tsekhmeistruk.funnycats.models.entities.TheCatApiResponse;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

public interface TheCatApiSet {

    @GET("categories/list")
    Observable<TheCatApiResponse> getCategoryList();

}
