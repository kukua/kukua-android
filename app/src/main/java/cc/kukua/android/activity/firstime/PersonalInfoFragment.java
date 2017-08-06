package cc.kukua.android.activity.firstime;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.eventbuses.TransactFragment;
import cc.kukua.android.interfaces.FragmentInterface;

/**
 * A simple {@link Fragment} subclass.
 */
public class PersonalInfoFragment extends Fragment {


    @BindView(R.id.btn_next_reg)
    Button btnNext;
    FragmentInterface fragmentInterface;
    public PersonalInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View  rootView = inflater.inflate(R.layout.fragment_personal_info, container, false);
        ButterKnife.bind(this, rootView);
        setFragmentTitle();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccountInfoFragment accountInfoFragment = new AccountInfoFragment();
                EventBus.getDefault().post(new TransactFragment(accountInfoFragment));
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }


    private void setFragmentTitle() {
        if (fragmentInterface != null) {
            fragmentInterface.setToolBarTitle(DummyDataProvider.PERSONAL_INFO);
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