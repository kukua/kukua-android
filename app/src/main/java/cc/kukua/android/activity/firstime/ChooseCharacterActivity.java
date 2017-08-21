package cc.kukua.android.activity.firstime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.adapters.ChooseCharacterPagerAdapter;


public class ChooseCharacterActivity extends FragmentActivity {

    @BindView(R.id.viewpager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
        ButterKnife.bind(this);

        pager.setAdapter(new ChooseCharacterPagerAdapter(getSupportFragmentManager()));
    }

}

