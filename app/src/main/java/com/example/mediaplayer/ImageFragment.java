package com.example.mediaplayer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ImageFragment extends Fragment {

    private ImageView fragmentImageView;
    private int[] imageIds;
    private int currentImageIndex = 0;

    public static ImageFragment newInstance(int[] imageIds) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putIntArray("imageIds", imageIds);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imageIds = getArguments().getIntArray("imageIds");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        fragmentImageView = view.findViewById(R.id.fragmentImageView);

        // Инициализация начального изображения
        updateImage(imageIds[currentImageIndex]);

        return view;
    }

    public void updateImage(int imageId) {
        fragmentImageView.setImageResource(imageId);
    }
}
