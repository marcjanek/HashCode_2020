import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class Solution {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources");
        for (File i : Objects.requireNonNull(file.listFiles())) {
            new Solution().solve(i);
        }
    }

    public void solve(final File file) throws IOException {
        String s = loadFile(file);

        String solution = "";

        saveToFile("solutions/" + file.getName().substring(0, file.getName().length() - 4) + "." + java.time.Instant.now() + ".txt",
                solution
        );
    }

    private String loadFile(final File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()));
    }

    private void saveToFile(final String path, final String value) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(value);
        }
    }
}
