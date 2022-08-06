package ku.cs.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class has a method that use to read, write, update and delete data from csv file
 * writing by Weerawong Vonggatunyu (Non)
 * ps. the class method is not perfectly done and can have some problem so can be change
 *     or update in the future
 * */
public class CSVReaderWriter {
    /*
    * Read data from csv file and return as ArrayList<String[]>
    * */
    public ArrayList<String[]> readCsv(String path) {
        String row;
        ArrayList<String[]> data = new ArrayList<>();
        try {
            int numberOfLine = 0;
            BufferedReader csvReader = new BufferedReader(new FileReader(path));
            while ((row = csvReader.readLine()) != null) {
                String[] line = row.split(",");
                // ignore first line because it is a header column
                if (numberOfLine > 0) {
                    data.add(line);
                    System.out.println("username: " + line[0] + ", password: " + line[1]);
                }
                numberOfLine++;
            }
            csvReader.close();

        } catch (IOException e) {
            System.err.println("No file found");
        }

        return data;
    }

    /*
    * Add new data to the end of a file
    * */
    public void writeCsv(String[] data, String path) {
        try {
            // because fileWriter will replace file with new content we need to copy old content first before
            // writing it
            ArrayList<String[]> oldData = readCsv(path);
            FileWriter csvWriter = new FileWriter(path);

            for (String[] d : oldData) {
                csvWriter.append(String.join(",", d));
                csvWriter.append("\n");
            }
            // append new data
            csvWriter.append(String.join(",", data));
            csvWriter.append("\n");

            csvWriter.flush();
            csvWriter.close();

            System.out.println("Write file success");

        } catch (IOException e) {
            System.err.println("No file found");
        }
    }

    /*
    * Update data by replace the selected row with new one
    * check data and updateData is the same by unique key/id
    * which will be in the first column
    * */
    public void updateCsv(String[] updateData, String path) {
        try {
            ArrayList<String[]> oldData = readCsv(path);
            FileWriter csvWriter = new FileWriter(path);

            for (String[] data: oldData) {
                // the data we want to update is found
                if (data[0].equalsIgnoreCase(updateData[0])) {
                    csvWriter.append(String.join(",", updateData));
                } else {
                    csvWriter.append(String.join(",", data));
                }

                csvWriter.append("\n");
            }

            csvWriter.flush();
            csvWriter.close();

        } catch (IOException e) {
            System.err.println("No file found");
        }
    }

    /*
    * Remove data by not writing it to the file
    * */
    public void removeCsv(String[] targetData, String path) {
        try {
            ArrayList<String[]> oldData = readCsv(path);
            FileWriter csvWriter = new FileWriter(path);

            for (String[] data: oldData) {
                // keep everything except the data we want to remove
                if (!data[0].equalsIgnoreCase(targetData[0])) {
                    csvWriter.append(String.join(",", data));
                    csvWriter.append("\n");
                }
            }

            csvWriter.flush();
            csvWriter.close();

        } catch (IOException e) {
            System.err.println("No file found");
        }
    }

}
