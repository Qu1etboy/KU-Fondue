package ku.cs.models;

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
        reportList = reportList
                .stream()
                .filter(r -> !r.getId().equals(report.getId()))
                .collect(Collectors.toList());
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
