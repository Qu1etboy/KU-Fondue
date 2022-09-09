package ku.cs.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ComplaintCategory {
    private String id;
    private String name;
    private List<CategoryAttribute> categoryAttributeList;
    private boolean requireImage;

    public ComplaintCategory(String id, String name,List<CategoryAttribute> categoryAttributeList, boolean requireImage) {
        this.id = id;
        this.name=name;
        this.categoryAttributeList = categoryAttributeList;
        this.requireImage = requireImage;

    }

    public ComplaintCategory(String id, String name, List<CategoryAttribute> categoryAttributeList) {

        this(id,name, categoryAttributeList,false);
    }

    public ComplaintCategory(String name, List<CategoryAttribute> categoryAttributeList) {

        this(UUID.randomUUID().toString(),name,categoryAttributeList,false);
    }

    public ComplaintCategory(String name) {
        this(UUID.randomUUID().toString(), name, new ArrayList<>(), false);
    }


    public String getId() {
        return id;
    }

    public List<CategoryAttribute> getCategoryAttributeList() {
        return categoryAttributeList;
    }

    public boolean isRequireImage() {
        return requireImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String [] toStringArray(){
        return new String[] {
                id,
                name,
                Boolean.toString(requireImage)
        };
    }

    public String toString() {
        return name;
    }
}
