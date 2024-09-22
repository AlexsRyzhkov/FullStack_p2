import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class Task3 {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String filePath = "src/main/resources/test.txt";

        List<String> fileStrings = Files.readAllLines(Path.of(filePath));

        System.out.println(getCheckSum(fileStrings));
    }

    public static int getCheckSum(List<String> fileStrings) throws IOException, NoSuchAlgorithmException {

        int checksum = 0;

        for (String line : fileStrings) {
            for (char ch : line.toCharArray()) {
                checksum += ch;
                checksum = checksum & 0xFFFF;
            }
        }

        return checksum;
    }
}
