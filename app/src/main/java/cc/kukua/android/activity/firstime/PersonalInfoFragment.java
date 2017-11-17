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

import java.util.HashMap;

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
public class PersonalInfoFragment extends Fragment {


    @BindView(R.id.personal_account_subtitle)
    TextView subTitle;
    @BindView(R.id.btn_next_reg)
    Button btnNext;
    @BindView(R.id.et_first_name)
    EditText etFirstName;
    @BindView(R.id.et_last_name)
    EditText etLastName;

    boolean isNew = true;

    FragmentInterface fragmentInterface;
    SessionManager session;

    public PersonalInfoFragment() {
        // Required empty public constructor
    }

    //public static HashMap<String, String> userDetail = new HashMap<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
        ButterKnife.bind(this, rootView);
        session = new SessionManager(getContext());

        this.isNew = getArguments().getBoolean("isNew");
        updateFragmentUI();

        if (this.isNew) {
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Save Data
                    DummyDataProvider.userDetail.put("firstName", etFirstName.getText().toString());
                    DummyDataProvider.userDetail.put("lastName", etLastName.getText().toString());
                    Bundle bundle = new Bundle();
                    bundle.putBoolean("isNew", true);
                    AccountInfoFragment accountInfoFragment = new AccountInfoFragment();
                    accountInfoFragment.setArguments(bundle);
                    EventBus.getDefault().post(new TransactFragment(accountInfoFragment));
                }
            });
        } else {
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Save Data
                    session.saveFirstName(etFirstName.getText().toString());
                    session.saveLastName(etLastName.getText().toString());
                    UiUtils.showSnackbar("You personal info has been updated", v);
                }
            });
        }
        // Inflate the layout for this fragment
        return rootView;
    }


    private void updateFragmentUI() {
        if (fragmentInterface != null) {
            fragmentInterface.setToolBarTitle(getResources().getString(R.string.title_fragment_personal_info));

            if (this.isNew) {
                subTitle.setText(getResources().getString(R.string.subtitle_personal_info_new));
                btnNext.setText(getResources().getString(R.string.next));
            } else {
                subTitle.setText(getResources().getString(R.string.subtitle_personal_info_edit));
                btnNext.setText(getResources().getString(R.string.save));
                etFirstName.setText(session.getFirstName());
                etLastName.setText(session.getLastName());
            }
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