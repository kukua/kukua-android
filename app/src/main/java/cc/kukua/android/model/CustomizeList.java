package cc.kukua.android.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cc.kukua.android.R;

/**
 * Created by mistaguy on 8/20/2017.
 */

public class CustomizeList {
    public static HashMap<String, List<Integer>> getData() {
        HashMap<String, List<Integer>> expandableListDetail = new HashMap<String, List<Integer>>();

        List<Integer> hats = new ArrayList<Integer>();
        hats.add(R.drawable.item_hat1);
        hats.add(R.drawable.item_hat2);

        List<Integer> gloves = new ArrayList<Integer>();
        gloves.add(R.drawable.item_lower_arm1);
        gloves.add(R.drawable.item_lower_arm2);

        List<Integer> shirts = new ArrayList<Integer>();
        shirts.add(R.drawable.item_torso1);
        shirts.add(R.drawable.item_torso2);

        List<Integer> faces = new ArrayList<Integer>();
        faces.add(R.drawable.item_face1);
        faces.add(R.drawable.item_face2);


        List<Integer> pants = new ArrayList<Integer>();
        pants.add(R.drawable.item_upper_leg1);
        pants.add(R.drawable.item_upper_leg2);

        List<Integer> shoes = new ArrayList<Integer>();
        shoes.add(R.drawable.item_lower_leg1);
        shoes.add(R.drawable.item_lower_leg2);

        expandableListDetail.put("HAT", hats);
        expandableListDetail.put("FACE", faces);
        expandableListDetail.put("SHIRT", shirts);
        expandableListDetail.put("GlOVES", gloves);
        expandableListDetail.put("PANTS", pants);
        expandableListDetail.put("SHOES", shoes);
        return expandableListDetail;
    }
}