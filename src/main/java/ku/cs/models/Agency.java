package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Agency {
    private String id;
    private String name;

    // list of complaint category that an agency handle
    private List<ComplaintCategory> managedCategory;

    public Agency(String id, String name, List<ComplaintCategory> managedCategory) {
        this.id = id;
        this.name = name;
        this.managedCategory = managedCategory;
    }

    public Agency(String id, String name) {
        this(id, name, new ArrayList<>());
    }

    public Agency(String name) {
        this(UUID.randomUUID().toString(), name);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addManagedCategory(ComplaintCategory complaintCategory) {
        managedCategory.add(complaintCategory);
    }
    public void removeManagedCategory(ComplaintCategory complaintCategory) {
        managedCategory.remove(complaintCategory);
    }
    public List<ComplaintCategory> getManagedCategory() {
        return managedCategory;
    }
    public List<String> getManagedCategoryName() {
        return managedCategory
                .stream()
                .map(c -> c.getName())
                .collect(Collectors.toList());
    }
    @Override
    public String toString() {
        return name;
    }

    public String[] toStringArray() {
        return new String[] {
                id,
                name,
                managedCategory
                        .stream()
                        .map(c -> c.getId())
                        .collect(Collectors.joining(","))

        };
    }
}
