package tsekhmeistruk.funnycats.di.component;

import retrofit2.Retrofit;
import tsekhmeistruk.funnycats.models.cats.remote.ICatPhotosDataSource;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

public interface ApiComponent {

    Retrofit retrofit();

    ICatPhotosDataSource catPhotosDataSource();

}
