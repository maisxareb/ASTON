import java.util.List;

public class Student {
    private String name;
    private List<Book> books;
    private int age;

    public Student(String name, List<Book> books, int age) {
        this.name = name;
        this.books=books;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        return String.format("Student: %s, Age: %d, Books count: %d",
                name, age, books.size());
    }
}