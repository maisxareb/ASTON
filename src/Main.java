public class Main {
    public static void main(String[] args) {
        System.out.println("=== Тестирование ImmutableStudent с clone() ===\n");

        MutablePerson originalPerson = new MutablePerson("Иван", "Петров", 20);
        System.out.println("Создан оригинальный объект: " + originalPerson);

        ImmutableStudent student = new ImmutableStudent("ST001", originalPerson);
        System.out.println("Создан студент на основе оригинала");

        System.out.println("\n=== Тест 1: Изменение оригинала не влияет на студента ===");
        System.out.println("Оригинал до: " + originalPerson);

        MutablePerson studentPerson1 = student.getPerson();
        System.out.println("Студент до: " + studentPerson1);

        originalPerson.setName("Петр");
        originalPerson.setSecondName("Сидоров");
        originalPerson.setAge(25);
        System.out.println("\nОригинал после изменения: " + originalPerson);

        MutablePerson studentPerson2 = student.getPerson();
        System.out.println("Студент после: " + studentPerson2);

        System.out.println("\n=== Тест 2: Изменение полученной копии не влияет на студента ===");
        MutablePerson personFromStudent = student.getPerson();
        System.out.println("Копия до: " + personFromStudent);

        personFromStudent.setName("Анна");
        personFromStudent.setSecondName("Иванова");
        personFromStudent.setAge(22);
        System.out.println("Копия после изменения: " + personFromStudent);

        MutablePerson studentPerson3 = student.getPerson();
        System.out.println("Студент после изменения копии: " + studentPerson3);

        System.out.println("\n=== Проверка что разные вызовы getPerson() возвращают разные объекты ===");
        MutablePerson copy1 = student.getPerson();
        MutablePerson copy2 = student.getPerson();
        System.out.println("copy1 == copy2: " + (copy1 == copy2));
        System.out.println("copy1.equals(copy2): " + copy1.equals(copy2));
    }
}
