import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get("students.txt"));

            for (String line : lines) {
                String[] parts = line.split("\\|");

                String name = parts[0];
                int age = Integer.parseInt(parts[1]);

                List<Book> books = new ArrayList<>();
                for (int i = 2; i < parts.length; i += 4) {
                    String title = parts[i];
                    String author = parts[i + 1];
                    int pages = Integer.parseInt(parts[i + 2]);
                    int year = Integer.parseInt(parts[i + 3]);
                    books.add(new Book(title, author, pages, year));
                }

                students.add(new Student(name, books, age));
            }
            System.out.println("=== Все студенты ===");
            students.forEach(System.out::println);
            System.out.println();

            System.out.println("=== Книги после 2000 года (первые 3 по кол-ву страниц) ===");

            students.stream()
                    .flatMap(student -> student.getBooks().stream())
                    .sorted(Comparator.comparingInt(Book::getPages))
                    .distinct()
                    .filter(book -> book.getYear() > 2000)
                    .limit(3)
                    .peek(book -> {
                        if (book.equals(students.stream()
                                .flatMap(s -> s.getBooks().stream())
                                .sorted(Comparator.comparingInt(Book::getPages))
                                .distinct()
                                .filter(b -> b.getYear() > 2000)
                                .findFirst()
                                .orElse(null))) {
                            System.out.println("=== Результат поиска ===");
                            System.out.println("Найден год выпуска книги: " + book.getYear());
                        }
                    })
                    .forEach(System.out::println);

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}