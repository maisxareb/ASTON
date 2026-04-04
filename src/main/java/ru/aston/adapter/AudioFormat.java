package ru.aston.adapter;

public enum AudioFormat {
    MP3,
    MP4,
    VLC;

    public static AudioFormat fromString(String type) {
        try {
            return AudioFormat.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}