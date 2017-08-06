package cc.kukua.android.activity.firstime;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.HomeActivity;
import cc.kukua.android.eventbuses.TransactFragment;
import cc.kukua.android.interfaces.FragmentInterface;
import cc.kukua.android.utils.UiUtils;

public class PersonalInfoActivity extends AppCompatActivity implements FragmentInterface {
    @BindView(R.id.btn_submit)
    Button nextButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkAndRegEventBus();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        //Call PersonalInfo Fragment
        transactFragment(new PersonalInfoFragment());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PersonalInfoActivity.this, AccountInfoActivity.class));
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setToolBarTitle(String title) {

    }
    private void checkAndUnRegEventBus() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
    private void checkAndRegEventBus() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        checkAndRegEventBus();
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkAndUnRegEventBus();
    }

    private void transactFragment(final Fragment fragment) {
        final String backStateName = fragment.getClass().getSimpleName();
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction().replace(R.id.fragment_content, fragment);
        fragmentTransaction.commit();

    }

    @SuppressWarnings("unused")
    @Subscribe(sticky = true, threadMode = ThreadMode.ASYNC)
    public void onEventAsync(final Object o) {
        UiUtils.runOnMain(new Runnable() {
            @Override
            public void run() {
                if (o instanceof TransactFragment) {
                    TransactFragment transactFragment = (TransactFragment) o;
                    transactFragment(transactFragment.getFragment());
                }
            }
        });
    }
}
