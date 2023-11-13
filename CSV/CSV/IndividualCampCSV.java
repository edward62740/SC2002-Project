import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndividualCampCSV {
    public static List<List<String>> ReadIndividualCamp (String FILENAME) throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            line = br.readLine();
            line = br.readLine();

        // Split the CSV line into an array of values
        String[] values = splitCSVLine(line);

        String temp;

        // Assign values to separate variables
        String[] id = convertToList(values[0]);
        String[] name = convertToList(values[1]);
        String[] dates = convertToList(values[2]);
        String[] visible = convertToList(values[3]);
        String[] closingDate = convertToList(values[4]);
        String[] userGroup = convertToList(values[5]);
        String[] location = convertToList(values[6]);
        String[] totalSlots = convertToList(values[7]);
        String[] ccmSlots = convertToList(values[8]);
        String[] staff = convertToList(values[9]);
        String[] committee = convertToList(values[10]);
        String[] registeredStudent = convertToList(values[11]);
        String[] description = convertToList(values[12]);

        List<List<String>> output = new ArrayList<>();

        output.add(Arrays.asList(id));
        output.add(Arrays.asList(name));
        output.add(Arrays.asList(dates));
        output.add(Arrays.asList(visible));
        output.add(Arrays.asList(closingDate));
        output.add(Arrays.asList(userGroup));
        output.add(Arrays.asList(location));
        output.add(Arrays.asList(totalSlots));
        output.add(Arrays.asList(ccmSlots));
        output.add(Arrays.asList(staff));
        output.add(Arrays.asList(committee));
        output.add(Arrays.asList(registeredStudent));
        output.add(Arrays.asList(description));

        return output;
        }
    }

    public static void writeIndividualCamp(String FILENAME, List<List<String>> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("Id,Name,Dates,Visible,ClosingDate,UserGroup,Location,TotalSlots,CCMSlots,Staff,Committee,RegisteredStudent,Description");
            writer.newLine();

            // Iterate over the list and construct the CSV content
            StringBuilder line = new StringBuilder();
            for (List<String> column : data) {
                line.append("\"");
                for (String value : column) {
                    line.append(value);
                    line.append(",");
                }
                line.deleteCharAt(line.length() - 1);
                line.append("\"");
                line.append(",");
            }
            writer.write(line.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String[] splitCSVLine(String csvLine) {
        // Split the CSV line using commas, taking into account quoted values
        return csvLine.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    private static String[] convertToList (String temp) {
        temp = temp.replace("\"", "");
        return temp.split(",");
    }
}