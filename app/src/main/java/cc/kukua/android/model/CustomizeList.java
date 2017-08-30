package cc.kukua.android.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mistaguy on 8/20/2017.
 */

public class CustomizeList {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> hats = new ArrayList<String>();
        hats.add("Hat 1");
        hats.add("Hat 2");
        hats.add("Hat 3");

        List<String> clothes = new ArrayList<String>();
        clothes.add("clothes 1");
        clothes.add("clothes 2");
        clothes.add("clothes 3");

        expandableListDetail.put("HATS", hats);
        expandableListDetail.put("CLOTHES", clothes);
        return expandableListDetail;
    }
}