import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ReadCSV {

    private static List<String> id = new ArrayList<>();
    private static List<String> name = new ArrayList<>();

    public static List<List<String>> readFile(String FILENAME) throws FileNotFoundException, IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                id.add(values[0]);
                name.add(values[1]);
            }

            List<List<String>> CSVOut = new ArrayList<>();
            CSVOut.add(id);
            CSVOut.add(name);

            return CSVOut;
        }
        catch (FileNotFoundException e) {
            List<List<String>> ErrorFNF= new ArrayList<>();
            ErrorFNF.add(List.of("File Not Found"));
            return ErrorFNF;
        }
    }

    public static void writeFile(String FILENAME, List<List<String>> data) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            // Writing CSV header if needed
            writer.println("Id,Name");
    
            // Writing data to CSV
            int rowCount = data.get(0).size(); // Assuming all lists have the same size

            int i = 0;

            while (i < rowCount) {

                StringBuilder rowString = new StringBuilder();
                for (List<String> row : data) {
                    if (row.isEmpty()) {
                    // Handle empty rows, if necessary
                    i++;
                    continue;
                    }

                    if (rowString.length() > 0) {
                        rowString.append(",");
                    }
                    rowString.append(row.get(i));
                }
                writer.println(rowString.toString());

                i++;
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }

        catch (java.lang.IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}

