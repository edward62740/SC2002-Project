import java.io.FileNotFoundException;
import java.io.IOException;

public class mainClass {

    public static void main(String[] args) throws FileNotFoundException, IOException{
        System.out.println(StudentCSV.readFile());
    }
}