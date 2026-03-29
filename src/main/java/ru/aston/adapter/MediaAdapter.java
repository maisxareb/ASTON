package ru.aston.adapter;

public class MediaAdapter implements MediaPlayer {
    private AdvancedMediaPlayer advancedPlayer;

    public MediaAdapter() {
        this.advancedPlayer = new AdvancedMediaPlayer();
    }

    @Override
    public void play(String audioType, String filename) {
        System.out.println("  [Адаптер] Преобразую запрос для формата " + audioType);
        if (audioType.equalsIgnoreCase("mp4")) {
            advancedPlayer.playMp4(filename);
        } else if (audioType.equalsIgnoreCase("vlc")) {
            advancedPlayer.playVlc(filename);
        } else {
            System.out.println("  [Адаптер] Не могу преобразовать формат: " + audioType);
        }
    }
}