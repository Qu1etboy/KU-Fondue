package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import javafx.scene.chart.PieChart;
import ku.cs.models.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ComplaintCategoryListDataSource implements DataSource <ComplaintCategoryList> {
    String directoryName;
    String fileName;

    public ComplaintCategoryListDataSource(String directoryName, String fileName) {
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
    public ComplaintCategoryList readData() {
        ComplaintCategoryList complaintCategoryList = new ComplaintCategoryList();
        String filePath = directoryName+ File.separator + fileName;
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            List<String[]> allData = reader.readAll();
//            DataSource<UserList> userData = new UserListDataSource("data", "user.csv");
//            UserList userList = userData.readData();
            DataSource<CategoryAttributeList> categoryAttributeData = new CategoryAttributeListDataSource("data", "attribute.csv");
            CategoryAttributeList categoryAttributeList = categoryAttributeData.readData();
            for (String[] data : allData) {
                if (data.length > 0) {
                   List<CategoryAttribute> categoryAttributes = categoryAttributeList.findAllCategoryAttributeByCategoryId(data[0]);

                   boolean requireImage = Boolean.parseBoolean(data[2]);
                    complaintCategoryList.addComplaintCategory(new ComplaintCategory(data[0], data[1],categoryAttributes, requireImage));
                }
            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return  complaintCategoryList;
    }

    @Override
    public void writeData(ComplaintCategoryList complaintCategoryList) {
        String filePath = directoryName + File.separator + fileName;
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(filePath));
            for(ComplaintCategory complaintCategory : complaintCategoryList.getComplaintCategoryList()){
                String[] row = complaintCategory.toStringArray();
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
