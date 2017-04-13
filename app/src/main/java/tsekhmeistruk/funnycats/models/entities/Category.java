package tsekhmeistruk.funnycats.models.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "category")
public class Category {

    @Element(name = "id")
    private long id;

    @Element(name = "name")
    private String name;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
