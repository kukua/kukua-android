package cc.kukua.android.activity.firstime;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.activity.auth.SessionManager;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.eventbuses.TransactFragment;
import cc.kukua.android.interfaces.FragmentInterface;
import cc.kukua.android.utils.UiUtils;

/**
 * @author Calistus
 */
public class AccountInfoFragment extends Fragment {

    @BindView(R.id.account_subtitle)
    TextView subTitle;
    @BindView(R.id.btn_next2)
    Button btnNext;
    @BindView(R.id.et_phone_number)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    boolean isNew = true;

    FragmentInterface fragmentInterface;
    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_account_info, container, false);

        ButterKnife.bind(this, rootView);
        session = new SessionManager(getContext());

        this.isNew = getArguments().getBoolean("isNew");
        updateFragmentUI();

        if(this.isNew) {
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DummyDataProvider.userDetail.put("phone", etPhone.getText().toString());
                    DummyDataProvider.userDetail.put("email", etEmail.getText().toString());
                    DummyDataProvider.userDetail.put("password", etPassword.getText().toString());

                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isNew", true);
                    AccountInfoFragment accountInfoFragment = new AccountInfoFragment();
                    accountInfoFragment.setArguments(bundle);
                    LocationFragment locationFragment = new LocationFragment();
                    EventBus.getDefault().post(new TransactFragment(locationFragment));
                }
            });
        }else{
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    session.saveEmail(etEmail.getText().toString());
                    session.savePhoneNumber(etPhone.getText().toString());
                    UiUtils.showSnackbar("You account has been updated", v);
                }
            });
        }
        // Inflate the layout for this fragment
        return rootView;     }

    private void updateFragmentUI() {
        if (fragmentInterface != null) {
            fragmentInterface.setToolBarTitle(DummyDataProvider.ACCOUNT_INFO);
        }

        if (this.isNew) {
            subTitle.setText(getResources().getString(R.string.subtitle_account_info_new));
            btnNext.setText(getResources().getString(R.string.next));
        } else {
            etPassword.setEnabled(false);
            etEmail.setEnabled(false);
            subTitle.setText(getResources().getString(R.string.subtitle_account_info_edit));
            btnNext.setText(getResources().getString(R.string.save));
            etPhone.setText(session.getKeyPhoneNumber());
            etEmail.setText(session.getEmail());
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentInterface = (FragmentInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Host activity must implement the Fragment Interface");
        }
    }
}
