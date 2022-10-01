package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ku.cs.models.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AgencyListDataSource implements DataSource<AgencyList> {
    // read and write csv file using opencsv library
    String directoryName;
    String fileName;

    public AgencyListDataSource(String directoryName, String fileName) {
        this.directoryName = directoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }

    /**
     * Check if file is existed. if not create it
     */
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
    public AgencyList readData() {
        AgencyList agencyList = new AgencyList();
        String filePath = directoryName + File.separator + fileName;
        try {
            CSVReader reader = new CSVReader(new FileReader(filePath, StandardCharsets.UTF_8));

            List<String[]> allData = reader.readAll();
            DataSource<ComplaintCategoryList> categoryData = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
            ComplaintCategoryList complaintCategoryList = categoryData.readData();

            for (String[] data : allData) {
                List<ComplaintCategory> categories = new ArrayList<>();
                String[] categoryIds = data[2].split(",");
                for (String categoryId : categoryIds) {
                    if (categoryId.isEmpty()) continue;
                    categories.add(complaintCategoryList.findComplaintCategoryById(categoryId));
                }
                agencyList.addAgency(new Agency(
                        data[0],
                        data[1],
                        categories
                        )
                );
            }

        } catch (CsvException | IOException e) {
            throw new RuntimeException(e);
        }

        return agencyList;
    }

    @Override
    public void writeData(AgencyList agencyList) {
        String filePath = directoryName + File.separator + fileName;
        CSVWriter writer = null;
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            writer = new CSVWriter(osw);

            for (Agency agency : agencyList.getAgencyList()) {
                String[] row = agency.toStringArray();
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
