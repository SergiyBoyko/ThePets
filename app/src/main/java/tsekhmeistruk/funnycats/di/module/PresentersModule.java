package tsekhmeistruk.funnycats.di.module;

import dagger.Module;
import dagger.Provides;
import tsekhmeistruk.funnycats.di.scopes.Scope;
import tsekhmeistruk.funnycats.di.scopes.Scopes;
import tsekhmeistruk.funnycats.models.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.presenters.CatPhotosPresenter;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

@Module
public class PresentersModule {

    @Provides
    @Scope(Scopes.VIEW)
    public CatPhotosPresenter provideCatPhotosPresenter(ICatPhotosDataSource catPhotosDataSource) {
        return new CatPhotosPresenter(catPhotosDataSource);
    }

}
