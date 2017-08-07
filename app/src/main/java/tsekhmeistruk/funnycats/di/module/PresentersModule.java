package tsekhmeistruk.funnycats.di.module;

import dagger.Module;
import dagger.Provides;
import tsekhmeistruk.funnycats.di.scopes.Scope;
import tsekhmeistruk.funnycats.di.scopes.Scopes;
import tsekhmeistruk.funnycats.models.remote.ICatPhotosDataSource;
import tsekhmeistruk.funnycats.presenters.FullSizePhotoPresenter;
import tsekhmeistruk.funnycats.presenters.PhotoListPresenter;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

@Module
public class PresentersModule {

    @Provides
    @Scope(Scopes.VIEW)
    public PhotoListPresenter providePhotoListPresenter(ICatPhotosDataSource catPhotosDataSource) {
        return new PhotoListPresenter(catPhotosDataSource);
    }

    @Provides
    @Scope(Scopes.VIEW)
    public FullSizePhotoPresenter provideFullSizeImagePresenter(ICatPhotosDataSource catPhotosDataSource) {
        return new FullSizePhotoPresenter(catPhotosDataSource);
    }

}
