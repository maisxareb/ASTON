package ru.aston.adapter;

public class AdvancedMediaPlayer {
    public void playMp4(String filename) {
        System.out.println("[AdvancedPlayer] Воспроизведение MP4: " + filename);
    }

    public void playVlc(String filename) {
        System.out.println("[AdvancedPlayer] Воспроизведение VLC: " + filename);
    }
}