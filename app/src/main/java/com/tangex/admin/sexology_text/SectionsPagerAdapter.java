package com.tangex.admin.sexology_text;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//-------------CHAT-ACTIVITY-----------------
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Fragment_Friends fragmentFriends = new Fragment_Friends();
                return fragmentFriends;
            case 1:
                Fragment_Expert fragmentExpert = new Fragment_Expert();
                return fragmentExpert;
            case 2:
                Fragment_Chats fragmentChats = new Fragment_Chats();
                return fragmentChats;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Người dùng";
            case 1:
                return "Chuyên gia";
            case 2:
                return "Tin nhắn";
            default:
                return null;
        }
//        return super.getPageTitle(position);
    }

}
