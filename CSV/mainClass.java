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
        CreateIndividual.CreateIndividual("Data/student_list.csv", "STUDENT");

        System.out.println(IndividualCampCSV.ReadIndividualCamp("Data/CAMP/CAMP1.CSV"));
        System.out.println(IndividualStudentCSV.ReadIndividualStudent("Data/STUDENT/STUDENT1.CSV"));
        System.out.println(IndividualCampCSV.ReadIndividualCamp("Data/CAMP/CAMP1TEST.CSV"));
        System.out.println(IndividualStudentCSV.ReadIndividualStudent("Data/STUDENT/STUDENT1TEST.CSV"));

        IndividualCampCSV.writeIndividualCamp("Data/CAMP/CAMP1TEST.CSV", IndividualCampCSV.ReadIndividualCamp("Data/CAMP/CAMP1TEST.CSV"));
        IndividualStudentCSV.writeIndividualStudent("Data/STUDENT/STUDENT1TEST.CSV", IndividualStudentCSV.ReadIndividualStudent("Data/STUDENT/STUDENT1TEST.CSV"));
    }
}