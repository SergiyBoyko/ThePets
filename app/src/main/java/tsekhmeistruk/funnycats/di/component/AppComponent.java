package tsekhmeistruk.funnycats.di.component;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;
import tsekhmeistruk.funnycats.di.module.ApiModule;
import tsekhmeistruk.funnycats.di.module.AppModule;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class
})
public interface AppComponent extends ApiComponent {

    Context context();

    SharedPreferences preferences();

    Executor executor();

}
