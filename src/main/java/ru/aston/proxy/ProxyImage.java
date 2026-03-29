package ru.aston.proxy;

public class ProxyImage implements Image {
    private RealImage realImage;
    private String filename;

    public ProxyImage(String filename) {
        this.filename = filename;
        System.out.println("  [Прокси] Создан прокси для изображения: " + filename);
    }

    @Override
    public void display() {
        if (realImage == null) {
            System.out.println("  [Прокси] Первое отображение - загружаем реальное изображение");
            realImage = new RealImage(filename);
        } else {
            System.out.println("  [Прокси] Повторное отображение - используем кэш");
        }
        realImage.display();
    }
}
