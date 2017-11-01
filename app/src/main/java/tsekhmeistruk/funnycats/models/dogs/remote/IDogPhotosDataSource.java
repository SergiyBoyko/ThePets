package tsekhmeistruk.funnycats.models.dogs.remote;

import rx.Observable;
import tsekhmeistruk.funnycats.models.dogs.entities.TheDogApiResponse;

/**
 * Created by fbrsw on 01.11.2017.
 */

public interface IDogPhotosDataSource {

    Observable<TheDogApiResponse> getBulldogImages();

    Observable<TheDogApiResponse> getBoxerImages();

    Observable<TheDogApiResponse> getDobermanImages();

    Observable<TheDogApiResponse> getLabradorImages();

    Observable<TheDogApiResponse> getPoodleImages();
}
