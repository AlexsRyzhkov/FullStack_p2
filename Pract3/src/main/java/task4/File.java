package task4;

public class File {

    FileType type;
    int size;

    public File(FileType type, int size) {
        this.type = type;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Файл{" +
                "тип='" + type + '\'' +
                ", размер=" + size +
                '}';
    }
}
