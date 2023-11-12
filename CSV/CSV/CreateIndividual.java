import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateIndividual {

    public static void CreateIndividual(String FILENAME, String TYPE) {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            List<List<String>> lines = new ArrayList<>();

            // Read CSV content
            while ((line = reader.readLine()) != null) {
                List<String> values = new ArrayList<>();
                String[] splitLine = line.split(",");
                for (String value : splitLine) {
                    values.add(value);
                }
                lines.add(values);
            }

            for (int i = 1; i < lines.size(); i++) {
                List<String> row = lines.get(i);
                if (!row.isEmpty()) {
                    // Create a new CSV file for each row
                    String outputCSVFile = "Data/" + TYPE + '/' + TYPE + lines.get(i).get(0) + ".csv";
                    writeCSV(outputCSVFile, lines.get(0), row);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeCSV(String fileName, List<String> header, List<String> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write header
            writer.write(String.join(",", header));
            writer.newLine();

            // Write data
            writer.write(String.join(",", data));
        }
    }
}
