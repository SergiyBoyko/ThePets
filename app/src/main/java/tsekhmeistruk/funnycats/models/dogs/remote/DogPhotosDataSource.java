package tsekhmeistruk.funnycats.models.dogs.remote;

import rx.Observable;
import tsekhmeistruk.funnycats.api.TheDogApiSet;
import tsekhmeistruk.funnycats.models.dogs.entities.TheDogApiResponse;

/**
 * Created by fbrsw on 01.11.2017.
 */

public class DogPhotosDataSource implements IDogPhotosDataSource {

    private TheDogApiSet theDogApiSet;

    public DogPhotosDataSource(TheDogApiSet theDogApiSet) {
        this.theDogApiSet = theDogApiSet;
    }

    @Override
    public Observable<TheDogApiResponse> getBulldogImages() {
        return theDogApiSet.getBulldogImages();
    }

    @Override
    public Observable<TheDogApiResponse> getBoxerImages() {
        return theDogApiSet.getBoxerImages();
    }

    @Override
    public Observable<TheDogApiResponse> getDobermanImages() {
        return theDogApiSet.getDobermanImages();
    }

    @Override
    public Observable<TheDogApiResponse> getLabradorImages() {
        return theDogApiSet.getLabradorImages();
    }

    @Override
    public Observable<TheDogApiResponse> getPoodleImages() {
        return theDogApiSet.getPoodleImages();
    }
}
