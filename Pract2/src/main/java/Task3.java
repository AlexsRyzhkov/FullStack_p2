import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Task3 {

    public static void main(String[] args) throws IOException {
        System.out.println(getCheckSum());
    }

    public static int getCheckSum() throws IOException {
        String filePath = "src/main/resources/100Mb.txt";

        int checksum = 0;

        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel fileChannel = fis.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (fileChannel.read(buffer) > 0) {
                buffer.flip();

                while (buffer.hasRemaining()) {
                    checksum += buffer.get() & 0xFF;
                    checksum &= 0xFFFF;
                }

                buffer.clear();
            }
        }

        return checksum;
    }
}
