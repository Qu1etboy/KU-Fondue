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

    public void removeAttribute(CategoryAttribute categoryAttribute) {
        categoryAttributeList = categoryAttributeList
                .stream()
                .filter(a -> !a.getAttributeId().equals(categoryAttribute.getAttributeId()))
                .collect(Collectors.toList());
    }

    /**
     * This method remove all attribute that contain category id
     * you want to remove
     *
     * @param complaintCategory A category you want to remove
     */
    public void removeAllAttributeByCategoryId(ComplaintCategory complaintCategory) {
        categoryAttributeList = categoryAttributeList
                .stream()
                .filter(a -> !a.getIdCategory().equals(complaintCategory.getId()))
                .collect(Collectors.toList());
    }

    public void updateAttribute(CategoryAttribute categoryAttribute) {
        categoryAttributeList = categoryAttributeList
                .stream()
                .map(a -> a.getAttributeId().equals(categoryAttribute.getAttributeId()) ? categoryAttribute : a)
                .collect(Collectors.toList());
    }


}
