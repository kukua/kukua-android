package cc.kukua.android.activity.firstime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.adapters.ChooseCharacterPagerAdapter;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.utils.LogUtils;
import cc.kukua.android.utils.UiUtils;


public class ChooseCharacterActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager pager;
    @BindView(R.id.left_nav)
    ImageView leftNav;
    @BindView(R.id.right_nav)
    ImageView rightNav;
    @BindView(R.id.toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.btn_next3)
    Button btnNextButton;

    private String TAG = ChooseCharacterActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_character);
        ButterKnife.bind(this);
        
        tvToolbarTitle.setText(getString(R.string.choose_your_character));

        pager.setAdapter(new ChooseCharacterPagerAdapter(getSupportFragmentManager()));

        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = pager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    DummyDataProvider.userDetail.put("character",String.valueOf(tab));
                    pager.setCurrentItem(tab);
                } else if (tab == 0) {
                    pager.setCurrentItem(tab);
                    DummyDataProvider.userDetail.put("character",String.valueOf(tab));
                }
            }
        });

        // Images right navigatin
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = pager.getCurrentItem();
                tab++;
                pager.setCurrentItem(tab);
            }
        });
        btnNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.log(TAG,DummyDataProvider.userDetail.toString());
            }
        });
    }

}

