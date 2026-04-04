package ru.aston.adapter;

public class AudioPlayer implements MediaPlayer {
    private MediaAdapter mediaAdapter;

    @Override
    public void play(String audioType, String filename) {
        AudioFormat format = AudioFormat.fromString(audioType);

        if (format == null) {
            System.out.println("[AudioPlayer] Ошибка: неподдерживаемый формат " + audioType);
            return;
        }

        switch (format) {
            case MP3:
                System.out.println("[AudioPlayer] Воспроизведение MP3: " + filename);
                break;
            case MP4:
            case VLC:
                mediaAdapter = new MediaAdapter();
                mediaAdapter.play(audioType, filename);
                break;
            default:
                System.out.println("[AudioPlayer] Ошибка: неподдерживаемый формат " + audioType);
        }
    }
}