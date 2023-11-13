import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IndividualStudentCSV {
    public static List<List<String>> ReadIndividualStudent (String FILENAME) throws FileNotFoundException, IOException {
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
        String[] password = convertToList(values[2]);
        String[] faculty = convertToList(values[3]);
        String[] isDefaultPassword = convertToList(values[4]);
        String[] role = convertToList(values[5]);
        String[] camps = convertToList(values[6]);
        String[] prevCamps = convertToList(values[7]);
        String[] committee = convertToList(values[8]);
        String[] points = convertToList(values[9]);

        List<List<String>> output = new ArrayList<>();

        output.add(Arrays.asList(id));
        output.add(Arrays.asList(name));
        output.add(Arrays.asList(password));
        output.add(Arrays.asList(faculty));
        output.add(Arrays.asList(isDefaultPassword));
        output.add(Arrays.asList(role));
        output.add(Arrays.asList(camps));
        output.add(Arrays.asList(prevCamps));
        output.add(Arrays.asList(committee));
        output.add(Arrays.asList(points));

        return output;
        }
    }

    public static void writeIndividualStudent(String FILENAME, List<List<String>> data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {
            writer.write("Id,Name,Password,Faculty,IsDefaultPassword,Role,Camps,PrevCamps,Committtee,Points");
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
