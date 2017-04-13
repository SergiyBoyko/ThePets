package tsekhmeistruk.funnycats.models.entities;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

@Root(name = "categories")
public class CategoryList {

    @ElementList(inline = true)
    private List<Category> categoryList;

    private List<Category> getCategoryList() {
        return categoryList;
    }

    public List<String> getCategories() {
        List<String> categoriesNames = new ArrayList<>();
        categoriesNames.add("all");
        categoriesNames.add("favorites");

        for (Category category : getCategoryList()) {
            categoriesNames.add(category.getName());
        }

        return categoriesNames;
    }

}
