package tsekhmeistruk.funnycats;

import android.support.multidex.MultiDexApplication;

import tsekhmeistruk.funnycats.di.component.AppComponent;
import tsekhmeistruk.funnycats.di.component.DaggerAppComponent;
import tsekhmeistruk.funnycats.di.module.ApiModule;
import tsekhmeistruk.funnycats.di.module.AppModule;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

public class AppFunnyPets extends MultiDexApplication {

    private AppComponent appComponent;

    public AppFunnyPets() {
        super();

        appComponent = DaggerAppComponent.builder()
                .apiModule(new ApiModule())
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent appComponent() {
        return appComponent;
    }

}
