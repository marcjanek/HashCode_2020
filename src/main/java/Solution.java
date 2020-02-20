import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {
    public static void main(String[] args) throws IOException {
        File file = new File("src/main/resources");
        for (File i : Objects.requireNonNull(file.listFiles())) {
            new Solution().solve(i);
        }
    }

    public void solve(final File file) throws IOException {
        String[] split = loadFile(file).split("\n");
        int days = 0;

        List<Library> libraries = new LinkedList<>();
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (i == 0) {
                days = Integer.parseInt((s.split(" ")[2]));
            } else if (i == 1) {
                String[] strings = s.split(" ");
                for (int index = 0; index < strings.length; index++) {
                    String s1 = strings[index];
                    books.add(new Book(index, Integer.parseInt(s1)));
                }
            } else {
                Library library = new Library(i / 2 - 1, new BigDecimal((s.split(" ")[1])), Integer.parseInt((s.split(" ")[2])));
                int numOfBooks = Integer.parseInt((s.split(" ")[0]));
                ++i;
                s = split[i];
                libraries.add(library);

                for (int j = 0; j < numOfBooks; ++j) {
                    library.addBook(books.get(Integer.parseInt(s.split(" ")[j])));
                }
            }
        }

        List<Library> solutions = new ArrayList<>();
        while (days > 0 && libraries.size() > 0) {

            System.out.println(days);
            int finalDays1 = days;
            Library bestLibrary = libraries.parallelStream().max(Comparator.comparing(o -> o.libraryScore(finalDays1))).orElse(null);
            if (bestLibrary == null) {
                break;
            }

            solutions.add(bestLibrary);
            libraries.remove(bestLibrary);

            int finalDays = days;
            libraries = libraries.stream()
                    .filter(e -> e.getSignTime().longValue() < finalDays)
                    .filter(e -> !e.getBooks().isEmpty())
                    .collect(Collectors.toList());

            bestLibrary.setSolutionBooks(bestLibrary.scannedBooks(days));

            libraries.forEach(e -> e.getBooks().removeAll(bestLibrary.getSolutionBooks()));

            days -= bestLibrary.getSignTime().longValue();
        }

        String solution = (solutions.size() - solutions.stream().filter(e -> e.getSolutionBooks().isEmpty()).count()) + "\n" +
                solutions.stream()
                        .filter(e -> e.getSolutionBooks().size() > 0)
                        .map(e -> e.toString() + "\n")
                        .reduce("", String::concat);
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
