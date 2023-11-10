import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mainClass {

    public static void main(String[] args) throws FileNotFoundException, IOException{
        System.out.println(ReadCSV.readFile("Data/camp_list.csv"));

        List<List<String>> test = new ArrayList<>();

        test.add(List.of("1", "2"));
        test.add(List.of("Emilia's Camp", "Miku's Camp"));

        ReadCSV.writeFile("Data/camp_summary.csv", test);

        CreateIndividual.CreateIndividual("Data/camp_list.csv", "CAMP");
    }
}