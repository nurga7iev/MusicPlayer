package com.example.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private int[] musicIds = {R.raw.song1, R.raw.song2, R.raw.song3}; // ваши музыкальные треки
    private int[] imageIds = {R.drawable.image1, R.drawable.image2, R.drawable.image3}; // ваши изображения
    private int currentTrackIndex = 0;

    private ImageView playButton;
    private ImageView pauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация кнопок
        ImageView nextButton = findViewById(R.id.nextButton);
        ImageView prevButton = findViewById(R.id.backButton);
        playButton = findViewById(R.id.playButton);
        pauseButton = findViewById(R.id.pauseButton);
        ImageView showImagesButton = findViewById(R.id.showImagesButton);

        // Слушатели событий для кнопок
        nextButton.setOnClickListener(v -> nextTrack());
        prevButton.setOnClickListener(v -> previousTrack());
        playButton.setOnClickListener(v -> playCurrentTrack());
        pauseButton.setOnClickListener(v -> pauseCurrentTrack());

        // Кнопка для просмотра изображений
        showImagesButton.setOnClickListener(v -> loadFragment());
    }

    private void loadFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ImageFragment fragment = ImageFragment.newInstance(imageIds); // Передаем массив изображений
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();
    }

    private void playMusic(int index) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(this, musicIds[index]);
        mediaPlayer.start();
    }

    private void playCurrentTrack() {
        if (mediaPlayer == null) {
            playMusic(currentTrackIndex);
        } else if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void pauseCurrentTrack() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void nextTrack() {
        currentTrackIndex = (currentTrackIndex + 1) % musicIds.length;
        playMusic(currentTrackIndex);
        updateFragmentImage(); // Обновляем изображение
    }

    private void previousTrack() {
        currentTrackIndex = (currentTrackIndex - 1 + musicIds.length) % musicIds.length;
        playMusic(currentTrackIndex);
        updateFragmentImage(); // Обновляем изображение
    }

    private void updateFragmentImage() {
        ImageFragment fragment = (ImageFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment != null) {
            fragment.updateImage(imageIds[currentTrackIndex]); // Обновляем изображение в фрагменте
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
