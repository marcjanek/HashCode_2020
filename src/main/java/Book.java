import lombok.Value;

@Value
public class Book implements Comparable<Book> {
    int id, value;

    @Override
    public int compareTo(Book book) {
        return Integer.compare(book.getId(), this.getId());
    }
}
