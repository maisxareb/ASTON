package ru.aston.adapter;

public class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;

    @Override
    public void play(String audioType, String filename) {
        if (audioType.equalsIgnoreCase("mp3")) {
            System.out.println("  [AudioPlayer] Воспроизведение MP3: " + filename);
        }
        else if (audioType.equalsIgnoreCase("mp4") || audioType.equalsIgnoreCase("vlc")) {
            mediaAdapter = new MediaAdapter();
            mediaAdapter.play(audioType, filename);
        } else {
            System.out.println("  [AudioPlayer] Ошибка: неподдерживаемый формат " + audioType);
        }
    }
}