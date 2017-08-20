package cc.kukua.android.activity.firstime;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cc.kukua.android.R;
import cc.kukua.android.adapters.AppuseListAdapter;
import cc.kukua.android.constants.DummyDataProvider;
import cc.kukua.android.interfaces.FragmentInterface;
import cc.kukua.android.model.AppuseList;

/**
 * @author Calistus
 */
public class AppUsageFragment extends Fragment {


    private FragmentInterface fragmentInterface;

    @BindView(R.id.btn_next3)
    Button btnNext;
    @BindView(R.id.btn_dont_specify)
    Button btnDontSpecify;
    @BindView(R.id.expandableListView)
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private int lastExpandedPosition = -1;
    private int lastExpandedItemPosition = -1;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_app_usage, container, false);

        ButterKnife.bind(this, rootView);
        setFragmentTitle();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });
        btnDontSpecify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LocationActivity.class));
            }
        });


        expandableListDetail = AppuseList.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new AppuseListAdapter(getContext(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;

                Toast.makeText( getContext(),
                        expandableListTitle.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText( getContext(),
                        expandableListTitle.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();

            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                Toast.makeText(
                        getContext(),
                        expandableListTitle.get(groupPosition)
                                + " -> "
                                + expandableListDetail.get(
                                expandableListTitle.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT
                ).show();

                if (lastExpandedItemPosition != -1
                        && childPosition != lastExpandedItemPosition) {
                    int index = parent.getFlatListPosition(
                            ExpandableListView.getPackedPositionForChild(lastExpandedPosition, lastExpandedItemPosition)
                    );
                    parent.setItemChecked(index, false);
                }
                lastExpandedItemPosition = childPosition;

                int index = parent.getFlatListPosition(ExpandableListView.getPackedPositionForChild(groupPosition, childPosition));
                parent.setItemChecked(index, true);

                return true;
            }
        });


        return rootView;
    }

    private void setFragmentTitle() {
        if (fragmentInterface != null) {
            fragmentInterface.setToolBarTitle(DummyDataProvider.App_USAGE);
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
