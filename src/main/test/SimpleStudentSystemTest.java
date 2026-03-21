import org.junit.jupiter.api.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleStudentSystemTest {

    private static final String TEST_FILE = "test_students.txt";
    private List<Student> students;

    @BeforeEach
    void setUp() throws IOException {
        createTestFile(TEST_FILE);
        students = readStudentsFromFile(TEST_FILE);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(TEST_FILE));
    }

    @Test
    @DisplayName("Тест 1: Чтение файла и создание студентов")
    void testFileReadingAndStudentCreation() {
        assertNotNull(students);
        assertEquals(4, students.size());

        Student first = students.get(0);
        assertEquals("Иван", first.getName());
        assertEquals(20, first.getAge());
        assertEquals(5, first.getBooks().size());

        Student second = students.get(1);
        assertEquals("Анна", second.getName());
        assertEquals(22, second.getAge());
    }

    @Test
    @DisplayName("Тест 2: Получение списка всех книг")
    void testGetAllBooks() {
        long totalBooks = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .count();

        assertEquals(20, totalBooks);
    }

    @Test
    @DisplayName("Тест 3: Сортировка книг по страницам")
    void testSortingByPages() {
        List<Book> sortedBooks = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                .collect(Collectors.toList());

        assertFalse(sortedBooks.isEmpty());

        for (int i = 0; i < sortedBooks.size() - 1; i++) {
            assertTrue(sortedBooks.get(i).getPages() <= sortedBooks.get(i + 1).getPages());
        }
    }

    @Test
    @DisplayName("Тест 4: Уникальность книг (distinct)")
    void testDistinctBooks() {
        long distinctBooks = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .distinct()
                .count();

        List<Book> books = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .distinct()
                .collect(Collectors.toList());

        for (int i = 0; i < books.size(); i++) {
            for (int j = i + 1; j < books.size(); j++) {
                assertNotEquals(books.get(i), books.get(j));
            }
        }

        // Дополнительная проверка
        assertTrue(distinctBooks <= 20);
    }

    @Test
    @DisplayName("Тест 5: Фильтрация книг после 2000 года")
    void testFilterByYear() {
        List<Book> booksAfter2000 = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .filter(book -> book.getYear() > 2000)
                .collect(Collectors.toList());

        assertFalse(booksAfter2000.isEmpty());

        for (Book book : booksAfter2000) {
            assertTrue(book.getYear() > 2000);
        }
    }

    @Test
    @DisplayName("Тест 6: Ограничение стрима на 3 элемента (limit)")
    void testLimitOperation() {
        List<Book> limitedBooks = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .collect(Collectors.toList());

        assertEquals(3, limitedBooks.size(), "Должно быть ровно 3 книги после limit(3)");
    }

    @Test
    @DisplayName("Тест 7: Получение годов выпуска (map)")
    void testGetYears() {
        List<Integer> years = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .map(Book::getYear)
                .collect(Collectors.toList());

        assertEquals(3, years.size());

        for (Integer year : years) {
            assertTrue(year > 2000);
        }
    }

    @Test
    @DisplayName("Тест 8: Метод короткого замыкания findFirst")
    void testFindFirstAndOptional() {
        Optional<Integer> resultYear = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .map(Book::getYear)
                .findFirst();

        assertTrue(resultYear.isPresent(), "Optional должен содержать значение");
        assertTrue(resultYear.get() > 2000, "Год должен быть после 2000");
    }

    @Test
    @DisplayName("Тест 9: Обработка Optional (ifPresentOrElse)")
    void testIfPresentOrElse() {
        // Тест с существующим значением
        Optional<Integer> presentYear = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .filter(book -> book.getYear() > 2000)
                .map(Book::getYear)
                .findFirst();

        assertTrue(presentYear.isPresent(), "Должны быть книги после 2000 года");

        // Тест с отсутствующим значением
        Optional<Integer> emptyYear = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .filter(book -> book.getYear() > 3000)
                .map(Book::getYear)
                .findFirst();

        assertTrue(emptyYear.isEmpty(), "Не должно быть книг после 3000 года");
    }

    @Test
    @DisplayName("Тест 10: Комплексный тест всей цепочки операций")
    void testFullStreamChain() {
        List<Book> result = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                .distinct()
                .filter(book -> book.getYear() > 2000)
                .limit(3)
                .collect(Collectors.toList());

        assertEquals(3, result.size(), "Должно быть 3 книги");

        // Проверяем сортировку
        assertTrue(result.get(0).getPages() <= result.get(1).getPages());
        assertTrue(result.get(1).getPages() <= result.get(2).getPages());

        // Проверяем фильтрацию
        for (Book book : result) {
            assertTrue(book.getYear() > 2000);
        }

        // Проверяем уникальность
        Set<Book> uniqueBooks = new HashSet<>(result);
        assertEquals(result.size(), uniqueBooks.size());
    }

    @Test
    @DisplayName("Тест 11: Проверка equals и hashCode")
    void testEqualsAndHashCode() {
        Book book1 = new Book("Тест", "Автор", 100, 2020);
        Book book2 = new Book("Тест", "Автор", 100, 2020);
        Book book3 = new Book("Другой", "Автор", 100, 2020);

        assertEquals(book1, book2, "Одинаковые книги должны быть равны");
        assertNotEquals(book1, book3, "Разные книги не должны быть равны");
        assertEquals(book1.hashCode(), book2.hashCode(), "HashCode одинаковых книг должен совпадать");

        // Проверяем работу distinct
        List<Book> books = Arrays.asList(book1, book2, book3);
        long distinctCount = books.stream().distinct().count();
        assertEquals(2, distinctCount, "Должно быть 2 уникальные книги");
    }

    @Test
    @DisplayName("Тест 12: Проверка toString методов")
    void testToStringMethods() {
        Student student = students.get(0);
        String studentString = student.toString();

        assertTrue(studentString.contains(student.getName()));
        assertTrue(studentString.contains(String.valueOf(student.getAge())));

        Book book = students.get(0).getBooks().get(0);
        String bookString = book.toString();

        assertTrue(bookString.contains(book.getTitle()));
        assertTrue(bookString.contains(String.valueOf(book.getPages())));
    }

    @Test
    @DisplayName("Тест 13: Проверка краевых случаев")
    void testEdgeCases() {
        // Тест с пустым списком студентов
        List<Student> emptyList = new ArrayList<>();
        Optional<Integer> emptyResult = emptyList.stream()
                .flatMap(student -> student.getBooks().stream())
                .filter(book -> book.getYear() > 2000)
                .map(Book::getYear)
                .findFirst();

        assertTrue(emptyResult.isEmpty(), "Для пустого списка Optional должен быть пустым");

        // Тест с фильтром, который ничего не найдет
        Optional<Integer> noResult = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .filter(book -> book.getYear() > 3000)
                .map(Book::getYear)
                .findFirst();

        assertTrue(noResult.isEmpty(), "Для фильтра без результатов Optional должен быть пустым");

        // Тест с limit(0)
        List<Book> zeroLimit = students.stream()
                .flatMap(student -> student.getBooks().stream())
                .limit(0)
                .collect(Collectors.toList());

        assertTrue(zeroLimit.isEmpty(), "limit(0) должен вернуть пустой список");
    }

    private List<Student> readStudentsFromFile(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        List<String> lines = Files.readAllLines(Paths.get(filename));

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

        return students;
    }

    private void createTestFile(String filename) throws IOException {
        List<String> testData = Arrays.asList(
                "Иван|20|Война и мир|Толстой|1300|1869|Преступление и наказание|Достоевский|600|1866|Мастер и Маргарита|Булгаков|400|1967|Тихий Дон|Шолохов|1400|1940|Собачье сердце|Булгаков|200|1925",
                "Анна|22|1984|Оруэлл|300|1949|Сто лет одиночества|Маркес|450|1967|Гарри Поттер|Роулинг|400|2001|Властелин колец|Толкин|1200|1954|Хоббит|Толкин|300|2002",
                "Петр|19|Алгоритмы|Кормен|800|2009|Java. Полное руководство|Шилдт|1200|2018|Чистый код|Мартин|400|2008|Паттерны проектирования|Гамма|500|1994|Эффективная Java|Блох|300|2018",
                "Мария|21|Маленький принц|Экзюпери|100|1943|Алиса в стране чудес|Кэрролл|150|1865|Три товарища|Ремарк|400|1938|Над пропастью во ржи|Сэлинджер|200|1951|Убить пересмешника|Ли|300|1960"
        );

        Files.write(Paths.get(filename), testData);
    }
}