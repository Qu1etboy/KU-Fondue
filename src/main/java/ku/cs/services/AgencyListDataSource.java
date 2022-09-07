package ku.cs.services;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import ku.cs.models.Agency;
import ku.cs.models.AgencyList;
import ku.cs.models.User;
import ku.cs.models.UserList;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
            CSVReader reader = new CSVReader(new FileReader(filePath));

            List<String[]> allData = reader.readAll();
            for (String[] data : allData) {
                agencyList.addAgency(new Agency(
                        data[0],
                        data[1]
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
            writer = new CSVWriter(new FileWriter(filePath));
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
