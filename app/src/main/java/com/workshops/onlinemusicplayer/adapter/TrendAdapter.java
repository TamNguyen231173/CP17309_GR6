package com.workshops.onlinemusicplayer.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.workshops.onlinemusicplayer.fragment.homeTabLayout.ArtistsFragment;
import com.workshops.onlinemusicplayer.fragment.homeTabLayout.NewsFragment;
import com.workshops.onlinemusicplayer.fragment.homeTabLayout.PodcastsFragment;
import com.workshops.onlinemusicplayer.fragment.homeTabLayout.VideoFragment;

public class TrendAdapter extends FragmentStateAdapter {

    public TrendAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new NewsFragment();
            case 1:
                return new VideoFragment();
            case 2:
                return new ArtistsFragment();
            case 3:
                return new PodcastsFragment();
            default:
                return new NewsFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

}
