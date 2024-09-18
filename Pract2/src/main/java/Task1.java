import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Task1 {

    public static void main(String[] args) {
        Path path = Paths.get("src/main/resources/test.txt");

        try{
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

            for (String line : lines) {
                System.out.println(line);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
