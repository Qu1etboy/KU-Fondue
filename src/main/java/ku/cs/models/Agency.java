package ku.cs.models;

import java.util.UUID;

public class Agency {
    private String id;
    private String name;

    // list of complaint category that an agency handle
    // private List<ComplaintCategory> managedCategory;

    public Agency(String id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }

    public String[] toStringArray() {
        return new String[] {
                id,
                name
        };
    }
}
