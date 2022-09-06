package ku.cs.models;

import java.util.ArrayList;
import java.util.List;

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
}
