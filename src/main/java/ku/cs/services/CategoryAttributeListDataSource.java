package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ku.cs.models.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CategoryAttributeListDataSource implements DataSource <CategoryAttributeList> {
    String directoryName;
    String fileName;

    public CategoryAttributeListDataSource(String directoryName, String fileName) {
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
    public CategoryAttributeList readData() {
        CategoryAttributeList categoryAttributeList = new CategoryAttributeList();
        String filePath = directoryName + File.separator + fileName;

        try {
            CSVReader reader = new CSVReader(new FileReader(filePath));
            List<String[]> allData = reader.readAll();
//            DataSource<UserList> userData = new UserListDataSource("data", "user.csv");
//            UserList userList = userData.readData();

            // DataSource<CategoryAttributeList> categoryAttributeData = new CategoryAttributeListDataSource("data", "attribute.csv");
            // CategoryAttributeList complaintCategoryList = new CategoryAttributeList();

            for (String[] data : allData) {
                if (data.length > 0) {
                    List<String> inputData = new ArrayList<>(Arrays.asList(data[4].split(",")));
                    List<String> inputAnswer = new ArrayList<>(Arrays.asList(data[5].split(",")));
                    // List<String> inputImage = Arrays.asList(data[5].split(","));

                    categoryAttributeList.addCategoryAttribute(
                            new CategoryAttribute(data[0], data[1],data[2], data[3], inputData, inputAnswer,null)
                    );
                }
            }

        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
        return categoryAttributeList;
    }

    @Override
    public void writeData(CategoryAttributeList data) {
        String filePath = directoryName + File.separator + fileName;
        CSVWriter writer = null;
        try {
            writer = new CSVWriter(new FileWriter(filePath));
            for(CategoryAttribute categoryAttribute : data.getCategoryAttributeList()){
                String[] row = categoryAttribute.toStringArray();
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