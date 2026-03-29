package ru.aston.builder;

public class Computer {
    private String cpu;
    private String ram;
    private String storage;
    private String graphicsCard;
    private boolean bluetooth;
    private boolean wifi;

    Computer(String cpu, String ram, String storage, String graphicsCard, boolean bluetooth, boolean wifi) {
        this.cpu = cpu;
        this.ram = ram;
        this.storage = storage;
        this.graphicsCard = graphicsCard;
        this.bluetooth = bluetooth;
        this.wifi = wifi;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("  Компьютер:\n");
        sb.append("    Процессор: ").append(cpu).append("\n");
        sb.append("    ОЗУ: ").append(ram).append("\n");
        sb.append("    Накопитель: ").append(storage != null ? storage : "не указан").append("\n");
        sb.append("    Видеокарта: ").append(graphicsCard != null ? graphicsCard : "встроенная").append("\n");
        sb.append("    Bluetooth: ").append(bluetooth ? "да" : "нет").append("\n");
        sb.append("    WiFi: ").append(wifi ? "да" : "нет");
        return sb.toString();
    }

    public static class ComputerBuilder {
        private String cpu;
        private String ram;
        private String storage;
        private String graphicsCard;
        private boolean bluetooth;
        private boolean wifi;

        public ComputerBuilder(String cpu, String ram) {
            this.cpu = cpu;
            this.ram = ram;
        }

        public ComputerBuilder storage(String storage) {
            this.storage = storage;
            return this;
        }

        public ComputerBuilder graphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public ComputerBuilder bluetooth(boolean bluetooth) {
            this.bluetooth = bluetooth;
            return this;
        }

        public ComputerBuilder wifi(boolean wifi) {
            this.wifi = wifi;
            return this;
        }

        public Computer build() {
            return new Computer(cpu, ram, storage, graphicsCard, bluetooth, wifi);
        }
    }
}