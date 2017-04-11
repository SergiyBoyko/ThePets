package tsekhmeistruk.funnycats.di.scopes;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Roman Tsekhmeistruk on 11.04.2017.
 * Scope annotation for different scopes.
 */

@javax.inject.Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {

    String value() default "";

}
