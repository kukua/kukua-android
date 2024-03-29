package cc.kukua.android.activity.firstime;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.eventbuses.TransactFragment;
import cc.kukua.android.interfaces.FragmentInterface;
import cc.kukua.android.utils.UiUtils;

public class RegisterActivity extends AppCompatActivity implements FragmentInterface {
    //@BindView(R.id.btn_next) Button nextButton;
    @BindView(R.id.toolbar_title)
    TextView tvToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        checkAndRegEventBus();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setHomeButtonEnabled(false);

        Bundle bundle = new Bundle();
        bundle.putBoolean("isNew", true);
        PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();
        personalInfoFragment.setArguments(bundle);

        //Inflate a Fragment
        transactFragment(personalInfoFragment);
  }

  public void goBack(View view){
      super.onBackPressed();
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
        tvToolbarTitle.setText(title.toUpperCase(Locale.getDefault()));

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
        final FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction().addToBackStack("NotificationFragmentKey");

        fragmentTransaction.setCustomAnimations(R.anim.enter_animation,
                R.anim.stay, R.anim.stay, R.anim.exit_animation);

        fragmentTransaction.replace(R.id.fragment_content,
                fragment, "KEY");
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
