package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComplaintList {
    private List<Complaint> complaintList;

    public ComplaintList() {
        complaintList = new ArrayList<>();

    }
    public List<Complaint> getComplaintList(){
        return complaintList;
    }
    public void addComplaint(Complaint complaint){
        complaintList.add(complaint);
    }

    public void updateComplaint(Complaint complaint) {
        complaintList = complaintList
                .stream()
                .map(c -> c.getId().equals(complaint.getId()) ? complaint : c)
                .collect(Collectors.toList());
    }

    public int countCategory(ComplaintCategory complaintCategory) {
        return complaintList
                .stream()
                .filter(c -> c.getComplaintCategoryName().equals(complaintCategory.getName()))
                .toList()
                .size();
    }

}
