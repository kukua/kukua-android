package cc.kukua.android.activity.firstime;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.LocationActivity;
import cc.kukua.android.R;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.interfaces.FragmentInterface;

/**
 * @author Calistus
 */
public class AppUsageFragment extends Fragment {


    private FragmentInterface fragmentInterface;

    @BindView(R.id.btn_next3)
    Button btnNext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_usage, container, false);

        ButterKnife.bind(this, rootView);
        setFragmentTitle();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }

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