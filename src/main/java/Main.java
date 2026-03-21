import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

            Optional<Integer> resultYear = students.stream()
                    .flatMap(student -> student.getBooks().stream())
                    .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                    .distinct()
                    .filter(book -> book.getYear() > 2000)
                    .limit(3)
                    .map(Book::getYear)
                    .findFirst();

            System.out.println("=== Результат поиска ===");
            resultYear.ifPresentOrElse(
                    year -> System.out.println("Найден год выпуска книги:" + year),
                    () -> System.out.println("Книга, выпущенная после 2000 года, не найдена")
            );

            System.out.println("\n=== Книги после 2000 года ===");
            students.stream()
                    .flatMap(student -> student.getBooks().stream())
                    .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                    .distinct()
                    .filter(book -> book.getYear() > 2000)
                    .limit(3)
                    .forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла" + e.getMessage());
        }
    }
}
