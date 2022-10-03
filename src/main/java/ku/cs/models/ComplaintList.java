package ku.cs.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ComplaintList {
    private List<Complaint> complaintList;
    private int reportCount;
    private int inProgressCount;
    private int doneCount;

    public ComplaintList() {
        complaintList = new ArrayList<>();
        reportCount = 0;
        inProgressCount = 0;
        doneCount = 0;

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

    public ComplaintList filterBy(Filterer<Complaint> filterer) {
        ComplaintList filteredCategory = new ComplaintList();
        for (Complaint complaint : complaintList) {
            if (filterer.filter(complaint)) {
                filteredCategory.addComplaint(complaint);
            }
        }
        return filteredCategory;
    }

    public Complaint findComplaintById(String id) {
        for (Complaint complaint : complaintList) {
            if (complaint.getId().equals(id)) {
                return complaint;
            }
        }

        return null;
    }

    public void removeComplaint(Complaint complaint) {
        complaintList = complaintList
                .stream()
                .filter(c -> !c.getId().equals(complaint.getId()))
                .collect(Collectors.toList());
    }

    public int countCategory(ComplaintCategory complaintCategory) {
        return complaintList
                .stream()
                .filter(c -> c.getComplaintCategoryName().equals(complaintCategory.getName()))
                .toList()
                .size();
    }

    private void countStatus() {
        for (Complaint complaint : complaintList) {
            if (complaint.getStatus().equals("รอรับเรื่อง")) {
                reportCount++;
            } else if (complaint.getStatus().equals("ดําเนินการ")) {
                inProgressCount++;
            } else {
                doneCount++;
            }
        }
    }

    public int getReportCount() {
        countStatus();
        return reportCount;
    }

    public int getInProgressCount() {
        countStatus();
        return inProgressCount;
    }

    public int getDoneCount() {
        countStatus();
        return doneCount;
    }

}
