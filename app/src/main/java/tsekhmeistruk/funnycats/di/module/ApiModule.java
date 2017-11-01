package tsekhmeistruk.funnycats.di.module;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import tsekhmeistruk.funnycats.Constants;
import tsekhmeistruk.funnycats.api.TheCatApiSet;
import tsekhmeistruk.funnycats.api.TheDogApiSet;
import tsekhmeistruk.funnycats.models.cats.remote.CatPhotosDataSource;
import tsekhmeistruk.funnycats.models.cats.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.models.dogs.remote.DogPhotosDataSource;
import tsekhmeistruk.funnycats.models.dogs.remote.IDogPhotosDataSource;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

@Module
public class ApiModule {

    @Provides
    @Singleton
    @Named("retrofitForCats")
    public Retrofit provideRetrofitForCats() {
        return new Retrofit.Builder()
                .baseUrl(Constants.THE_CAT_API)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    @Named("retrofitForDogs")
    public Retrofit provideRetrofitForDogs() {
        return new Retrofit.Builder()
                .baseUrl(Constants.THE_DOG_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ICatPhotosDataSource provideCatPhotosDataSource(@Named("retrofitForCats") Retrofit retrofit) {
        return new CatPhotosDataSource(retrofit.create(TheCatApiSet.class));
    }

    @Provides
    @Singleton
    IDogPhotosDataSource provideDogPhotosDataSource(@Named("retrofitForDogs") Retrofit retrofit) {
        return new DogPhotosDataSource(retrofit.create(TheDogApiSet.class));
    }

}
