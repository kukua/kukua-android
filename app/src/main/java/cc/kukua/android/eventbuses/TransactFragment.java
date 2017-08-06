package cc.kukua.android.eventbuses;

import android.support.v4.app.Fragment;

/**
 * @author Ilo Calistus
 */

public class TransactFragment {

    private Fragment fragment;

    /**
     * @param fragment - The Fragment to transact by a host activity
     ***/
    public TransactFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public Fragment getFragment() {
        return fragment;
    }

}
