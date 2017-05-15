package tsekhmeistruk.funnycats.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.api.TheCatApiSet;
import tsekhmeistruk.funnycats.models.remote.CatPhotosDataSource;
import tsekhmeistruk.funnycats.models.remote.ICatPhotosDataSource;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(Constants.THE_CAT_API)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ICatPhotosDataSource provideCatPhotosDataSource(Retrofit retrofit) {
        return new CatPhotosDataSource(retrofit.create(TheCatApiSet.class));
    }

}
