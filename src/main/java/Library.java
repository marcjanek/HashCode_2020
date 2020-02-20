import lombok.Data;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class Library {
    private final Set<Book> books = new HashSet<>();
    private final int id;
    private final BigDecimal signTime;
    private final long numberOfBooksPerDay;

    private Set<Book> solutionBooks = new HashSet<>();

    public BigDecimal libraryScore(final int daysLeft) {
        return scannedBooks(daysLeft).parallelStream()
                .map(e -> new BigDecimal(e.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(signTime, MathContext.DECIMAL128);
    }

    public void addBook(final Book book) {
        books.add(book);
    }

    public long numOfBooksToScan(final int leftDays) {
        return numberOfBooksPerDay * Math.max((leftDays - signTime.longValue()), 0);
    }

    public Set<Book> scannedBooks(final int leftDays) {
        return books.parallelStream()
                .sorted(Comparator.comparingInt(Book::getValue))
                .limit(numOfBooksToScan(leftDays))
                .collect(Collectors.toSet());
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(this.id).append(" ").append(solutionBooks.size()).append("\n");
        for (Book solutionBook : solutionBooks) {
            stringBuilder.append(solutionBook.getId()).append(" ");
        }
        return stringBuilder.toString().trim();
    }
}
