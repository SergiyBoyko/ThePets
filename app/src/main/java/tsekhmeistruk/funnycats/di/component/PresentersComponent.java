package tsekhmeistruk.funnycats.di.component;

import dagger.Component;
import tsekhmeistruk.funnycats.activities.cats_activities.CatsActivity;
import tsekhmeistruk.funnycats.activities.FullSizeImageActivity;
import tsekhmeistruk.funnycats.activities.dog_activities.DogsActivity;
import tsekhmeistruk.funnycats.di.module.PresentersModule;
import tsekhmeistruk.funnycats.di.scopes.Scope;
import tsekhmeistruk.funnycats.di.scopes.Scopes;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

@Scope(Scopes.VIEW)
@Component(
        modules = {PresentersModule.class},
        dependencies = {AppComponent.class}
)
public interface PresentersComponent {

    void inject(CatsActivity catPhotosView);

    void inject(DogsActivity dogsPhotosView);

    void inject(FullSizeImageActivity fullSizeImageActivity);

}
