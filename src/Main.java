public class Main {
    public static void main(String[] args) {
        MutablePerson originalPerson = new MutablePerson("Иван", "Петров", 20);

        ImmutableStudent student = new ImmutableStudent("ST001", originalPerson);

        System.out.println("=== Тест 1: Изменение оригинала не влияет на студента ===");
        System.out.println("Оригинал до: " + originalPerson.getName() + " " +
                originalPerson.getSecondName() + ", " + originalPerson.getAge());

        MutablePerson studentPerson1 = student.getPerson();
        System.out.println("Студент до: " + studentPerson1.getName() + " " +
                studentPerson1.getSecondName() + ", " + studentPerson1.getAge());

        originalPerson.setName("Петр");
        originalPerson.setSecondName("Сидоров");
        originalPerson.setAge(25);

        MutablePerson studentPerson2 = student.getPerson();
        System.out.println("Студент после: " + studentPerson2.getName() + " " +
                studentPerson2.getSecondName() + ", " + studentPerson2.getAge());

        System.out.println("\n=== Тест 2: Изменение полученной копии не влияет на студента ===");
        MutablePerson personFromStudent = student.getPerson();
        System.out.println("Копия до: " + personFromStudent.getName() + " " +
                personFromStudent.getSecondName() + ", " + personFromStudent.getAge());

        personFromStudent.setName("Анна");
        personFromStudent.setSecondName("Иванова");
        personFromStudent.setAge(22);

        MutablePerson studentPerson3 = student.getPerson();
        System.out.println("Студент после изменения копии: " + studentPerson3.getName() + " " +
                studentPerson3.getSecondName() + ", " + studentPerson3.getAge());
    }
}
