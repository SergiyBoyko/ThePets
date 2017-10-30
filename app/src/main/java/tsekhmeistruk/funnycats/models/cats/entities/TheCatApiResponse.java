package tsekhmeistruk.funnycats.models.cats.entities;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "response")
public class TheCatApiResponse {

    @Element(name = "data")
    private Data data;

    public Data getData() {
        return data;
    }

}