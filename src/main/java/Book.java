import lombok.Value;

@Value
public class Book implements Cloneable, Comparable<Book> {
    int id, value;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Book book) {
        return Integer.compare(book.getId(), this.getId());
    }
}
