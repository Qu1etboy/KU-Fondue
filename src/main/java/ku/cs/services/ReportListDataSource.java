package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ku.cs.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ReportListDataSource implements DataSource<ReportList> {
    private String directoryName;
    private String fileName;

    public ReportListDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    public void checkFileIsExisted() {
        File file = new File(directoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = directoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public ReportList readData() {
        ReportList reportList = new ReportList();
        String filePath = directoryName + File.separator + fileName;

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8));
            List<String[]> allData = reader.readAll();

            DataSource<ComplaintList> complaintData = new ComplaintListDataSource("data", "complaint.csv");
            ComplaintList complaintList = complaintData.readData();
            DataSource<UserList> userData = new UserListDataSource("data", "user.csv");
            UserList userList = userData.readData();

            for (String[] data : allData) {
                if (data.length == 1 && data[0].isEmpty()) {
                    continue;
                }

                User user = userList.findUserById(data[1]);
                Complaint complaint = complaintList.findComplaintById(data[2]);

                reportList.addReport(
                        new Report(data[0], user, complaint, data[3], data[4])
                );

            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return reportList;
    }

    @Override
    public void writeData(ReportList reportList) {
        String filePath = directoryName + File.separator + fileName;
        CSVWriter writer = null;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer = new CSVWriter(osw);

            for (Report report : reportList.getReportList()) {
                String[] row = report.toStringArray();
                writer.writeNext(row);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
