package tsekhmeistruk.funnycats.di.module;

import dagger.Module;
import dagger.Provides;
import tsekhmeistruk.funnycats.di.scopes.Scope;
import tsekhmeistruk.funnycats.di.scopes.Scopes;
import tsekhmeistruk.funnycats.models.cats.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.presenters.cats_presenter.FullSizePhotoPresenter;
import tsekhmeistruk.funnycats.presenters.cats_presenter.CatPhotoListPresenter;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

@Module
public class PresentersModule {

    @Provides
    @Scope(Scopes.VIEW)
    public CatPhotoListPresenter provideCatPhotoListPresenter(ICatPhotosDataSource catPhotosDataSource) {
        return new CatPhotoListPresenter(catPhotosDataSource);
    }

    @Provides
    @Scope(Scopes.VIEW)
    public FullSizePhotoPresenter provideFullSizeImagePresenter(ICatPhotosDataSource catPhotosDataSource) {
        return new FullSizePhotoPresenter(catPhotosDataSource);
    }

}
