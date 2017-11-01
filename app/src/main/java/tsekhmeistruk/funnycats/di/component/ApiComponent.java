package tsekhmeistruk.funnycats.di.component;

import javax.inject.Named;

import retrofit2.Retrofit;
import tsekhmeistruk.funnycats.models.cats.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.models.dogs.remote.IDogPhotosDataSource;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

public interface ApiComponent {

    @Named("retrofitForCats")
    Retrofit catRetrofit();

    @Named("retrofitForDogs")
    Retrofit dogRetrofit();

    ICatPhotosDataSource catPhotosDataSource();

    IDogPhotosDataSource dogPhotosDataSource();

}
