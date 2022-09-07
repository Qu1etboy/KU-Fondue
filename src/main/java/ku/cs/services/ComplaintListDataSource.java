package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ku.cs.models.*;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ComplaintListDataSource implements DataSource<ComplaintList>{

    String directoryName;
    String fileName;

    public ComplaintListDataSource(String directoryName, String fileName) {
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
    public ComplaintList readData() {
        ComplaintList complaintList = new ComplaintList();
        String filePath = directoryName+ File.separator + fileName;
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            List<String[]> allData = reader.readAll();
            DataSource<UserList> userData = new UserListDataSource("data", "user.csv");
            UserList userList = userData.readData();

            DataSource<ComplaintCategoryList> categoryData = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
            ComplaintCategoryList complaintCategoryList = categoryData.readData();

            for (String[] data : allData) {
                if (data.length > 0) {
                    User user = userList.findUserByUsername(data[0]);
//                    String[] inputData = { "q1", "q2"};
//                    List<String> inputDataList = Arrays.asList(inputData);
                    ComplaintCategory complaintCategory = complaintCategoryList.findComplaintCategoryById(data[3]);

                    complaintList.addComplaint(new Complaint(user, data[1], data[2], complaintCategory ,null,null));
                }
            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return  complaintList;
    }

    @Override
    public void writeData(ComplaintList complaintList) {
        String filePath = directoryName + File.separator + fileName;
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(filePath));
            for(Complaint complaint : complaintList.getComplaintList()){
                String[] row = complaint.toStringArray();
                writer.writeNext(row);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            try {
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
