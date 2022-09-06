package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryAttributeList {
    private List<CategoryAttribute> categoryAttributeList;

    public CategoryAttributeList() {
        this.categoryAttributeList = new ArrayList<>();
    }

    public List<CategoryAttribute> getCategoryAttributeList() {
        return categoryAttributeList;
    }
    public void addCategoryAttribute(CategoryAttribute categoryAttribute){
        categoryAttributeList.add(categoryAttribute);
    }

    public CategoryAttribute findCategoryAttributeById(String id) {
        for (CategoryAttribute categoryAttribute : categoryAttributeList) {
            if (categoryAttribute.getIdCategory().equals(id)) {
                return categoryAttribute;
            }
        }
        return null;
    }

    public List<CategoryAttribute> findAllCategoryAttributeByCategoryId(String categoryId) {
        return categoryAttributeList
                .stream()
                .filter(e -> e.getIdCategory().equals(categoryId))
                .collect(Collectors.toList());
    }
}
