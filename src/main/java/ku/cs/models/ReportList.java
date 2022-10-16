package ku.cs.models;

import ku.cs.services.collection.Filterer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReportList {
    private List<Report> reportList;

    public ReportList() {
        reportList = new ArrayList<>();
    }

    public List<Report> getReportList() {
        return reportList;
    }

    public void addReport(Report report) {
        reportList.add(report);
    }

    public void removeReport(Report report) {
        // remove all the same complaints that have been report more than once
        // if it is report complaint otherwise remove user
        if (report.getComplaint() != null) {
            reportList = reportList
                    .stream()
                    .filter(r -> r.getComplaint() == null || !r.getComplaint().getId().equals(report.getComplaint().getId()))
                    .collect(Collectors.toList());
        } else {
            reportList = reportList
                    .stream()
                    .filter(r -> r.getUser() == null || !r.getUser().getId().equals(report.getUser().getId()))
                    .collect(Collectors.toList());
        }

    }

    public ReportList filterBy(Filterer<Report> filterer) {
        ReportList filteredReport = new ReportList();
        for (Report report : reportList) {
            if (filterer.filter(report)) {
                filteredReport.addReport(report);
            }
        }
        return filteredReport;
    }
}
