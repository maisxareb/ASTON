package ru.aston.proxy;

public class RealImage implements Image {
    private String filename;

    public RealImage(String filename) {
        this.filename = filename;
        loadFromDisk();
    }

    private void loadFromDisk() {
        System.out.println("[Загрузка] Изображение " + filename + " загружено с диска");
    }

    @Override
    public void display() {
        System.out.println("[Отображение] Показываем изображение: " + filename);
    }
}