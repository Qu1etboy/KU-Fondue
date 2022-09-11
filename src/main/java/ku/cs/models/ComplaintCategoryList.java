package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComplaintCategoryList {
    private List<ComplaintCategory> complaintCategoryList;

    public ComplaintCategoryList() {
        this.complaintCategoryList = new ArrayList<>();
    }

    public List<ComplaintCategory> getComplaintCategoryList() {
        return complaintCategoryList;
    }

    public void addComplaintCategory(ComplaintCategory complaintCategory){
        complaintCategoryList.add(complaintCategory);
    }

    public List<String> getAllComplaintCategoryName(){
        List <String> names = new ArrayList<>();

        for (ComplaintCategory complaintCategory : complaintCategoryList) {
            names.add(complaintCategory.getName());
        }

        return names;
    }
    public ComplaintCategory findComplaintCategoryById(String id) {
        for (ComplaintCategory complaintCategory : complaintCategoryList) {
            if (complaintCategory.getId().equals(id)) {
                return complaintCategory;
            }
        }

        return null;
    }

    public void updateComplaintCategory(ComplaintCategory complaintCategory) {
        complaintCategoryList = complaintCategoryList
                .stream()
                .map(c -> c.getId().equals(complaintCategory.getId()) ? complaintCategory : c)
                .collect(Collectors.toList());
    }

    public void removeComplaintCategory(ComplaintCategory complaintCategory) {
        complaintCategoryList = complaintCategoryList
                .stream()
                .filter(c -> !c.getId().equals(complaintCategory.getId()))
                .collect(Collectors.toList());
    }
}
