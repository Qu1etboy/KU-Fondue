package ku.cs.models;

import java.util.ArrayList;
import java.util.List;

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
}
