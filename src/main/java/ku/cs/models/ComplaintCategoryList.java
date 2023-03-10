package ku.cs.models;

import ku.cs.services.collection.Filterer;

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

    public ComplaintCategoryList filterBy(Filterer<ComplaintCategory> filterer) {
        ComplaintCategoryList filteredCategory = new ComplaintCategoryList();
        for (ComplaintCategory category : complaintCategoryList) {
            if (filterer.filter(category)) {
                filteredCategory.addComplaintCategory(category);
            }
        }
        return filteredCategory;
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
