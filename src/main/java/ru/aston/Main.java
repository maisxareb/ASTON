package ru.aston;

import ru.aston.adapter.AudioPlayer;
import ru.aston.builder.Computer;
import ru.aston.chain.JuniorSupport;
import ru.aston.chain.LeadSupport;
import ru.aston.chain.SeniorSupport;
import ru.aston.chain.SupportHandler;
import ru.aston.decorator.*;
import ru.aston.proxy.Image;
import ru.aston.proxy.ProxyImage;
import ru.aston.strategy.CreditCardPayment;
import ru.aston.strategy.PayPalPayment;
import ru.aston.strategy.ShoppingCart;

public class Main {
    public static void main(String[] args) {
        System.out.println("Демонстрация паттернов проектирования");

        System.out.println("1. ПАТТЕРН СТРАТЕГИЯ (Strategy)");
        demonstrateStrategy();

        System.out.println("\n2. ПАТТЕРН ЦЕПОЧКА ОБЯЗАННОСТЕЙ (Chain of Responsibility)");
        demonstrateChainOfResponsibility();

        System.out.println("\n3. ПАТТЕРН БИЛДЕР (Builder)");
        demonstrateBuilder();

        System.out.println("\n4. ПАТТЕРН ПРОКСИ (Proxy)");
        demonstrateProxy();

        System.out.println("\n5. ПАТТЕРН ДЕКОРАТОР (Decorator)");
        demonstrateDecorator();

        System.out.println("\n6. ПАТТЕРН АДАПТЕР (Adapter)");
        demonstrateAdapter();

        System.out.println("Демонстрация завершена");
    }

    private static void demonstrateStrategy() {
        ShoppingCart cart = new ShoppingCart();

        System.out.println("Клиент выбирает оплату кредитной картой:");
        cart.setPaymentStrategy(new CreditCardPayment("1234-5678-9012-3456"));
        cart.checkout(5000);

        System.out.println("\nКлиент выбирает оплату через PayPal:");
        cart.setPaymentStrategy(new PayPalPayment("user@example.com"));
        cart.checkout(3000);
    }

    private static void demonstrateChainOfResponsibility() {
        SupportHandler junior = new JuniorSupport();
        SupportHandler senior = new SeniorSupport();
        SupportHandler lead = new LeadSupport();

        junior.setNext(senior);
        senior.setNext(lead);

        System.out.println("Запрос 1 (легкая проблема - severity 2):");
        junior.handle("Не включается компьютер", 2);

        System.out.println("\nЗапрос 2 (проблема средней сложности - severity 5):");
        junior.handle("База данных не отвечает", 5);

        System.out.println("\nЗапрос 3 (критическая проблема - severity 9):");
        junior.handle("Критический сбой системы", 9);
    }

    private static void demonstrateBuilder() {
        Computer gamingPC = Computer.builder("Intel i9-13900K", "32GB DDR5")
                .storage("1TB NVMe SSD")
                .graphicsCard("NVIDIA RTX 4080")
                .bluetooth(true)
                .wifi(true)
                .build();

        Computer officePC = Computer.builder("Intel i5-13600K", "16GB DDR4")
                .storage("512GB SSD")
                .wifi(true)
                .build();

        System.out.println("Игровой компьютер:");
        System.out.println(gamingPC);

        System.out.println("\nОфисный компьютер:");
        System.out.println(officePC);
    }

    private static void demonstrateProxy() {
        System.out.println("Создаем прокси для изображений:");
        Image image1 = new ProxyImage("photo1.jpg");
        Image image2 = new ProxyImage("photo2.jpg");

        System.out.println("\nПервое отображение image1 (будет загрузка):");
        image1.display();

        System.out.println("\nПовторное отображение image1 (без загрузки):");
        image1.display();

        System.out.println("\nОтображение image2 (первый раз - загрузка):");
        image2.display();
    }

    private static void demonstrateDecorator() {
        Coffee simpleCoffee = new SimpleCoffee();
        System.out.println("Базовая конфигурация:");
        System.out.println(simpleCoffee.getDescription() + " = " + simpleCoffee.getCost() + " руб.");

        Coffee milkCoffee = new Milk(new SimpleCoffee());
        System.out.println("\nС добавлением молока:");
        System.out.println(milkCoffee.getDescription() + " = " + milkCoffee.getCost() + " руб.");

        Coffee sugarCoffee = new Sugar(new SimpleCoffee());
        System.out.println("\nС добавлением сахара:");
        System.out.println(sugarCoffee.getDescription() + " = " + sugarCoffee.getCost() + " руб.");

        Coffee fancyCoffee = new WhippedCream(new Sugar(new Milk(new SimpleCoffee())));
        System.out.println("\nКофе с молоком, сахаром и взбитыми сливками:");
        System.out.println(fancyCoffee.getDescription() + " = " + fancyCoffee.getCost() + " руб.");
    }

    private static void demonstrateAdapter() {
        AudioPlayer audioPlayer = new AudioPlayer();

        System.out.println("Воспроизведение MP3 (нативный формат):");
        audioPlayer.play("mp3", "song.mp3");

        System.out.println("\nВоспроизведение MP4 (через адаптер):");
        audioPlayer.play("mp4", "video.mp4");

        System.out.println("\nВоспроизведение VLC (через адаптер):");
        audioPlayer.play("vlc", "movie.vlc");

        System.out.println("\nВоспроизведение AVI (неподдерживаемый формат):");
        audioPlayer.play("avi", "film.avi");
    }
}