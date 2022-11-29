package com.workshops.onlinemusicplayer.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.firebase.firestore.auth.User;
import com.workshops.onlinemusicplayer.fragment.ExploreFragment;
import com.workshops.onlinemusicplayer.fragment.HomeFragment;
import com.workshops.onlinemusicplayer.fragment.LikeFragment;
import com.workshops.onlinemusicplayer.fragment.SongsFragment;
import com.workshops.onlinemusicplayer.fragment.UserFragment;
import com.workshops.onlinemusicplayer.listener.MusicSelectListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentPagerAdapter {

    private final MusicSelectListener selectListener;
    List<Fragment> fragments = new ArrayList<>();

    public MainPagerAdapter(FragmentManager fm, MusicSelectListener selectListener) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.selectListener = selectListener;

        setFragments();
    }

    public void setFragments() {
        fragments.add(HomeFragment.newInstance(selectListener));
        fragments.add(ExploreFragment.newInstance(selectListener));
        fragments.add(LikeFragment.newInstance(selectListener));
        fragments.add(UserFragment.newInstance());
    }

    @NotNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}