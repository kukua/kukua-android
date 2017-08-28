package cc.kukua.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cc.kukua.android.activity.firstime.CharacterOneFragment;
import cc.kukua.android.activity.firstime.CharacterTwoFragment;
import cc.kukua.android.constants.DummyDataProvider;

/**
 * Created by mistaguy on 8/21/2017.
 */

public class ChooseCharacterPagerAdapter extends FragmentPagerAdapter {

    public ChooseCharacterPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int pos) {
        switch(pos) {

            case 0:
                DummyDataProvider.userDetail.put("character","0");
                return CharacterOneFragment.newInstance("FirstFragment, Instance 1");
            case 1:
                DummyDataProvider.userDetail.put("character","1");
                return CharacterTwoFragment.newInstance("SecondFragment, Instance 1");
            default:
                DummyDataProvider.userDetail.put("character","0");
                return CharacterOneFragment.newInstance("ThirdFragment, Default");
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}