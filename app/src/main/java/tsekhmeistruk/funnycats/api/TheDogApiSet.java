package tsekhmeistruk.funnycats.api;

import retrofit2.http.GET;
import rx.Observable;
import tsekhmeistruk.funnycats.models.dogs.entities.TheDogApiResponse;

/**
 * Created by fbrsw on 30.10.2017.
 */

public interface TheDogApiSet {

    @GET("breed/bulldog/images")
    Observable<TheDogApiResponse> getTestImages();

}
