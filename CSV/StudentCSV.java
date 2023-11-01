import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentCSV {

    private static String FILENAME = "student_list.csv";

    private static List<String> id = new ArrayList<>();
    private static List<String> name = new ArrayList<>();
    private static List<String> email = new ArrayList<>();
    private static List<String> faculty = new ArrayList<>();

    public static List<List<String>> readFile() throws FileNotFoundException, IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                id.add(values[0]);
                name.add(values[1]);
                email.add(values[2]);
                faculty.add(values[3]);
            }

            List<List<String>> studentCSVOut = new ArrayList<>();
            studentCSVOut.add(id);
            studentCSVOut.add(name);
            studentCSVOut.add(email);
            studentCSVOut.add(faculty);

            return studentCSVOut;
        }
    }
}

