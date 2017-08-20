package cc.kukua.android.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mistaguy on 8/20/2017.
 */

public class AppuseList {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> myself = new ArrayList<String>();
        myself.add("Business");
        myself.add("Farming");
        myself.add("Transport");

        List<String> family = new ArrayList<String>();
        family.add("Business");
        family.add("Farming");
        family.add("Transport");

        List<String> customers = new ArrayList<String>();
        customers.add("Business");
        customers.add("Farming");
        customers.add("Transport");

        expandableListDetail.put("Customers, which i server as:", customers);
        expandableListDetail.put("Family, for these people:", family);
        expandableListDetail.put("Myself, I work in:", myself);
        return expandableListDetail;
    }
}