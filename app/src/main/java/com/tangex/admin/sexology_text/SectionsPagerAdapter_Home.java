package com.tangex.admin.sexology_text;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionsPagerAdapter_Home extends FragmentPagerAdapter {
    public SectionsPagerAdapter_Home(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Fragment_Home fragment_home = new Fragment_Home();
                return fragment_home;
            case 1:
                Fragment_Heath fragment_heath = new Fragment_Heath();
                return fragment_heath;
            case 2:
                Fragment_Psy_phys fragment_psy_phys = new Fragment_Psy_phys();
                return fragment_psy_phys;
            case 3:
                Fragment_Parent fragment_parent = new Fragment_Parent();
                return fragment_parent;
            case 4:
                Fragment_LGBT fragment_lgbt = new Fragment_LGBT();
                return fragment_lgbt;
            case 5:
                Fragment_Story fragment_story = new Fragment_Story();
                return fragment_story;
            case 6:

                Fragment_Skill fragment_skill = new Fragment_Skill();
                return fragment_skill;
            case 7:

                Fragment_Law fragment_law = new Fragment_Law();
                return fragment_law;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 8;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {

            case 1:
                return "Sức khỏe";
            case 2:
                return "Tâm lí";
            case 3:
                return "Phụ huynh";
            case 4:
                return "LGBT";
            case 5:
                return "Câu chuyện";
            case 6:
                return "Kỹ năng";
            case 7:
                return "Pháp luật";
        }
//        return super.getPageTitle(position);
        return null;
    }
}
