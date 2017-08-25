package cc.kukua.android.activity.firstime;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.eventbuses.TransactFragment;
import cc.kukua.android.interfaces.FragmentInterface;

/**
 * @author Calistus
 */
public class AccountInfoFragment extends Fragment {

    private FragmentInterface fragmentInterface;

    @BindView(R.id.btn_next2)
    Button btnNext;
    @BindView(R.id.et_phone_number)
    EditText etPhone;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_account_info, container, false);

        ButterKnife.bind(this, rootView);
        setFragmentTitle();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DummyDataProvider.userDetail.put("phone",etPhone.getText().toString());
                DummyDataProvider.userDetail.put("email",etEmail.getText().toString());
                DummyDataProvider.userDetail.put("password",etPassword.getText().toString());

                //startActivity(new Intent(getActivity(), LocationFragment.class));
                LocationFragment locationFragment = new LocationFragment();
                EventBus.getDefault().post(new TransactFragment(locationFragment));
            }
        });
        // Inflate the layout for this fragment
        return rootView;     }

    private void setFragmentTitle() {
        if (fragmentInterface != null) {
            fragmentInterface.setToolBarTitle(DummyDataProvider.ACCOUNT_INFO);
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
