package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import javafx.scene.chart.PieChart;
import ku.cs.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

public class ComplaintCategoryListDataSource implements DataSource <ComplaintCategoryList> {
    private String directoryName;
    private String fileName;

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
            CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8));
            List<String[]> allData = reader.readAll();

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
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer = new CSVWriter(osw);

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
