package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import javafx.scene.image.Image;
import ku.cs.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ComplaintListDataSource implements DataSource<ComplaintList>{

    private String directoryName;
    private String fileName;

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
            CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8));
            List<String[]> allData = reader.readAll();
            DataSource<UserList> userData = new UserListDataSource("data", "user.csv");
            UserList userList = userData.readData();

            DataSource<ComplaintCategoryList> categoryData = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
            ComplaintCategoryList complaintCategoryList = categoryData.readData();

            for (String[] data : allData) {
                if (data.length == 1 && data[0].isEmpty()) {
                    continue;
                }

                User user = userList.findUserById(data[1]);
                // ComplaintCategory complaintCategory = complaintCategoryList.findComplaintCategoryById(data[3]);
                List<User> userVote = new ArrayList<>();
                for (String userId : data[11].split(",")) {
                    if (userId.isEmpty()) continue;
                    userVote.add(userList.findUserById(userId));
                }

                LocalDateTime date = LocalDateTime.parse(data[8]);
                User teacher = userList.findUserById(data[13]);
                List<Image> imageList = new ArrayList<>();
                for (String path : data[14].split(",")) {
                    imageList.add(new Image("file:images/" + path));
                }
                Complaint complaint = new Complaint(data[0], user, data[2], data[3], data[4], data[5], date, data[9], Integer.parseInt(data[10]), userVote, teacher, imageList);

                String[] question = data[6].split(",");
                String[] answer = data[7].split(",");

                for (int i = 0; i < question.length; i++) {
                    complaint.addQuestionAnswer(question[i], answer[i]);
                }

                complaintList.addComplaint(complaint);

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
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer = new CSVWriter(osw);

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
