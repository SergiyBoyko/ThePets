package tsekhmeistruk.funnycats.models;

import java.io.Serializable;

/**
 * Created by fbrsw on 31.10.2017.
 */

public interface PictureEntity extends Serializable {

    String getUrl();

    String getId();

    String getSourceUrl();

}
