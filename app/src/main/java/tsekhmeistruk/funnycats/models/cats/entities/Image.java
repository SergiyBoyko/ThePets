package tsekhmeistruk.funnycats.models.cats.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

import java.io.Serializable;

import tsekhmeistruk.funnycats.models.PictureEntity;

/**
 * Created by Roman Tsekhmeistruk on 17.01.2017.
 */

@Root(name = "image")
public class Image implements PictureEntity {

    @Element(name = "url")
    private String url;

    @Element(name = "id")
    private String id;

    @Element(name = "source_url", required = false)
    private String sourceUrl;

    @Element(name = "sub_id", required = false)
    private String subId;

    @Element(name = "created", required = false)
    private String created;

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getSourceUrl() {
        return sourceUrl;
    }

}
